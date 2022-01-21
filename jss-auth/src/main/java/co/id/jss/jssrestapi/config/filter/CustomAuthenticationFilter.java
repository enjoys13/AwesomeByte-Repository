package co.id.jss.jssrestapi.config.filter;

import co.id.jss.jssrestapi.common.HeaderValidation;
import co.id.jss.jssrestapi.common.exception.VarException;
import co.id.jss.jssrestapi.common.exception.VarExceptionResponse;
import co.id.jss.jssrestapi.common.misc.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final Logger LOG = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

    @Autowired
    private HeaderValidation headerValidation;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        LOG.info("=================== FILTERING ===================");

        LOG.info("============================= CHECK REQUEST PARAM ");

        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            LOG.info("Parameter Name -> " + paramName + ", Value -> " + request.getParameter(paramName));
        }

        String uri = request.getRequestURI();
        try {
            if (Constants.URI_SWAGGER.stream().anyMatch(u -> uri.toLowerCase().startsWith(u.toLowerCase()))) {
                LOG.info("=================== ACCESSING SWAGGER !! THIS SHOULD NOT HAPPENED IN PRODUCTION ");

            } else if (Constants.URI_API_REQUEST_WITHOUT_JWT.stream().anyMatch(u -> uri.toLowerCase().startsWith(u.toLowerCase()))) {
                LOG.info("=================== ACCESSING BY PASSED URI  : " + Constants.URI_API_REQUEST_WITHOUT_JWT);

                headerValidation.headerClient(request);

            } else if (Constants.URI_API_BYPASS_PARAMETER_GET.stream().anyMatch(u -> uri.toLowerCase().startsWith(u.toLowerCase()) && request.getMethod().equalsIgnoreCase(HttpMethod.GET.toString()))){
                LOG.info("=================== ACCESSING BY PASSED URI  : " + Constants.URI_API_BYPASS_PARAMETER_GET);
                LOG.info("=================== ACCESSING BY PASSED URI DO NOT NEED TO CHECK CLIENT VERSION ");

            }else{
                LOG.info("=================== CUSTOM AUTHENTICATION FILTER");

                headerValidation.headerClient(request);
                headerValidation.headerJwt(request);
            }

            filterChain.doFilter(request, response);

        } catch (VarException ex) {
            LOG.info("=================== CUSTOM AUTHENTICATION ERROR");
            LOG.error(ex.getMessage(), ex);
            ObjectMapper mapper = new ObjectMapper();
            VarExceptionResponse customResponse = new VarExceptionResponse(ex, request);
            response.setContentType("application/json");
            response.getWriter().write(mapper.writeValueAsString(customResponse));
            response.setStatus(ex.getHttpStatusCode());
            response.setCharacterEncoding("UTF-8");
            response.getWriter().close();
        }

        /*
        01. permit whitelist  -- api registration, login, otp
        02. check jwt authentication -- jwt session not exist kicked error
        03. from jwt authentication -- set security context -- set username and userid in spring context
        04. from jwt authentication -- check URI access based on RoleDomain
         */
//
//        System.out.println("============================= CHECK REQUEST CUSTOM ");
//        System.out.println("+++++ URI             " + request.getRequestURI());
//        System.out.println("+++++ METHOD          " + request.getMethod());
//        System.out.println("+++++ SESSION ID      " + request.getRequestedSessionId());
//        System.out.println("+++++ AUTH TYPE       " + request.getAuthType());
//        System.out.println("+++++ CONTEXT PATH    " + request.getContextPath());
//        System.out.println("+++++ PATH INFO       " + request.getPathInfo());
//        System.out.println("+++++ REQUEST URL     " + request.getRequestURL());
//        System.out.println("+++++ PATH TRANSLATED " + request.getPathTranslated());
//        System.out.println("+++++ SERVLET PATH    " + request.getServletPath());
    }
}
