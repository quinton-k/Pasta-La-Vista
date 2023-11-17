package com.quinton.discord.plv.command;

import com.quinton.discord.plv.io.db.GuildCommandPermissionDAO;
import com.quinton.pasta.io.db.DatabaseAccessManagerFactory;
import com.quinton.pasta.io.db.transaction.impl.ReadAllEntriesTransactionImpl;
import discord4j.common.JacksonResources;
import discord4j.discordjson.json.ApplicationCommandRequest;
import discord4j.rest.RestClient;
import discord4j.rest.service.ApplicationService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Utility class for registering commands, both globally and for specific guilds, in a Discord bot.
 */
public class CommandRegistrar {

    /**
     * Registers guild-specific commands for a specific guild based on the provided list of file names.
     *
     * @param guildId    The ID of the guild to register commands for.
     * @param fileNames  The list of file names representing the commands to be registered.
     * @throws IOException If an I/O error occurs while reading command files.
     */
    public void registerGuildCommands(long guildId,long applicationId,List<String> fileNames) throws IOException {
        final JacksonResources d4jMapper = JacksonResources.create();
        final ApplicationService applicationService = restClient.getApplicationService();

        //Get our commands json from resources as command data
        List<ApplicationCommandRequest> commands = new ArrayList<>();
        for (String json : getCommandsJson(fileNames)) {
            GuildCommand guildCommand = d4jMapper.getObjectMapper()
                    .readValue(json, GuildCommand.class);
            ApplicationCommandRequest request = d4jMapper.getObjectMapper()
                    .readValue(json, ApplicationCommandRequest.class);
            System.out.println("Request: " + request);
            if (this.guildHasCommand(guildId,guildCommand.getId())) {
                commands.add(request); //Add to our array list
            }
        }

        /* Bulk overwrite commands. This is now idempotent, so it is safe to use this even when only 1 command
        is changed/added/removed
        */
        applicationService.bulkOverwriteGuildApplicationCommand(applicationId,guildId, commands)
                .doOnNext(cmd -> Logger.getGlobal().info("Successfully registered Guild Command " + cmd.name() + " for guild " + guildId))
                .doOnError(e -> Logger.getGlobal().warning("Failed to register Guild commands for " + guildId ))
                .subscribe();
    }

    /**
     * Registers global commands based on the provided list of file names.
     *
     * @param fileNames The list of file names representing the commands to be registered globally.
     * @throws IOException If an I/O error occurs while reading command files.
     */
    public void registerGlobalCommands(List<String> fileNames) throws IOException {
        //Create an ObjectMapper that supports Discord4J classes
        final JacksonResources d4jMapper = JacksonResources.create();

        // Convenience variables for the sake of easier to read code below
        final ApplicationService applicationService = restClient.getApplicationService();
        final long applicationId = restClient.getApplicationId().block();

        //Get our commands json from resources as command data
        List<ApplicationCommandRequest> commands = new ArrayList<>();
        for (String json : getCommandsJson(fileNames)) {
//            ApplicationCommandRequest.builder().
            ApplicationCommandRequest request = d4jMapper.getObjectMapper()
                    .readValue(json, ApplicationCommandRequest.class);
            System.out.println("Request: " + request.defaultMemberPermissions());
            commands.add(request); //Add to our array list
        }

        /* Bulk overwrite commands. This is now idempotent, so it is safe to use this even when only 1 command
        is changed/added/removed
        */
        applicationService.bulkOverwriteGlobalApplicationCommand(applicationId, commands)
                .doOnNext(cmd -> Logger.getGlobal().info("Successfully registered Global Command " + cmd.name()))
                .doOnError(e -> Logger.getGlobal().warning("Failed to register global commands: " + e.getMessage()))
                .subscribe();
    }

    private boolean guildHasCommand(long guildId, long commandId) {
       Collection<GuildCommandPermission> permissions = DatabaseAccessManagerFactory.createDatabaseAccessManager("./res/guild_data.db")
                .performTransaction(
                    new ReadAllEntriesTransactionImpl<>(guildId),
                        GuildCommandPermissionDAO.class
                );
        return permissions.stream().anyMatch(permission -> permission.getCommandId() == commandId);
    }

    // Helper methods for reading command files and converting them to a list of strings
    private List<String> getCommandsJson(List<String> fileNames) throws IOException {
        // Confirm that the commands folder exists
        URL url = CommandRegistrar.class.getClassLoader().getResource(commandsFolderName);
        Objects.requireNonNull(url, commandsFolderName + " could not be found");

        //Get all the files inside this folder and return the contents of the files as a list of strings
        List<String> list = new ArrayList<>();
        for (String file : fileNames) {
            String resourceFileAsString = getResourceFileAsString(commandsFolderName + file);
            list.add(Objects.requireNonNull(resourceFileAsString, "Command file not found: " + file));
        }
        return list;
    }

    /**
     * Gets a specific resource file as String
     *
     * @param fileName The file path omitting "resources/"
     * @return The contents of the file as a String, otherwise throws an exception
     */
    private String getResourceFileAsString(String fileName) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream resourceAsStream = classLoader.getResourceAsStream(fileName)) {
            if (resourceAsStream == null) return null;
            try (InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream);
                 BufferedReader reader = new BufferedReader(inputStreamReader)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

    /**
     * Constructs a new CommandRegistrar with the given RestClient and commands folder name.
     *
     * @param restClient        The Discord4J RestClient.
     * @param commandsFolderName The name of the folder containing command files.
     */
    public CommandRegistrar(RestClient restClient, String commandsFolderName) {
        this.restClient = restClient;
        this.commandsFolderName = commandsFolderName;
    }

    /**
     * The name of the folder containing command files.
     */
    private final String commandsFolderName;
    /**
     * The Discord4J RestClient used for interacting with the Discord API.
     */
    private final RestClient restClient;
}
