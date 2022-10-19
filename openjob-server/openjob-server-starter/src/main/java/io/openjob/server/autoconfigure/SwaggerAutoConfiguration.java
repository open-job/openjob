package io.openjob.server.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Configuration
public class SwaggerAutoConfiguration {
    @Bean
    public Docket createOpenapi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("openapi")
                .apiInfo(ApiInfo.DEFAULT)
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.openjob.server.openapi"))
                .build();
    }

    @Bean
    public Docket createAdmin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("admin")
                .apiInfo(ApiInfo.DEFAULT)
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.openjob.server.admin"))
                .build();
    }
}
