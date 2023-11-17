package com.quinton.discord.plv.event.listener.impl;

import com.quinton.discord.plv.event.listener.EventListener;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.guild.GuildCreateEvent;
import reactor.core.publisher.Flux;

/**
 * Event listener for handling Guild Create events.
 */
public class GuildCreateEventListener implements EventListener<GuildCreateEvent> {

    /**
     * Callback method invoked when a Guild Create event occurs.
     *
     * @param event The {@link GuildCreateEvent} representing the Guild Create event.
     */
    @Override
    public Flux<GuildCreateEvent> on(GuildCreateEvent event) {
        System.out.println("Guild Create Event: " + event.getGuild());
        registerGuildCommands(event.getClient(), event.getGuild().getId().asLong(), event.getClient().getSelfId().asLong());
        return Flux.empty();
    }

    /**
     * Registers guild-specific commands based on the command names retrieved from the specified directory.
     *
     * @param client        The {@link GatewayDiscordClient} instance.
     * @param guildId       The ID of the guild for which commands will be registered.
     * @param applicationId The ID of the Discord application.
     */
    private void registerGuildCommands(GatewayDiscordClient client, long guildId, long applicationId) {
//        CommandRegistrationExecutor registrationExecutor = new CommandRegistrationExecutor();
//        registrationExecutor.executeRegistration(
//                new GuildCommandRegistration(applicationId, guildId,
//                        new CommandRegistrar(client.getRestClient(), "commands/guild/")), "guild"
//        );
    }
}
