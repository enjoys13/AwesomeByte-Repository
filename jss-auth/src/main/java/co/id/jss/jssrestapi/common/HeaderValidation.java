package co.id.jss.jssrestapi.common;

import co.id.jss.jssrestapi.common.exception.VarException;
import co.id.jss.jssrestapi.common.misc.CommonUtils;
import co.id.jss.jssrestapi.common.misc.Constants;
import co.id.jss.jssrestapi.domain.Client;
import co.id.jss.jssrestapi.domain.User;
import co.id.jss.jssrestapi.domain.UserRole;
import co.id.jss.jssrestapi.jwt.CustomUserDetails;
import co.id.jss.jssrestapi.jwt.JwtClaimDTO;
import co.id.jss.jssrestapi.jwt.JwtTokenEngine;
import co.id.jss.jssrestapi.service.AuditTrailService;
import co.id.jss.jssrestapi.service.UserService;
import co.id.jss.jssrestapi.service.VersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
@Transactional
public class HeaderValidation {

    private final Logger LOG = LoggerFactory.getLogger(HeaderValidation.class);

    private final CommonUtils commonUtils;

    private final JwtTokenEngine jwtTokenEngine;

    private final UserService userService;

    private final VersionService versionService;

    private final AuditTrailService auditTrailService;

    @Autowired
    public HeaderValidation(CommonUtils commonUtils, JwtTokenEngine jwtTokenEngine, UserService userService,
                            VersionService versionService, AuditTrailService auditTrailService) {

        this.commonUtils = commonUtils;
        this.jwtTokenEngine = jwtTokenEngine;
        this.userService = userService;
        this.versionService = versionService;
        this.auditTrailService = auditTrailService;
    }

    /**
     * check request header contain of client_id and client_version
     * these client should be active and version match with database
     * if not should throws error
     *
     * //@param request
     * //@throws VarException
     */
    public void headerClient(HttpServletRequest request) throws VarException {
        try {
            String clientId = request.getHeader(Constants.HEADER_CLIENT_ID);
            String clientVersion = request.getHeader(Constants.HEADER_CLIENT_VERSION);

            LOG.info("==========CLIENT_ID=============== " + clientId);
            LOG.info("==========CLIENT_VERSION========== " + clientVersion);

            if (commonUtils.isNull(clientId) || commonUtils.isNull(clientVersion)) {
                throw new Exception(Constants.ERROR_HEADER_CLIENT_NOT_FOUND);
            } else {
                Optional<Client> registeredVersion = versionService.getVersionByIdAndIsActive(clientId, true);
                if (!registeredVersion.isPresent()) {
                    throw new Exception(Constants.ERROR_HEADER_CLIENT_NOT_FOUND_OR_NOT_ACTIVE);
                } else {
                    if (!clientVersion.equalsIgnoreCase(registeredVersion.get().getVersion())) {
                        throw new Exception((Constants.ERROR_HEADER_CLIENT_VERSION_NOT_MATCH));
                    }
                }
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(),ex);
            throw new VarException(
                    HttpStatus.BAD_REQUEST,
                    ex.getMessage().startsWith(Constants.PREFIX_KNOWN_ERROR) ? ex.getMessage() : Constants.ERROR_GENERAL);
        }
    }

    /**
     * check request header jwt contain of username, userid and role
     * validate userid exist or not
     * vaildate userid blocked or not
     * validate user id active or not
     * validate jwt session active or not
     * validate uri access authorized or not
     * and set to spring security context holder
     *
     * //@param request
     * //@throws VarException
     */
    public void headerJwt(HttpServletRequest request) throws VarException {

        JwtClaimDTO claim = jwtTokenEngine.deGenerate(request.getHeader(Constants.JWT_AUTHORIZATION), request.getRequestURI());

        claim.setCurrentURI(request.getRequestURI());

        Optional<User> userOptional = userService.getUser(claim.getUserId());

        if(!userOptional.isPresent()){
            throw new VarException(
                    HttpStatus.BAD_REQUEST,
                    claim.getCurrentURI(),
                    Constants.ERROR_USER_NOT_FOUND);
        }

        User user = userOptional.get();

        String sessionId = user.getSessionId();

        if (user.isBlock()) {
            throw new VarException(
                    HttpStatus.UNAUTHORIZED,
                    claim.getCurrentURI(),
                    Constants.ERROR_USER_IS_BLOCKED);
        } else if (!user.isActive()) {
            throw new VarException(
                    HttpStatus.UNAUTHORIZED,
                    claim.getCurrentURI(),
                    Constants.ERROR_USER_NOT_ACTIVE);
        } else if (sessionId == null) {
            throw new VarException(
                    HttpStatus.UNAUTHORIZED,
                    claim.getCurrentURI(),
                    Constants.ERROR_USER_ALREADY_LOGGED_OUT);
        } else if (!sessionId.equalsIgnoreCase(claim.getSessionId())) {
            throw new VarException(
                    HttpStatus.UNAUTHORIZED,
                    claim.getCurrentURI(),
                    Constants.ERROR_USER_SESSION_NOT_MATCH);
        } else if (user.getSessionExp().isBefore(LocalDateTime.now())) {
            throw new VarException(
                    HttpStatus.UNAUTHORIZED,
                    claim.getCurrentURI(),
                    Constants.ERROR_USER_SESSION_EXPIRED);
        } else if (user.getPasswordExp().isBefore(LocalDateTime.now())) {
            throw new VarException(
                    HttpStatus.UNAUTHORIZED,
                    claim.getCurrentURI(),
                    Constants.ERROR_USER_PASSWORD_EXPIRED);
        }

        String userlogin = user.getUserlogin();

        Long id = user.getId();

        Collection<UserRole> userRoleList = user.getUserRoleById();
        Collection<UserRole> userRoleListRemoved = new ArrayList<>();

        for(UserRole userRole : userRoleList){

            if(!userRole.getRoleByRoleId().isActive()){

                userRoleListRemoved.add(userRole);
            }
        }

        userRoleList.removeAll(userRoleListRemoved);
        userRoleListRemoved.clear();

        UserDetails userDetails = CustomUserDetails.createContextHolderUser(userlogin, id, userRoleList, sessionId, request.getRequestURI());

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        auditTrailService.addAuditTrail(user, request.getRequestURI(), request.getMethod());
    }
}
