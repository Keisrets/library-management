package kn18012.librarymanagement.config;

import kn18012.librarymanagement.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll();
//        http.authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/login", "/register").permitAll()
//                .antMatchers("/lib-admin/**").access("hasAuthority('admin')").anyRequest().authenticated()
//                .and().formLogin().loginPage("/login").successForwardUrl("/").defaultSuccessUrl("/succ").failureUrl("/err").permitAll()
//                .and().logout().logoutSuccessUrl("/").permitAll().logoutUrl("/logout")
//                .and().headers().frameOptions().disable()
//                .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(6);
    }
}