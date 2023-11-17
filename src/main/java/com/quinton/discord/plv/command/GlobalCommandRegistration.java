package com.quinton.discord.plv.command;

import discord4j.rest.util.Permission;

import java.io.IOException;
import java.util.List;

/**
 * Implementation of {@link CommandRegistration} for registering global commands.
 */
public class GlobalCommandRegistration implements CommandRegistration {

    /**
     * Registers global commands based on the provided list of file names.
     *
     * @param fileNames The list of file names representing the global commands to be registered.
     */
    @Override
    public void register(List<String> fileNames) {
        try {
            Permission.ADMINISTRATOR.getValue();
            registrar.registerGlobalCommands(fileNames);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructs a new GlobalCommandRegistration with the given {@link CommandRegistrar}.
     *
     * @param registrar The {@link CommandRegistrar} used for registering global commands.
     */
    public GlobalCommandRegistration(CommandRegistrar registrar) {
        this.registrar = registrar;
    }

    /**
     * The {@link CommandRegistrar} used for registering global commands.
     */
    private final CommandRegistrar registrar;
}

