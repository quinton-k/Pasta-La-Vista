package com.quinton.discord.plv.event.listener.impl;

import com.quinton.discord.plv.event.listener.EventListener;
import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import reactor.core.publisher.Flux;

public class ApplicationCommandInteractionEventListener implements EventListener<ApplicationCommandInteractionEvent> {
    @Override
    public Flux<ApplicationCommandInteractionEvent> on(ApplicationCommandInteractionEvent event) {
        System.out.println("Doing this");
        return Flux.empty();
    }
}
