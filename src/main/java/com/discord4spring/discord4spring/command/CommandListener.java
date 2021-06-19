package com.discord4spring.discord4spring.command;

import com.discord4spring.discord4spring.event.MessageCreateListener;
import discord4j.core.object.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Getter
@AllArgsConstructor
public class CommandListener extends MessageCreateListener {
    private final String prefix;
    private final Function<Message, Mono<Void>> function;
}
