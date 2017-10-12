package org.dclou.example.demogpb.customer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private ServletContext servletContext;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
                .apis(RequestHandlerSelectors.basePackage("org.dclou.example.demogpb.customer.mvc"))
                .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
            .pathProvider(new RelativePathProvider(servletContext));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Customer Service API")
                .description("API of the Customer Application")
                .version("1.0.0")
                .build();
    }
}