package ru.strategy48.ejudge.printer.server.exceptions;

public class PrinterServerException extends Exception {
    public PrinterServerException(final String message) {
        super("Printer server error: " + message);
    }

    public PrinterServerException(final String message, final Throwable cause) {
        super("Printer server error: " + message, cause);
    }
}
