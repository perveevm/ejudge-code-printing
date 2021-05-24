package ru.strategy48.ejudge.printer.server.objects;

public class PrinterQuery {
    private final User user;
    private final String source;

    public PrinterQuery(final User user, final String source) {
        this.user = user;
        this.source = source;
    }

    public User getUser() {
        return user;
    }

    public String getSource() {
        return source;
    }
}
