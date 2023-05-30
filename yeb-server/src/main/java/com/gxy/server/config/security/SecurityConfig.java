package com.gxy.server.config.security;

/*
* Security配置类
* */

import com.gxy.server.config.security.component.*;
import com.gxy.server.pojo.Admin;
import com.gxy.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/*
* Security配置类
*
* */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;
    @Autowired
    private RestfulAccessDenieHandler restfulAccessDenieHandler;

    @Autowired
    private CustomFilter customFilter;

    @Autowired
    private CustomUrlDecisionManager customUrlDecisionManager;

    // 我们只需要使用把BCryptPasswordEncoder对象注入Spring容器中，SpringSecurity就会使用该PasswordEncoder来进行密码校验
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthencationTokenFilter jwtAuthencationTokenFilter(){
        return new JwtAuthencationTokenFilter();
    }


        /**
         * anyRequest          |   匹配所有请求路径
         * access              |   SpringEl表达式结果为true时可以访问
         * anonymous           |   匿名可以访问
         * denyAll             |   用户不能访问
         * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
         * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
         * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
         * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
         * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
         * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
         * permitAll           |   用户可以任意访问
         * rememberMe          |   允许通过remember-me登录的用户访问
         * authenticated       |   用户登录后可访问
         */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //使用Jwt,不需要csrf
        http.csrf()
                .disable()
                //因为基于token,所以不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //过滤请求
                .authorizeRequests()

                //所有请求都需要认证
                .anyRequest()
                // 用户登录后即可访问
                .authenticated()
                //动态权限配置
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customUrlDecisionManager);
                        o.setSecurityMetadataSource(customFilter);
                        return o;
                    }
                })
                .and()
                //禁用缓存
                .headers()
                .cacheControl();


        //添加JWT filter 登录授权过滤器，这里的意思是将jwtAuthenticationTokenFilter这个过滤器放在UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(jwtAuthencationTokenFilter(), UsernamePasswordAuthenticationFilter.class);


        //添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDenieHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }

    /*
    * 重写获取UserDetails的方法
    * */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    //这里返回一个UserDetailsService接口的实现类，也就是以后用我们的这个UserDetailsService的实现类中的kloadUserByUsername方法
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        // 使用lambda表达式，返回一个我们自定义实现的UserDetailsService的实现类，以后就调用我们实现的loadUserByUsername方法,从数据库中查询用户信息
        return username ->{
            Admin admin = adminService.getAdminByUserName(username);
            if(admin != null){
                admin.setRoles(adminService.getRoles(admin.getId()));
                return admin;
            }
            throw new UsernameNotFoundException("用户名或密码不正确");

        };
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //放行静态资源
        web.ignoring().antMatchers(
                "/login",
                "/logout",
                "/css/**",
                "/js/**",
                "/index.html",
                "favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/captcha",//放行验证码接口
                "/ws/**"//放行websocket
        );
    }



}
