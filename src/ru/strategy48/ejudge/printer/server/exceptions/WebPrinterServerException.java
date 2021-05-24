package ru.strategy48.ejudge.printer.server.exceptions;

public class WebPrinterServerException extends PrinterServerException {
    public WebPrinterServerException(final String message) {
        super("Error happened while working with web printer server: " + message);
    }

    public WebPrinterServerException(final String message, final Throwable cause) {
        super("Error happened while working with web printer server: " + message, cause);
    }
}
