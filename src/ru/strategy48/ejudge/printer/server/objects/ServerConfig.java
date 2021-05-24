package ru.strategy48.ejudge.printer.server.objects;

import java.nio.file.Path;

public class ServerConfig {
    private final String host;
    private final int port;

    private final Path printerDirectory;

    private final Path printPageHtmlPath;
    private final Path successHtmlPath;
    private final Path failHtmlPath;

    private final Path usersListPath;
    private final Path tokensListPath;

    public ServerConfig(final String host, final int port, final Path printerDirectory,
                        final Path printPageHtmlPath, final Path successHtmlPath, final Path failHtmlPath,
                        final Path usersListPath, final Path tokensListPath) {
        this.host = host;
        this.port = port;

        this.printerDirectory = printerDirectory;

        this.printPageHtmlPath = printPageHtmlPath;
        this.successHtmlPath = successHtmlPath;
        this.failHtmlPath = failHtmlPath;

        this.usersListPath = usersListPath;
        this.tokensListPath = tokensListPath;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public Path getPrinterDirectory() {
        return printerDirectory;
    }

    public Path getPrintPageHtmlPath() {
        return printPageHtmlPath;
    }

    public Path getUsersListPath() {
        return usersListPath;
    }

    public Path getSuccessHtmlPath() {
        return successHtmlPath;
    }

    public Path getFailHtmlPath() {
        return failHtmlPath;
    }

    public Path getTokensListPath() {
        return tokensListPath;
    }
}
