package ru.strategy48.ejudge.printer.server.exceptions;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 * Describes {@link Exception} that can be thrown if error happened while processing config of
 * {@link ru.strategy48.ejudge.printer.server.PrintingServer}
 */
public class InvalidServerConfigException extends WebPrinterServerException {
    public InvalidServerConfigException(final String message) {
        super("Invalid server config: " + message);
    }

    public InvalidServerConfigException(final String message, final Throwable cause) {
        super("Invalid server config: " + message, cause);
    }
}
