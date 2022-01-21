package co.id.jss.jssrestapi.config;

import co.id.jss.jssrestapi.config.filter.CustomAuthenticationFilter;
import co.id.jss.jssrestapi.config.filter.HttpLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public abstract class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() {
        return new CustomAuthenticationFilter();
    }

    @Bean
    public HttpLoggingFilter httpLoggingFilter() {
        return new HttpLoggingFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .authorizeRequests().antMatchers(HttpMethod.DELETE).denyAll();

        http.addFilterAfter(httpLoggingFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(customAuthenticationFilter(), HttpLoggingFilter.class);
    }
}