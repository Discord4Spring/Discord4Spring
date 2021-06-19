package net.hypixel.floppybot.event;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface MessageListener {

    String getPrefix();
    Function<Message, Mono<Void>> getFunction();

    default String parseCommand(String command) {
        return command.replaceAll(getPrefix() + " ", "");
    }

    default Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().startsWith(getPrefix()))
                .flatMap(Message::getChannel)
                .flatMap(channel -> getFunction().apply(eventMessage));
    }
}
