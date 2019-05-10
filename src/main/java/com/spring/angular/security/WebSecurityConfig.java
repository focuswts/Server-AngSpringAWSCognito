package com.spring.angular.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
            // other public endpoints of your API may be appended to this array
    };


    @Bean(name = "jwt")
    public JwtAuthFilter authTokenFilterBean() throws Exception {
        return new JwtAuthFilter();
    }


    //Fix For Cors Problems When Angular Call API
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


    @Bean
    public FilterRegistrationBean filterRegistrationBean() throws Exception {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        JwtAuthFilter securityFilter = authTokenFilterBean();
        registrationBean.setFilter(securityFilter);
        registrationBean.addUrlPatterns("/api/**");
        //registrationBean.setName("jwt");
        registrationBean.setEnabled(false);
        return registrationBean;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //  UsernamePasswordAuthenticationFilter f = new UsernamePasswordAuthenticationFilter();

        http.cors().and().csrf().disable().
                authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().antMatcher("/api/**")
                .addFilterAt(authTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

//                .and()
//                .addFilterBefore(authTokenFilterBean(), UsernamePasswordAuthenticationFilter.class).antMatcher("/v2/api-docs");
    }


}
