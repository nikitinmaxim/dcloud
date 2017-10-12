package org.dclou.example.demogpb.customer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * Created by msnikitin on 20.04.2017.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
        .antMatcher("/**")
            .authorizeRequests()
        .antMatchers("/manage/**")
            .permitAll()
        .antMatchers("/api-docs/**", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**")
            .permitAll()
        .anyRequest()
            .hasAuthority("FOO_READ");
        // @formatter:on
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("customer").tokenStore(tokenStore);
    }

    @Autowired
    TokenStore tokenStore;
}
