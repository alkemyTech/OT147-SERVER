package com.alkemy.ong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();

    }
    /**
     * Provides the authorization description.
     *
     * @return an APIKey object
     */
    private ApiKey apiKey(){
        return new ApiKey("JWT","Authorization","header");
    }
    /**
     * Provides a default security reference.
     *
     * @return a default set of authorizations to apply to each api operation
     */
    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }
    /**
     * Provides a default security scope.
     *
     * @return a list of security references
     */
    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope= new AuthorizationScope("global","accessEverything");
        AuthorizationScope[] authorizationScopes=new AuthorizationScope[1];
        authorizationScopes[0]=authorizationScope;
        return Arrays.asList(new SecurityReference("JWT",authorizationScopes));
    }

    /**
     * Provides information about the API: name, terms of service, description, contact information,
     * version.
     *
     * @return an API Info object
     */
    private ApiInfo apiInfo(){
        return new ApiInfo(
                "ONG Alkemy",
                "Description: ONG BackEnd Java With SpringBoot",
                "1.0",
                "Terminos y Condiciones. ",
                new Contact("group147.com",
                        "www.Alkemy.org",
                        "alkemy@gmail.com"),
                "Licencia: Alkemy.org and Group pointer.",
                "www.ApiLicencia.com",
                Collections.emptyList()
        );
    }
}
