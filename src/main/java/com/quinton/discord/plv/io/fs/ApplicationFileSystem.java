package com.quinton.discord.plv.io.fs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ApplicationFileSystem {

    /**
     * Check if a file with the given name exists within the shared file system.
     *
     * @param fileName The name of the file to check.
     * @return true if the file exists, false otherwise.
     */
    public boolean fileExists(String fileName) {
        return this.fileExists(fileName, base);
    }

    /**
     * Get a File object for a file with the given name within the shared file system.
     *
     * @param fileName The name of the file to get.
     * @return The File object representing the file, or null if it doesn't exist.
     */
    public File getFile(String fileName) {
        return this.getFile(fileName, base);
    }

    /**
     * Get a File object for a directory with the given name within the shared file system.
     *
     * @param directoryName The name of the directory to get.
     * @return The File object representing the directory, or null if it doesn't exist.
     */
    public File getDirectory(String directoryName) {
        return this.getDirectory(directoryName, base);
    }

    /**
     * Checks if a file with the given name exists within the shared file system.
     *
     * @param fileName The name of the file to check.
     * @param path     The base path of the file system.
     * @return true if the file exists, false otherwise.
     */
    private boolean fileExists(String fileName, Path path) {
        try {
            FileSearchVisitor visitor = new FileSearchVisitor(fileName);
            Files.walkFileTree(path, visitor);
            return visitor.isFileFound();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get a File object for a file with the given name within the shared file system.
     *
     * @param fileName The name of the file to get.
     * @param path     The base path of the file system.
     * @return The File object representing the file, or null if it doesn't exist.
     */
    private File getFile(String fileName, Path path) {
        try {
            FileSearchVisitor visitor = new FileSearchVisitor(fileName);
            Files.walkFileTree(path, visitor);
            if (visitor.isFileFound()) {
                return visitor.getFoundFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get a File object for a directory with the given name within the shared file system.
     *
     * @param directoryName The name of the directory to get.
     * @param path          The base path of the file system.
     * @return The File object representing the directory, or null if it doesn't exist.
     */
    private File getDirectory(String directoryName, Path path) {
        try {
            DirectorySearchVisitor visitor = new DirectorySearchVisitor(directoryName);
            Files.walkFileTree(path, visitor);
            if (visitor.getFoundDirectory() != null) {
                return visitor.getFoundDirectory();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ApplicationFileSystem(String root) {
        this.base = Paths.get(new File(root).toURI());
    }


    private final Path base;
}


