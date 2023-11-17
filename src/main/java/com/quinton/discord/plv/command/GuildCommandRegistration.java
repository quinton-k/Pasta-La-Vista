package com.quinton.discord.plv.command;

import java.io.IOException;
import java.util.List;

/**
 * Implementation of {@link CommandRegistration} for registering guild-specific commands.
 */
public class GuildCommandRegistration implements CommandRegistration {

    /**
     * Registers guild-specific commands based on the provided list of file names.
     *
     * @param fileNames The list of file names representing the guild-specific commands to be registered.
     */
    @Override
    public void register(List<String> fileNames) {
        try {
            registrar.registerGuildCommands(guildId,applicationId, fileNames);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructs a new GuildCommandRegistration with the given guild ID and {@link CommandRegistrar}.
     *
     * @param applicationId The ID of the application
     * @param guildId   The ID of the guild for which commands will be registered.
     * @param registrar The {@link CommandRegistrar} used for registering guild-specific commands.
     */
    public GuildCommandRegistration(long applicationId,long guildId, CommandRegistrar registrar) {
        this.applicationId = applicationId;
        this.guildId = guildId;
        this.registrar = registrar;
    }

    /**
     * The ID of the application
     */
    private final long applicationId;

    /**
     * The ID of the guild for which commands will be registered.
     */
    private final long guildId;

    /**
     * The {@link CommandRegistrar} used for registering guild-specific commands.
     */
    private final CommandRegistrar registrar;
}

