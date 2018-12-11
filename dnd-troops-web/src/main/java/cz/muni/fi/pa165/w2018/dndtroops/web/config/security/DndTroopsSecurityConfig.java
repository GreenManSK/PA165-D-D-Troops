package cz.muni.fi.pa165.w2018.dndtroops.web.config.security;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.UserDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.UserFacade;
import cz.muni.fi.pa165.w2018.dndtroops.web.WebUris;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;

/**
 * Class DndTroopsSecurityConfig
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Configuration
@EnableWebSecurity
public class DndTroopsSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        List<UserDTO> users = userFacade.getAll();
        for (UserDTO user : users) {
            auth.inMemoryAuthentication()
                    .passwordEncoder(passwordEncoder)
                    .withUser(user.getLogin())
                    .password(user.getLogin())
                    .roles(userFacade.isAdmin(user.getId()) ? "ADMIN" : "USER");
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()

                //Hero controller
                .antMatchers(WebUris.URL_HERO + "/").hasAnyRole("ADMIN")
                .antMatchers(WebUris.URL_HERO + "/all").hasAnyRole("ADMIN")
                .antMatchers(WebUris.URL_HERO + "/create").hasAnyRole("ADMIN")
                .antMatchers(WebUris.URL_HERO + "/*/update").hasAnyRole("ADMIN", "USER")
                .antMatchers(WebUris.URL_HERO + "/*/delete").hasAnyRole("ADMIN")
                .antMatchers(WebUris.URL_HERO + "/**").hasAnyRole("ADMIN", "USER")

                //Role controller
                .antMatchers(WebUris.URL_ROLE + "/").hasAnyRole("ADMIN")
                .antMatchers(WebUris.URL_ROLE + "/all").hasAnyRole("ADMIN")
                .antMatchers(WebUris.URL_ROLE + "/create").hasAnyRole("ADMIN")
                .antMatchers(WebUris.URL_ROLE + "/*/update").hasAnyRole("ADMIN")
                .antMatchers(WebUris.URL_ROLE + "/*/delete").hasAnyRole("ADMIN")
                .antMatchers(WebUris.URL_ROLE + "/**").hasAnyRole("ADMIN")

                //Troop controller
                .antMatchers(WebUris.URL_TROOP + "/").hasAnyRole("ADMIN")
                .antMatchers(WebUris.URL_TROOP + "/all").hasAnyRole("ADMIN")
                .antMatchers(WebUris.URL_TROOP + "/findByName/*").hasAnyRole("ADMIN")
                .antMatchers(WebUris.URL_TROOP + "/findByMission/*").hasAnyRole("ADMIN")
                .antMatchers(WebUris.URL_TROOP + "/create").hasAnyRole("ADMIN")
                .antMatchers(WebUris.URL_TROOP + "/*/update").hasAnyRole("ADMIN")
                .antMatchers(WebUris.URL_TROOP + "/*/delete").hasAnyRole("ADMIN")
                .antMatchers(WebUris.URL_TROOP + "/**").hasAnyRole("ADMIN")

                // Others
                .antMatchers(WebUris.URL_LOGIN + "*").permitAll()
                .antMatchers(WebUris.NOT_FOUND).permitAll()
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
        return new PasswordEncoderImpl();
    }
}
