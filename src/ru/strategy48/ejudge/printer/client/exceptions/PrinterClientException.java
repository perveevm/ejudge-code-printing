package ru.strategy48.ejudge.printer.client.exceptions;

public class PrinterClientException extends Exception {
    public PrinterClientException(final String message) {
        super("Printer client error: " + message);
    }

    public PrinterClientException(final String message, final Throwable cause) {
        super("Printer client error: " + message, cause);
    }
}
