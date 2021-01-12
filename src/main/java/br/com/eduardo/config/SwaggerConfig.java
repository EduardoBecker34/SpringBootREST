package br.com.eduardo.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.eduardo")).paths(PathSelectors.any()).build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("RESTful API com Spring Boot", 
				"API para estudos e retorno de dados de teste.",
				"v1", 
				"URL de Termos de serviço", 
				new Contact("Eduardo Becker da Luz", "https://github.com/EduardoBecker34/SpringBootREST.git", "eduardobeckerdaluz@gmail.com"), 
				"Licença", 
				"URL da Licença",
				Collections.emptyList());
	}

}
