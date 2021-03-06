package org.dclou.platform.authservice.config;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * Created by msnikitin on 28.04.2017.
 */
@Configuration
@EnableGlobalMethodSecurity(proxyTargetClass = true, mode = AdviceMode.PROXY,
        prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
}
