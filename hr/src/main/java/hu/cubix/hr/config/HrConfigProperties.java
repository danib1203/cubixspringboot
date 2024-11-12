package hu.cubix.hr.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "hr.jwt")
public class HrConfigProperties {

    private String secret;
    private int expiryInterval;
    private String issuer;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getExpiryInterval() {
        return expiryInterval;
    }

    public void setExpiryInterval(int expiryInterval) {
        this.expiryInterval = expiryInterval;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

}
