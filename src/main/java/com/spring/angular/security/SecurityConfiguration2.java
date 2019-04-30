//package com.spring.angular.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//
//@Configuration
//
//@EnableGlobalMethodSecurity(securedEnabled = true)
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
//
//
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////
////        http.headers().cacheControl();
////        http.csrf().disable()
////              .antMatcher("/**")
////                .authorizeRequests().antMatchers("/api/**").access("hasRole('users')")
////                .anyRequest().authenticated().and().oauth2Login()
////                .and()
////                .addFilterBefore(new JwtAuthFilter(), UsernamePasswordAuthenticationFilter.class) .sessionManagement()
////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////    }
//
//
//    @Autowired
//    UserDetailsServiceImpl userDetailsService;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .oauth2Login();
//    }
//
//
//}
