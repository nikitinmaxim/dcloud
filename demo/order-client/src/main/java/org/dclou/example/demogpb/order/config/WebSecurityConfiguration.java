package org.dclou.example.demogpb.order.config;

import org.dclou.example.demogpb.order.util.OAuth2ClientAuthenticationProcessingFilter4PasswordGrant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by msnikitin on 28.04.2017.
 */
@Configuration
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/api/**")
                    .authenticated()
                .antMatchers("/**", "/login**")
                    .permitAll()
                .anyRequest()
                    .authenticated()
                .and().logout().logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))).permitAll()
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
    }

    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();

        filters.add(ssoFilter(pass(), "/login/pass", true));
        filters.add(ssoFilter(code(), "/login/code", false));

        filter.setFilters(filters);
        return filter;
    }

    private Filter ssoFilter(WebSecurityConfiguration.ClientResources client, String path, boolean usePasswordGrant) {
        OAuth2ClientAuthenticationProcessingFilter filter = usePasswordGrant ?
                new OAuth2ClientAuthenticationProcessingFilter4PasswordGrant(path)
                : new OAuth2ClientAuthenticationProcessingFilter(path);

        OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
        filter.setRestTemplate(template);

        UserInfoTokenServices tokenServices = new UserInfoTokenServices(
                client.getResource().getUserInfoUri(), client.getClient().getClientId());
        tokenServices.setRestTemplate(template);

        filter.setTokenServices(tokenServices);
        return filter;
    }

    @Bean
    @ConfigurationProperties("oauth2settings")
    public WebSecurityConfiguration.ClientResources pass() {
        return new WebSecurityConfiguration.ClientResourcesPasswordGrant();
    }

    @Bean
    @ConfigurationProperties("oauth2settings")
    public WebSecurityConfiguration.ClientResources code() {
        return new WebSecurityConfiguration.ClientResourcesAuthCodeGrant();
    }

    @Bean
    public OAuth2RestTemplate apiRestTemplate() {
        OAuth2RestTemplate template = new OAuth2RestTemplate(pass().getClient(), oauth2ClientContext);
        template.setRetryBadAccessTokens(false);
        return template;
    }

    interface ClientResources {
        BaseOAuth2ProtectedResourceDetails getClient();
        ResourceServerProperties getResource();
    }

    class ClientResourcesPasswordGrant implements WebSecurityConfiguration.ClientResources {
        @NestedConfigurationProperty
        private ResourceServerProperties resource = new ResourceServerProperties();

        @NestedConfigurationProperty
        private ResourceOwnerPasswordResourceDetails client = new ResourceOwnerPasswordResourceDetails();

        @Override
        public ResourceOwnerPasswordResourceDetails getClient() {
            return client;
        }

        @Override
        public ResourceServerProperties getResource() {
            return resource;
        }
    }

    class ClientResourcesAuthCodeGrant implements WebSecurityConfiguration.ClientResources {
        @NestedConfigurationProperty
        private ResourceServerProperties resource = new ResourceServerProperties();

        @NestedConfigurationProperty
        private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

        @Override
        public AuthorizationCodeResourceDetails getClient() {
            return client;
        }

        @Override
        public ResourceServerProperties getResource() {
            return resource;
        }
    }
}
