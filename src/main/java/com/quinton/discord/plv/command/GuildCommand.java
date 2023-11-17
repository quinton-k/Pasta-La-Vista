package com.quinton.discord.plv.command;

/**
 * Represents a guild-specific command with an ID.
 */
public class GuildCommand {

    /**
     * Gets the ID of the guild command.
     *
     * @return The unique ID of the guild command.
     */
    public long getId() {
        return id;
    }

    /**
     * Default constructor for the GuildCommand class.
     */
    public GuildCommand() {
        // Default constructor
    }

    /**
     * Constructs a new GuildCommand with the given ID.
     *
     * @param id The unique ID of the guild command.
     */
    public GuildCommand(long id) {
        this.id = id;
    }

    /**
     * The unique ID of the guild command.
     */
    private long id;
}

