package ru.strategy48.ejudge.printer.client.exceptions;

public class WebPrinterClientException extends PrinterClientException {
    public WebPrinterClientException(final String message) {
        super("Error happened while working with web printer client: " + message);
    }

    public WebPrinterClientException(final String message, final Throwable cause) {
        super("Error happened while working with web printer client: " + message, cause);
    }
}
