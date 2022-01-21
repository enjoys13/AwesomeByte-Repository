package co.id.jss.jssrestapi.common;

import co.id.jss.jssrestapi.common.exception.VarException;
import co.id.jss.jssrestapi.common.misc.Constants;
import co.id.jss.jssrestapi.jwt.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {

    private final Logger LOG = LoggerFactory.getLogger(AuthUtils.class);

    public CustomUserDetails getCurrentUserDetail() throws VarException {
        CustomUserDetails details /*= null*/;

        try {
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            details = (CustomUserDetails) authentication.getPrincipal();

        } catch (Exception ex) {

            //FOR ANY ERROR BECAUSE CAN'T GET SECURITY HOLDER THROW URI AS "/"
            LOG.error(ex.getMessage(), ex);

            throw new VarException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_USER_DETAIL);
        }
        return details;
    }
}
