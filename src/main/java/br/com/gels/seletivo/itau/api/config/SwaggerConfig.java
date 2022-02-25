package br.com.gels.seletivo.itau.api.config;

import br.com.gels.seletivo.itau.api.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(Constants.MAIN_PACKAGE))
                .paths(regex("/.*"))
                .build()
                .globalResponseMessage(RequestMethod.POST, responseMessage())
                .globalResponseMessage(RequestMethod.GET, responseMessage())
                .globalResponseMessage(RequestMethod.PUT, responseMessage())
                .globalResponseMessage(RequestMethod.DELETE, responseMessage())
                .globalResponseMessage(RequestMethod.PATCH, responseMessage())
                .apiInfo(apiInfo());
    }

    @SuppressWarnings("rawtypes")
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                Constants.TITLE_API,
                Constants.TITLE_VERSION,
                Constants.VERSION,
                Constants.TERMS_OF_SERVICE,
                new Contact(Constants.AUTHOR, Constants.URL_AUTOR, Constants.EMAIL_AUTOR),
                Constants.LICENSE, Constants.LICENSE_LINK, new ArrayList<VendorExtension>()
        );

        return apiInfo;
    }

    @SuppressWarnings("serial")
    private List<ResponseMessage> responseMessage() {
        return new ArrayList<ResponseMessage>() {{
            add(new ResponseMessageBuilder().code(200).message(Constants.MESSAGE_CODE200).build());
            add(new ResponseMessageBuilder().code(201).message(Constants.MESSAGE_CODE201).build());
            add(new ResponseMessageBuilder().code(204).message(Constants.MESSAGE_CODE204).build());
            add(new ResponseMessageBuilder().code(401).message(Constants.MESSAGE_CODE401).build());
            add(new ResponseMessageBuilder().code(403).message(Constants.MESSAGE_CODE403).build());
            add(new ResponseMessageBuilder().code(404).message(Constants.MESSAGE_CODE404).build());
            add(new ResponseMessageBuilder().code(404).message(Constants.MESSAGE_CODE500).build());
        }};
    }
}