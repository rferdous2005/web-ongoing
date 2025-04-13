package com.cirt.web.config;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;

import com.cirt.web.exception.AppAccessDeniedHandler;
import com.cirt.web.exception.AppAuthEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AppAuthEntryPoint appAuthEntryPoint;
    private final AppAccessDeniedHandler AppAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").authenticated() // Protect /admin
                .anyRequest().permitAll() // Public access to other URLs
            )
            .formLogin(form -> form
                .loginPage("/login") // Custom login page
                .defaultSuccessUrl("/admin/posts", true) // Redirect after login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
            );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    // @Bean
    // public InMemoryUserDetailsManager userDetailsService() {
    //     return new InMemoryUserDetailsManager(
    //         User.withUsername("admin@cirt")
    //             .password(passwordEncoder().encode("cirt-admin@!23"))
    //             .roles("ADMIN")
    //             .build(),
    //         User.withUsername("raiyan@cirt")
    //             .password(passwordEncoder().encode("VhE4CUJYJM5h-cirt@fe85b1cd9daa%7D"))
    //             .roles("ADMIN")
    //             .build()
    //     );
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }
}
