package YOUmI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.function.Predicate;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(getSelector())
                .paths(PathSelectors.any())
                .build();
    }

    // swagger에 @RestController, @Controller 어노테이션을 단 것만 노출
    private static Predicate<RequestHandler> getSelector() {
        return RequestHandlerSelectors.withClassAnnotation(RestController.class).or(RequestHandlerSelectors.withClassAnnotation(Controller.class));
    }


}
