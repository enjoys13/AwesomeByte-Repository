package co.id.jss.jssrestapi.jwt;

import co.id.jss.jssrestapi.common.exception.VarException;
import co.id.jss.jssrestapi.common.misc.Constants;
import co.id.jss.jssrestapi.config.AuthConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenEngine {

    private final Logger LOG = LoggerFactory.getLogger(JwtTokenEngine.class);

    /**
     * Generate Token JWT containing userName, userId, roleId, session
     *
     * //@param claim
     * //@return
     */

    private final AuthConfig authConfig;

    @Autowired
    public JwtTokenEngine(AuthConfig authConfig) {
        this.authConfig = authConfig;
    }

    public String generate(JwtClaimDTO claim) throws VarException {
        Claims claims = Jwts.claims(); //.setSubject(claim.getUsername());
        claims.put(Constants.JWT_CLAIM_USER_ID, claim.getUserId());
        //claims.put(Constants.JWT_CLAIM_ROLE_ID, claim.getRoleId());
        claims.put(Constants.JWT_CLAIM_SESSION, claim.getSessionId());
        //claims.put(Constants.JWT_CLAIM_IS_CUSTOMER, claim.isCustomer());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, authConfig.getJwtSecret())
                .compact();
    }

    public JwtClaimDTO deGenerate(String jwt, String uri) throws VarException {

        try {
            return new JwtClaimDTO(
                    Jwts.parser()
                            .setSigningKey(authConfig.getJwtSecret())
                            .parseClaimsJws(jwt)
                            .getBody()
            );
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new VarException(HttpStatus.UNAUTHORIZED, uri, Constants.ERROR_REQUEST_NOT_AUTHORIZED);
        }

    }
}
