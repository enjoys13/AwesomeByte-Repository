package co.id.jss.jssrestapi.config.swagger;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile({"local", "development"})
public class AppSwaggerConfig extends SwaggerConfiguration {

}
