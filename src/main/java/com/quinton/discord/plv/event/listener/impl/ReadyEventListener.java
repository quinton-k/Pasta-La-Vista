package com.quinton.discord.plv.event.listener.impl;

import com.quinton.discord.plv.command.CommandRegistrar;
import com.quinton.discord.plv.command.CommandRegistrationExecutor;
import com.quinton.discord.plv.command.GlobalCommandRegistration;
import com.quinton.discord.plv.event.listener.EventListener;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import reactor.core.publisher.Flux;

import java.util.logging.Logger;

/**
 * An implementation of {@link EventListener} for handling {@link ReadyEvent}s.
 */
public class ReadyEventListener implements EventListener<ReadyEvent> {

    /**
     * Callback method invoked when a {@link ReadyEvent} occurs.
     *
     * @param event The {@link ReadyEvent} to be handled.
     */
    @Override
    public Flux<ReadyEvent> on(ReadyEvent event) {
        Logger.getGlobal().info("Logged in as " + event.getSelf().getUsername());
        registerGlobalCommands(event.getClient());
        return Flux.empty();
    }

    /**
     * Registers global commands based on the command names retrieved from the specified directory.
     *
     * @param client The {@link GatewayDiscordClient} instance.
     */
    private void registerGlobalCommands(GatewayDiscordClient client) {
        CommandRegistrationExecutor registrationExecutor = new CommandRegistrationExecutor();
        registrationExecutor.executeRegistration(
                new GlobalCommandRegistration(
                        new CommandRegistrar(client.getRestClient(), "commands/global/")), "global"
        );
    }
}
