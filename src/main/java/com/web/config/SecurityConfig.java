package com.web.config;

import com.web.domain.Account;
import com.web.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired// Sử dụng @Lazy để trì hoãn việc khởi tạo bean
    @Lazy
    AccountService accountService;

    @Autowired
    @Lazy
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;

    //cung cap nguon du lieu
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(username -> {
            try {
                Account user = accountService.findById(username).get();
                String password = bCryptPasswordEncoder.encode(user.getPassword());
                String[] roles = user.getAuthorities().stream()
                        .map(er -> er.getRole().getId())
                        .collect(Collectors.toList()).toArray(new String[0]);
                return User.withUsername(username).password(password).roles(roles).build();
            }catch (NoSuchElementException e){
                throw new UsernameNotFoundException(username + "not found");
            }
        });
    }
    //phan quyen su dung
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/cart/**").authenticated()
                .antMatchers("/order/**").authenticated()
                .antMatchers("/admin/**").hasAnyRole("STAF","DIRE")
                .anyRequest().permitAll();

        http.formLogin()
                .loginPage("/login/form")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/",false)
                .failureUrl("/login/error");
        http.oauth2Login()
                .loginPage("/oauth2/login/form")
                .defaultSuccessUrl("/oauth2/login/success",true)
                .failureUrl("/oauth2/login/error")
                .successHandler(googleOAuth2SuccessHandler);

        http.exceptionHandling()
                .accessDeniedPage("/security/");
        http.logout()
                .logoutUrl("/logoff")
                .logoutSuccessUrl("/");
    }
    //co che ma hoa mk
    @Bean
    BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
