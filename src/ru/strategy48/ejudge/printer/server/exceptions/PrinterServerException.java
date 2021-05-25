package ru.strategy48.ejudge.printer.server.exceptions;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 * Describes {@link Exception} that can be thrown if some error happened in
 * {@link ru.strategy48.ejudge.printer.server.PrintingServer}
 */
public class PrinterServerException extends Exception {
    public PrinterServerException(final String message) {
        super("Printer server error: " + message);
    }

    public PrinterServerException(final String message, final Throwable cause) {
        super("Printer server error: " + message, cause);
    }
}
