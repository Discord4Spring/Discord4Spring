package net.hypixel.floppybot.config;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class BotConfiguration {

    @Autowired
    private BotProperties properties;

    @Bean
    public GatewayDiscordClient gatewayDiscordClient() {
        return DiscordClientBuilder.create(properties.getToken())
                .build()
                .login()
                .block();
    }

    @Bean
    public Consumer<EmbedCreateSpec> embedCreateSpecConsumer() {
        return embedCreateSpec -> embedCreateSpec
            .setTitle(properties.getEmbed().getTitle())
            .setDescription(properties.getEmbed().getDescription())
            .setThumbnail(properties.getEmbed().getThumbnail().getUrl())
            .setFooter(properties.getEmbed().getFooter().getText(), null)
            .setColor(Color.GREEN);
    }

}
