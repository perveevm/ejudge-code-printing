package ru.strategy48.ejudge.printer.server.exceptions;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 * Describes {@link Exception} that can be thrown if an error happened in web side of
 * {@link ru.strategy48.ejudge.printer.server.PrintingServer}
 */
public class WebPrinterServerException extends PrinterServerException {
    public WebPrinterServerException(final String message) {
        super("Error happened while working with web printer server: " + message);
    }

    public WebPrinterServerException(final String message, final Throwable cause) {
        super("Error happened while working with web printer server: " + message, cause);
    }
}
