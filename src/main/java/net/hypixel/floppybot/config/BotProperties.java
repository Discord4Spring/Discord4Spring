package net.hypixel.floppybot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("bot")
public class BotProperties {
    private String token;

    @Getter
    @Setter
    static class Embed {
        private String title;
        private String description;

        @Getter
        @Setter
        static class Thumbnail {
            private String url;
        }

        @Getter
        @Setter
        static class Footer {
            private String text;
        }

        private Thumbnail thumbnail;
        private Footer footer;

        private Integer color;
    }

    private Embed embed;
}
