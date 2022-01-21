package co.id.jss.jssrestapi.config.swagger;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.TypeNameExtractor;

@Component
@Order(/*Ordered.LOWEST_PRECEDENCE*/)
@Profile({"local", "development"})
public class AppSwaggerPageableConfig extends SwaggerPageableParameterReader{

    @Autowired
    public AppSwaggerPageableConfig(TypeNameExtractor nameExtractor, TypeResolver resolver) {
        super(nameExtractor, resolver);
    }
}
