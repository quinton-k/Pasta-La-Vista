package com.quinton.discord.plv.io.fs;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * A FileSearchVisitor is a FileVisitor implementation that searches for a specific file by name.
 */
public class FileSearchVisitor extends SimpleFileVisitor<Path> {
    private final String fileName; // The name of the file to search for.
    private boolean fileFound;      // Indicates if the file has been found.
    private File foundFile;         // The found File object.

    /**
     * Constructs a new FileSearchVisitor for searching a specific file by name.
     *
     * @param fileName The name of the file to search for.
     */
    public FileSearchVisitor(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Called when visiting a file. If the file's name matches the target file name, it's considered found.
     *
     * @param file  The current file being visited.
     * @param attrs The attributes of the file.
     * @return TERMINATE to stop traversal if the file is found, CONTINUE otherwise.
     * @throws IOException If an I/O error occurs while visiting the file.
     */
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.getFileName().toString().equalsIgnoreCase(fileName)) {
            fileFound = true;
            foundFile = file.toFile();
            return FileVisitResult.TERMINATE; // Stop traversal once the file is found
        }
        return FileVisitResult.CONTINUE;
    }

    /**
     * Called when visiting a file fails. Continue traversal in case of errors.
     *
     * @param file The file that caused the visit failure.
     * @param exc  The exception that occurred.
     * @return CONTINUE to continue traversal.
     * @throws IOException If an I/O error occurs during the visit failure.
     */
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    /**
     * Checks if the target file has been found.
     *
     * @return true if the file has been found, false otherwise.
     */
    public boolean isFileFound() {
        return fileFound;
    }

    /**
     * Get the File object representing the found file.
     *
     * @return The File object of the found file or null if not found.
     */
    public File getFoundFile() {
        return foundFile;
    }
}


