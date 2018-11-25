package cz.muni.fi.pa165.w2018.dndtroops.web.config.security;

import cz.muni.fi.pa165.w2018.dndtroops.web.WebUris;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Class DndTroopsSecurityConfig
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Configuration
@EnableWebSecurity
public class DndTroopsSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1").password(passwordEncoder().encode("user")).roles("USER")
                .and()
                .withUser("user2").password(passwordEncoder().encode("user")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(WebUris.URL_LOGIN + "*").permitAll()
                .anyRequest().authenticated()

                // Login
                .and()
                .formLogin()
                .loginPage(WebUris.URL_LOGIN)
                .loginProcessingUrl(WebUris.URL_LOGIN)
                .failureUrl(WebUris.URL_LOGIN + "?error=true")
                .usernameParameter("user_login").passwordParameter("user_password")
                .defaultSuccessUrl(WebUris.URL_HOME, true)
                .permitAll()

                // Logout
                .and()
                .logout()
                .logoutUrl(WebUris.URL_LOGOUT)
                .deleteCookies("JSESSIONID")
                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
