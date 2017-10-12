package org.dclou.example.demogpb.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * Created by msnikitin on 28.04.2017.
 */
@Configuration
@EnableOAuth2Client
public class OAuth2Configuration {

//    @Bean
//    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(filter);
//        registration.setOrder(-100);
//        return registration;
//    }

//    @Bean
//    public RemoteTokenServices remoteTokenServices(@Value("${megaclient.client.tokenIntrospectionUri}") String checkTokenUrl,
//                                                   @Value("${megaclient.client.clientId}") String clientId,
//                                                   @Value("${megaclient.client.clientSecret}") String clientSecret) {
//        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
//        remoteTokenServices.setCheckTokenEndpointUrl(checkTokenUrl);
//        remoteTokenServices.setClientId(clientId);
//        remoteTokenServices.setClientSecret(clientSecret);
//        return remoteTokenServices;
//    }
}
