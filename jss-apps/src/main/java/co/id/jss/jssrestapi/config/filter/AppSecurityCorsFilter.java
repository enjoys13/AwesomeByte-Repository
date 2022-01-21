package co.id.jss.jssrestapi.config.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppSecurityCorsFilter extends SecurityCorsFilter {

}
