/*
 * MIT License
 *
 * Copyright (c) 2021 Owen Hayes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
