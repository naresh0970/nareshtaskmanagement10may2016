package com.tms;


import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Security configuration. Defines configuration methods and security
 * constraints. Autowired bean definitions can be found in {@link AuthAppConfig}
 * 
 * @author Pavan
 *
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	MyUserDetials userDetailsService;

	@Autowired
	DataSource datasource;
	Logger logger = LoggerFactory.getLogger(getClass());


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		logger.info("Configuring the security of the application");

		http

		// Basic Authentication
		.httpBasic()
		.and()
		.authorizeRequests()

		.antMatchers("/public/**","/fonts/**","/registerUser","/admin/**").permitAll()
		.antMatchers("/createTask").access("hasRole('projectManager')")
		//.antMatchers("/admin/**").access("hasRole('admin')")
		// Allother requests are to be authenticated.
		.anyRequest().authenticated().and().logout()
		 //Logout requires form submit. Bypassing the same.
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	.logoutSuccessUrl("/public/index.html").and().csrf().disable();
}

		

		

	/*	http.httpBasic().and().authorizeRequests().antMatchers("/").permitAll()
		.antMatchers("/admin/**").access("hasRole('ADMIN')")
		.antMatchers("/").access("hasRole('USER')")
		.antMatchers("/clerk/**").access("hasRole('CLERK')")
				.and().logout().invalidateHttpSession(false)
                .logoutUrl("/logout").logoutSuccessUrl("/index.html")
				.and().csrf().disable();*/
	
		
		

	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService);
		
		//auth.inMemoryAuthentication().withUser("naresh").password("naresh").authorities("ROLE_USER", "USER");
		
		/*
		auth.jdbcAuthentication()
		.dataSource(datasource)
		.usersByUsernameQuery("select username, password,enabled from user where username=?")
		.authoritiesByUsernameQuery("select username,role from user where username=?");
		*/

	}


	/*@Bean(name="passwordEncoder")
    public PasswordEncoder passwordencoder(){
    	return (PasswordEncoder) new BCryptPasswordEncoder();
    }*/
}
