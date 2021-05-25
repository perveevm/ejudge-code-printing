package ru.strategy48.ejudge.printer.client.exceptions;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 * Describes {@link Exception} that can be thrown when some error happened in the web side of
 * {@link ru.strategy48.ejudge.printer.client.PrintingClient}
 */
public class WebPrinterClientException extends PrinterClientException {
    public WebPrinterClientException(final String message) {
        super("Error happened while working with web printer client: " + message);
    }

    public WebPrinterClientException(final String message, final Throwable cause) {
        super("Error happened while working with web printer client: " + message, cause);
    }
}
