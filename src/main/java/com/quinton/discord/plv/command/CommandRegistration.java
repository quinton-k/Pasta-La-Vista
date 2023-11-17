package com.quinton.discord.plv.command;

import java.util.List;

/**
 * Represents the ability to register commands.
 */
public interface CommandRegistration {

    /**
     * Registers commands based on the provided list of file names.
     *
     * @param fileNames The list of file names representing the commands to be registered.
     */
    void register(List<String> fileNames);
}

