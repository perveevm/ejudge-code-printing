package ru.strategy48.ejudge.printer.server.objects;

public class PrinterToken {
    private final String token;
    private final String name;

    public PrinterToken(final String token, final String name) {
        this.token = token;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }
}
