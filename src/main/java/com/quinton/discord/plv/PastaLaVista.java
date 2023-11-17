package com.quinton.discord.plv;

import com.quinton.discord.plv.event.EventDispatcher;
import discord4j.core.DiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.core.shard.ShardingStrategy;
import discord4j.gateway.intent.IntentSet;

/**
 * Main class representing the Pasta La Vista application.
 */
public class PastaLaVista {

    /**
     * The entry point of the RSCord application.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        PastaLaVista pastaLaVista = new PastaLaVista();

        pastaLaVista.startDiscordBot();
    }

    /**
     * Starts the Discord bot by creating a Discord client, setting up sharding,
     * and registering an event listener using an {@link EventDispatcher}.
     */
    private void startDiscordBot() {
        DiscordClient.create(System.getenv("BOT_TOKEN"))
                .gateway()
                .setEnabledIntents(
                        IntentSet.all()
                )
                .setSharding(ShardingStrategy.recommended())
                .withGateway(client -> client.on(Event.class)
                        .flatMap(event -> {
                            // Check if the message is a command
//                            if (event instanceof ChatInputInteractionEvent) {
//                                return commandDispatcher.dispatch((ChatInputInteractionEvent) event);
//                            } else {

                                return eventDispatcher.dispatch(event);
//                            }
                        })
                        .then())
                .block();
    }


    /**
     * Constructor for the RSCord class.
     * Initializes the {@link EventDispatcher} for handling Discord events.
     */
    public PastaLaVista() {
        this.eventDispatcher = new EventDispatcher();
//        this.commandDispatcher = new CommandDispatcher();
    }

    /**
     * EventDispatcher instance for handling Discord events.
     */
    private final EventDispatcher eventDispatcher;
//    private final CommandDispatcher commandDispatcher;
}
