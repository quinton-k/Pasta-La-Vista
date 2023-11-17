package com.quinton.discord.plv.event.listener.impl;

import com.quinton.discord.plv.event.listener.EventListener;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import reactor.core.publisher.Flux;

public class ChatInputInteractionEventListener implements EventListener<ChatInputInteractionEvent> {
    @Override
    public Flux<ChatInputInteractionEvent> on(ChatInputInteractionEvent event) {
        return Flux.empty();
    }
}
