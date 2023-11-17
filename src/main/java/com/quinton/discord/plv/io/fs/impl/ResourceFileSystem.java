package com.quinton.discord.plv.io.fs.impl;

import com.quinton.discord.plv.io.fs.ApplicationFileSystem;

import java.util.Objects;

/**
 * Singleton class representing a file system for handling resources within the shared file system.
 * Extends {@link ApplicationFileSystem}.
 */
public class ResourceFileSystem extends ApplicationFileSystem {

    /**
     * Singleton instance of the ResourceFileSystem class.
     */
    private static ResourceFileSystem instance = null;

    /**
     * Gets the singleton instance of the ResourceFileSystem class.
     *
     * @return The singleton instance of ResourceFileSystem.
     */
    public static ResourceFileSystem getInstance() {
        if (instance == null)
            instance = new ResourceFileSystem();
        return instance;
    }

    /**
     * Private constructor for the ResourceFileSystem class.
     * Initializes the instance with the base path for resources.
     */
    private ResourceFileSystem() {
        super(Objects.requireNonNull(ResourceFileSystem.class.getClassLoader().getResource("")).getFile());
    }
}

