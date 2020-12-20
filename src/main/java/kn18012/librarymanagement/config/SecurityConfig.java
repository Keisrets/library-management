package kn18012.librarymanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    public SecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/results/**").permitAll()
                .and().authorizeRequests().antMatchers("/login", "/register").permitAll()
                .and().authorizeRequests().antMatchers("/lib-admin/**").access("hasAuthority('admin')")
                .and().authorizeRequests().antMatchers("/lib-dashboard/**").access("hasAnyAuthority('librarian', 'admin')").anyRequest().authenticated()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/", true).failureUrl("/login-failed").successHandler(authenticationSuccessHandler).permitAll()
                .and().logout().logoutSuccessUrl("/").permitAll().logoutUrl("/logout").invalidateHttpSession(true)
                .and().headers().frameOptions().disable()
                .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(6);
    }
}