package kn18012.librarymanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/login", "/register").permitAll()
                .antMatchers("/lib-admin/**").access("hasAuthority('admin')").anyRequest().authenticated()
                .antMatchers("/lib-dashboard/**").access("hasAuthority('librarian')").anyRequest().authenticated()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/", true).failureUrl("/login-failed").permitAll()
                .and().logout().logoutSuccessUrl("/").permitAll().logoutUrl("/logout").invalidateHttpSession(true)
                .and().headers().frameOptions().disable()
                .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(6);
    }
}