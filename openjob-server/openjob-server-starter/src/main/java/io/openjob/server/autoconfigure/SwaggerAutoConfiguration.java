package io.openjob.server.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Configuration
public class SwaggerAutoConfiguration {

    private static final String API_KEY_TOKEN_AUTH = "TokenAuth";

    private static final String API_KEY_SESSION_AUTH = "SessionAuth";

    /**
     * Openapi.
     *
     * @return Docket
     */
    @Bean
    public Docket createOpenapi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("openapi")
                .apiInfo(ApiInfo.DEFAULT)
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.openjob.server.openapi"))
                .build();
    }

    /**
     * Admin api
     *
     * @return Docket
     */
    @Bean
    public Docket createAdminApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("admin")
                .apiInfo(ApiInfo.DEFAULT)
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.openjob.server.admin"))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private List<ApiKey> securitySchemes() {
        return newArrayList(
                new ApiKey(API_KEY_TOKEN_AUTH, "Token", "header"),
                new ApiKey(API_KEY_SESSION_AUTH, "Session", "header")
        );
    }

    private List<SecurityContext> securityContexts() {
        return newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(selector -> !selector.contains("login") && !selector.contains("logout"))
                        .build()
        );
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");

        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = authorizationScope;

        return newArrayList(
                new SecurityReference(API_KEY_TOKEN_AUTH, scopes),
                new SecurityReference(API_KEY_SESSION_AUTH, scopes)
        );
    }
}
