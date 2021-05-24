package ru.strategy48.ejudge.printer.server.exceptions;

public class InvalidServerConfigException extends WebPrinterServerException {
    public InvalidServerConfigException(final String message) {
        super("Invalid server config: " + message);
    }

    public InvalidServerConfigException(final String message, final Throwable cause) {
        super("Invalid server config: " + message, cause);
    }
}
