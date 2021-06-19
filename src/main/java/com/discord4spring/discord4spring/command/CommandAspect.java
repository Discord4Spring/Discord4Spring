package com.discord4spring.discord4spring.command;

import com.discord4spring.discord4spring.embed.EmbedBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import com.discord4spring.discord4spring.event.EventListener;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.UnaryOperator;

@Aspect
@Component
public class CommandAspect {

    @Autowired
    @Qualifier("gatewayDiscordClient")
    private GatewayDiscordClient client;

    @Autowired
    private EmbedBuilder embedBuilder;

    public <T extends Event> void addListener(EventListener<T> listener) {
        client.on(listener.getEventType())
                .flatMap(listener::execute)
                .onErrorResume(listener::handleError)
                .subscribe();
    }

    public String parseCommand(Command command, String input) {
        return input.replaceAll(command.prefix() + " ", "");
    }

    public Mono<Message> createMessage(Command command, UnaryOperator<String> function, String message, MessageChannel channel) {
        if (command.hasEmbed()) {
            return channel.createEmbed(embedBuilder.addField("test", function.apply(parseCommand(command, message)), command.inline()).build());
        } else {
            return channel.createMessage(function.apply(parseCommand(command, message)));
        }
    }

    @AfterReturning(value = "@annotation(command)", returning = "function")
    public void addToCommandRegistry(Command command, UnaryOperator<String> function) {
        var listener = new CommandListener(command.prefix(), message ->
                    Mono.just(message)
                    .flatMap(Message::getChannel)
                    .flatMap(channel -> createMessage(command, function, message.getContent(), channel)).then());

        addListener(listener);
    }
}
