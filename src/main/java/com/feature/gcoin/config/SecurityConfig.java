//package com.feature.gcoin.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//class SecurityConfig extends WebSecurityConfigurerAdapter
//{
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception
//    {
//        auth.inMemoryAuthentication().withUser("john").password("123").roles("USER");
//    }
//
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception
//    {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception
//    {
//        http.csrf().disable();
////        http.authorizeRequests().antMatchers("/","/swagger-resources","/login").permitAll().anyRequest().authenticated().and().formLogin().permitAll();
////        http.csrf().disable();
////        http.headers().frameOptions().disable();
//    }
//}
