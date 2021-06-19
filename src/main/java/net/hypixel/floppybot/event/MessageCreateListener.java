package net.hypixel.floppybot.event;

import discord4j.core.event.domain.message.MessageCreateEvent;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public abstract class MessageCreateListener implements EventListener<MessageCreateEvent>, MessageListener {
    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        log.info("Message Created!");
        return processCommand(event.getMessage());
    }
}

