package com.bitchoi.landbnbserver.config;

import com.bitchoi.landbnbserver.filter.AuthenticationFilter;
import com.bitchoi.landbnbserver.security.AuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String[] WHITELIST = {
            "/v2/api-docs",
            "/webjars/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/registrations/**",
            "/auth/login",
            "/error"
    };

    private final AuthenticationProvider provider;

    public SecurityConfig(AuthenticationProvider provider) {
        super();
        this.provider = provider;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider);
    }

    @Bean
    AuthenticationFilter authenticationFilter() throws Exception {
        RequestMatcher rm =
                new NegatedRequestMatcher(new OrRequestMatcher(Arrays.stream(WHITELIST).map(AntPathRequestMatcher::new).collect(Collectors.toList())));
        final var filter = new AuthenticationFilter(rm);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().disable()
                .authenticationProvider(provider)
                .addFilterAfter(authenticationFilter(), AnonymousAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().disable();
    }
}
