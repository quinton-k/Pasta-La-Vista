package com.quinton.discord.plv.command;

import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import reactor.core.publisher.Mono;

public interface SlashCommand {

    Mono<Void> on(ApplicationCommandInteractionEvent event);
}
