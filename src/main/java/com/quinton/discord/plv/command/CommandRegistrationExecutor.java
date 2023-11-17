package com.quinton.discord.plv.command;

import com.quinton.discord.plv.io.fs.impl.ResourceFileSystem;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;

/**
 * Executor for executing command registrations.
 */
public class CommandRegistrationExecutor {

    /**
     * Executes the given command registration with the command names retrieved from the specified directory.
     *
     * @param registration       The {@link CommandRegistration} to be executed.
     * @param commandDirectoryName The name of the directory containing command files.
     */
    public void executeRegistration(CommandRegistration registration, String commandDirectoryName) {
        List<String> commandNames = this.getCommandNames(commandDirectoryName);
        if (commandNames != null) {
            registration.register(commandNames);
        }
    }

    /**
     * Gets the names of command files from the specified command directory.
     *
     * @param commandDirectoryName The name of the command directory.
     * @return A list of command file names, or null if the directory is empty or doesn't exist.
     */
    private List<String> getCommandNames(String commandDirectoryName) {
        File commandDirectory = ResourceFileSystem.getInstance().getDirectory(commandDirectoryName);
        File[] commandFiles = commandDirectory.listFiles();
        if (commandFiles != null) {
            return Arrays.stream(commandFiles)
                    .map(File::getName)
                    .collect(Collectors.toList());
        }
        return null;
    }
}

