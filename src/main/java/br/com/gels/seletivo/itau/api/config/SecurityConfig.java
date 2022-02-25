package br.com.gels.seletivo.itau.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userServiceDetailService;

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceDetailService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.formLogin().disable();
        http
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .requestMatchers(EndpointRequest.to(ShutdownEndpoint.class))
                .hasRole("ACTUATOR_ADMIN")
                .requestMatchers(EndpointRequest.toAnyEndpoint())
                .permitAll()
                .requestMatchers(PathRequest.toStaticResources()
                        .atCommonLocations())
                .permitAll()
                .and()
                .headers()
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers",
                        "Authorization,Content-Type,Accept,token"))
                .frameOptions()
                .sameOrigin()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .ignoringAntMatchers("/h2-console/**")
                .disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/h2-console/**");
    }
}