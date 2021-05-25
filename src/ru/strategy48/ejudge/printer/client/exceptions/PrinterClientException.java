package ru.strategy48.ejudge.printer.client.exceptions;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 * Describes {@link Exception} that can be thrown when some error happened while
 * {@link ru.strategy48.ejudge.printer.client.PrintingClient} has been working
 */
public class PrinterClientException extends Exception {
    public PrinterClientException(final String message) {
        super("Printer client error: " + message);
    }

    public PrinterClientException(final String message, final Throwable cause) {
        super("Printer client error: " + message, cause);
    }
}
