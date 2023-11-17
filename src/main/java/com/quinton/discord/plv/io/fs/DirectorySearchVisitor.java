package com.quinton.discord.plv.io.fs;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * A visitor for searching a directory during file tree traversal.
 */
public class DirectorySearchVisitor extends SimpleFileVisitor<Path> {
    private final String directoryName;
    private File foundDirectory;

    /**
     * Constructs a new DirectorySearchVisitor for the specified directory name.
     *
     * @param directoryName The name of the directory to search for.
     */
    public DirectorySearchVisitor(String directoryName) {
        this.directoryName = directoryName;
    }

    /**
     * Called before visiting a directory. Terminates traversal if the desired directory is found.
     *
     * @param dir   The directory being visited.
     * @param attrs The basic file attributes of the directory.
     * @return FileVisitResult.TERMINATE if the desired directory is found, FileVisitResult.CONTINUE otherwise.
     */
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        if (dir.getFileName().toString().equalsIgnoreCase(directoryName)) {
            foundDirectory = dir.toFile();
            return FileVisitResult.TERMINATE; // Stop traversal once directory is found
        }
        return FileVisitResult.CONTINUE;
    }

    /**
     * Get the found directory, if any.
     *
     * @return The found directory, or null if not found.
     */
    public File getFoundDirectory() {
        return foundDirectory;
    }
}


