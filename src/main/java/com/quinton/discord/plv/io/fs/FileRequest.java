package com.quinton.discord.plv.io.fs;

import java.nio.file.FileSystems;

/**
 * A FileRequest represents a request for a file or directory with a specified path.
 */
public class FileRequest {

    /**
     * Get the path of the file or directory to request.
     *
     * @return The path of the requested file or directory.
     */
    public String getFileRequest() {
        return fileRequest;
    }

    private FileRequest(FileRequestBuilder builder) {
        this.fileRequest = builder.path;
    }

    private final String fileRequest;

    /**
     * A builder class for constructing FileRequest objects.
     */
    public static class FileRequestBuilder {

        private String path = "";

        /**
         * Append a directory to the current path.
         *
         * @param dirName The name of the directory to append.
         * @return This FileRequestBuilder instance for method chaining.
         */
        public FileRequestBuilder getDirectory(String dirName) {
            path = this.path.concat(dirName).concat(FileSystems.getDefault().getSeparator());
            return this;
        }

        /**
         * Append a file or directory to the current path.
         *
         * @param name The name of the file or directory to append.
         * @return This FileRequestBuilder instance for method chaining.
         */
        public FileRequestBuilder getFile(String name) {
            path = this.path.concat(name);
            return this;
        }

        /**
         * Build the FileRequest object based on the provided path.
         *
         * @return A new FileRequest object with the specified path.
         */
        public FileRequest build() {
            return new FileRequest(this);
        }
    }
}

