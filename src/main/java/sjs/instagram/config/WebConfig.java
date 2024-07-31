package sjs.instagram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import sjs.instagram.db.user.UserEntity;

@Configuration
public class WebConfig {
    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c.disable())
                .formLogin(f -> f
                        .usernameParameter("instagramId")
                        .passwordParameter("password")
                        .loginPage("/loginForm")
                        .loginProcessingUrl("/loginProc")
                        .defaultSuccessUrl("/")
                        .failureUrl("/loginFail")
                )
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/loginForm").permitAll()
                        .requestMatchers("/loginFail").permitAll()
                        .requestMatchers("/loginProc").permitAll()
                        .requestMatchers("/joinForm").permitAll()
                        .requestMatchers("/joinProc").permitAll()
                        .anyRequest().hasRole(UserEntity.UserRole.USER.name())
                );
        return http.build();
    }
}
