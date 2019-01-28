package com.penghuang.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全配置类
 * @author Administrator
 *
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * 自定义配置
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
      .antMatchers("/css/**","/js/**","/fonts/**","/index").permitAll() // 静态资源都可以访问
      .antMatchers("/users/**").hasRole("ADMIN") //需要相应的角色才能访问
      .and()
      .formLogin()  //基于Form表单登录验证
      .loginPage("/login").failureUrl("/login-error"); // 自定义登录界面
	}
   
	/**
	 * 认证信息管理
	 */
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()) //认证信息存储于内存中
      .withUser("penghuang").password(new BCryptPasswordEncoder().encode("a5701582")).roles("ADMIN"); // 初始化一个管理员权限用户
	}
}
