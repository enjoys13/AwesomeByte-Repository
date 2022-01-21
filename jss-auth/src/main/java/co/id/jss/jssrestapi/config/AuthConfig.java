package co.id.jss.jssrestapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthConfig {

    private int jwtExpiration;
    private int otpExpiration;
    private int passwordExpiration;
    private String jwtSecret;

    public int getJwtExpiration() {
        return jwtExpiration;
    }

    public void setJwtExpiration(int jwtExpiration) {
        this.jwtExpiration = jwtExpiration;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public int getOtpExpiration() {
        return otpExpiration;
    }

    public void setOtpExpiration(int otpExpiration) {
        this.otpExpiration = otpExpiration;
    }

    public int getPasswordExpiration() {
        return passwordExpiration;
    }

    public void setPasswordExpiration(int passwordExpiration) {
        this.passwordExpiration = passwordExpiration;
    }
}
