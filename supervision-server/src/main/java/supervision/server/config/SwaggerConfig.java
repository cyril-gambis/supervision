package supervision.server.config;

import static com.google.common.base.Predicates.not;
import static springfox.documentation.builders.PathSelectors.regex;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



/**
 * Configuration of Swagger: the API documentation generator
 * 2 "dockets" are created (= 2 parts in documentation)
 * 
 * More examples: https://github.com/springfox/springfox-demos/blob/master/boot-swagger/src/main/java/springfoxdemo/boot/swagger/Application.java
 * https://springframework.guru/spring-boot-restful-api-documentation-with-swagger-2/
 * 
 * @author cyrilg
 *
 */
@Configuration
@EnableSwagger2
@Import({springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration.class})
public class SwaggerConfig {

	@Bean
	public Docket apiUsers() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Users")
				.select()
				.apis(RequestHandlerSelectors.any())
				// Autre possibilité: en fonction d'un package de base
//				.apis(RequestHandlerSelectors.basePackage("guru.springframework.controllers"))
				.paths(postPathsUsers())
				// Autre possibilité: tous les chemins d'API
//				.paths(PathSelectors.any())
				.build()
				.apiInfo(metaData())
				// Substitute java.time to java.util.Date
				.directModelSubstitute(LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(LocalDateTime.class, java.util.Date.class)
				;
	}

	@Bean
	public Docket apiDefault() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Default")
				.select()
				.apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.any())
				.paths(postPathsDefault())
				.build()
				.apiInfo(metaData())
				// Substitute java.time to java.util.Date
				.directModelSubstitute(LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(LocalDateTime.class, java.util.Date.class)
				;
	}

	private Predicate<String> postPathsUsers() {
		return regex("/users.*");
	}
/*
	private Predicate<String> postPathsTest() {
		return or(regex("/package1.*"), regex("/package2.*"));
	}
*/
	private Predicate<String> postPathsDefault() {
		return not(postPathsUsers());
	}
	
	/*
	private Predicate<String> postPaths() {
	// return regex("/*.*");
		return or(regex("/api/posts.*"), regex("/api/javainuse.*"));
	}
	*/
    private ApiInfo metaData() {
    	return new ApiInfoBuilder().title("Supervision REST API")
				.description("Supervision REST API for managing clients")
				.termsOfServiceUrl("http://www.google.com")
				.contact(new Contact("Supervision team", "http://www.google.com", "supervision@myteam.supervision"))
				.license("Copyright")
				.licenseUrl("http://www.google.fr")
				.version("1.0")
				.build();
    }
	
}


/* Example configuration

@Bean
    public Docket confDocketContext1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("GroupName1")
                .apiInfo(getApiInfo())
                .useDefaultResponseMessages(false)
                .pathMapping("/api/api1")
//                .forCodeGeneration(true)
                .alternateTypeRules(
                        getAlternateTypeRule(Collection.class, WildcardType.class, List.class,
                                WildcardType.class),
                        getAlternateTypeRule(Resources.class, WildcardType.class, Resources.class),
                        getAlternateTypeRule(PagedResources.class, WildcardType.class,
                                PagedResources.class))
                .directModelSubstitute(XMLGregorianCalendar.class, Date.class)
                .ignoredParameterTypes(PagedResourcesAssembler.class, Pageable.class).select()
                .paths(excludePath("api2")).build();
    }

    @Bean
    public Docket confDocketContext2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("GroupName2")
                .apiInfo(getApiInfo())
                .useDefaultResponseMessages(false)
                .pathMapping("/api/api2")
//                .forCodeGeneration(true)
                .alternateTypeRules(
                        getAlternateTypeRule(Collection.class, WildcardType.class, List.class,
                                WildcardType.class))
                .directModelSubstitute(XMLGregorianCalendar.class, Date.class)
                .ignoredParameterTypes(PagedResourcesAssembler.class, Pageable.class).select()
                .paths(includePath("api2")).build();
    }



	Exclude basic error controller

	return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .paths(Predicates.not(PathSelectors.regex("/error"))) // Exclude Spring error controllers
            .build();
*/