package com.quinton.discord.plv.io.db;

import com.quinton.discord.plv.command.GuildCommandPermission;
import com.quinton.pasta.io.db.dao.impl.AbstractManyToManyDataAccessObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuildCommandPermissionDAO extends AbstractManyToManyDataAccessObject<GuildCommandPermission, Long> {

    @Override
    public void create(GuildCommandPermission permission) {
        try {
            String sql = "INSERT INTO guild_command_permission (guild_id, command_id) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, permission.getGuildId());
                statement.setInt(2, permission.getCommandId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    @Override
    public void update(GuildCommandPermission permission) {
        try {
            String sql = "UPDATE guild_command_permission SET guild_id = ?, command_id = ? WHERE guild_id = ? AND command_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, permission.getGuildId());
                statement.setInt(2, permission.getCommandId());
                statement.setLong(3, permission.getGuildId());
                statement.setInt(4, permission.getCommandId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    @Override
    public List<GuildCommandPermission> readRelatedEntities(Long guildId) {
        List<GuildCommandPermission> permissions = new ArrayList<>();
        try {
            String sql = "SELECT command_id FROM guild_command_permission WHERE guild_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, guildId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int commandId = resultSet.getInt("command_id");
                        permissions.add(new GuildCommandPermission(guildId, commandId));
                    }
                }
            }
        } catch (SQLException e) {
            // Handle exceptions
            e.printStackTrace();
        }
        return permissions;
    }

    @Override
    public void deleteRelatedEntities(Long guildId) {
        try {
            String sql = "DELETE FROM guild_command_permission WHERE guild_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, guildId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    @Override
    protected void createTableIfNotExists() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS guild_command_permission (" +
                    "guild_id INTEGER NOT NULL," +
                    "command_id INTEGER NOT NULL," +
                    "PRIMARY KEY (guild_id, command_id)" +
                    ")";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    public GuildCommandPermissionDAO(Connection connection) {
        this.connection = connection;
        this.createTableIfNotExists();
    }

    private final Connection connection;

}



