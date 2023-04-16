package cz.upce.nnpia.sem.jwtconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final String[] SWAGGER_WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/user/login").permitAll()
                .antMatchers("/api/v1/authorize").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/restaurant").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/v1/restaurant/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/restaurant/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/api/v1/restaurant/**").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.GET,"/api/v1/restaurant").hasAnyRole("ADMIN","USER")

                .antMatchers(HttpMethod.POST,"/api/v1/user").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/user/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/api/v1/user/**").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.GET,"/api/v1/user").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.PUT,"/api/v1/user/**").hasAnyRole("ADMIN","USER")

                .antMatchers(HttpMethod.POST,"/api/v1/evaluation/**").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.PUT,"/api/v1/evaluation/**").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.GET,"/api/v1/evaluation/**").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.GET,"/api/v1/evaluation").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.DELETE,"/api/v1/evaluation/**").hasRole("ADMIN")

                .anyRequest().authenticated();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
