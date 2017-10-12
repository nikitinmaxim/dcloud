package org.dclou.platform.adminservice;

import be.ordina.msdashboard.EnableMicroservicesDashboardServer;
import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableAdminServer
@EnableMicroservicesDashboardServer
@SpringCloudApplication
@EnableDiscoveryClient
@EnableConfigurationProperties
public class SpringBootAdminApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootAdminApplication.class, args);
	}
}
