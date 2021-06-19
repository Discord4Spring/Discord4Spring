package net.hypixel.floppybot.embed;

import discord4j.core.spec.EmbedCreateSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class EmbedBuilder {
    @Autowired
    private Consumer<EmbedCreateSpec> embedCreateSpecConsumer;

    public EmbedBuilder addField(String name, String value, boolean inline) {
        embedCreateSpecConsumer = embedCreateSpecConsumer
                .andThen(embedCreateSpec ->
                        embedCreateSpec.addField(name, value, inline
                        )
                );
        return this;
    }

    public Consumer<EmbedCreateSpec> build() {
        return embedCreateSpecConsumer;
    }
}
