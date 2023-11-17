package com.quinton.discord.plv.command;

/**
 * Represents a permission configuration for a specific command within a guild.
 */
public class GuildCommandPermission {

    /**
     * Gets the ID of the command.
     *
     * @return The ID of the command.
     */
    public int getCommandId() {
        return commandId;
    }

    /**
     * Gets the ID of the guild associated with the command permission.
     *
     * @return The ID of the guild.
     */
    public long getGuildId() {
        return guildId;
    }

    /**
     * Constructs a new GuildCommandPermission with the given guild ID and command ID.
     *
     * @param guildId   The ID of the guild.
     * @param commandId The ID of the command.
     */
    public GuildCommandPermission(long guildId, int commandId) {
        this.guildId = guildId;
        this.commandId = commandId;
    }

    /**
     * The ID of the guild associated with the command permission.
     */
    private final long guildId;

    /**
     * The ID of the command.
     */
    private final int commandId;
}

