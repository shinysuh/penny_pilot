package com.jenna.pennypilot.core.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI getOpenApi() {
//        return new OpenAPI()
//        .components(new Components())
//                .info(getInfo());
        return new OpenAPI().info(getInfo());
    }

    private Info getInfo() {
        return new Info()
                .version("1.0.0")
                .title("Swagger for Penny Pilot")
                .description("Penny Pilot APIs");
    }

    @Bean
    public GroupedOpenApi api() {
        String[] paths = {"/api/v1/**"};
        String[] packageToScan = {"com.jenna.pennypilot"};
        return GroupedOpenApi.builder().group("springdoc-openapi")
                .pathsToMatch(paths)
                .pathsToMatch(packageToScan)
                .build();
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }

//    @Bean
//    ForwardedHeaderFilter forwardedHeaderFilter() {
//        return new ForwardedHeaderFilter();
//    }

}
