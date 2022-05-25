package ru.animalcare.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**","/img/**", "/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http .csrf().disable()
                .authorizeRequests().antMatchers("/","/public").permitAll()
                .antMatchers("/authenticated", "/admin", "/animals/add", "/account").authenticated().and()
                .formLogin()
                .loginPage("/login").usernameParameter("login").passwordParameter("entry")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies()
                .permitAll();

    }

//    @Bean
//    protected PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

}
