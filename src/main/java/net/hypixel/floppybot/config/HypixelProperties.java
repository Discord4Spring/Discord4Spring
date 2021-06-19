package net.hypixel.floppybot.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Getter
@Configuration
@ConfigurationProperties("hypixel")
public class HypixelProperties {
    private UUID token;

    public void setToken(String token) {
        this.token = UUID.fromString(token);
    }
}
