package ru.strategy48.ejudge.printer.client.objects;

public class ClientConfig {
    private final String token;
    private final String printerURL;

    public ClientConfig(final String token, final String printerURL) {
        this.token = token;
        this.printerURL = printerURL;
    }

    public String getToken() {
        return token;
    }

    public String getPrinterURL() {
        return printerURL;
    }
}
