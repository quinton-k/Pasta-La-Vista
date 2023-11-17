package com.quinton.discord.plv.event.listener.impl;

import com.quinton.discord.plv.event.listener.EventListener;
import discord4j.core.event.domain.interaction.ChatInputAutoCompleteEvent;
import reactor.core.publisher.Flux;

public class ChatInputAutoCompleteEventListener implements EventListener<ChatInputAutoCompleteEvent> {
    @Override
    public Flux<ChatInputAutoCompleteEvent> on(ChatInputAutoCompleteEvent event) {
        return Flux.empty();
    }
}
