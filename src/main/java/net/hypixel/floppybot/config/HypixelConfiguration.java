package net.hypixel.floppybot.config;

import net.hypixel.api.HypixelAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HypixelConfiguration {

    @Bean
    public HypixelAPI hypixelAPI(HypixelProperties properties) {
        return new HypixelAPI(properties.getToken());
    }
}
