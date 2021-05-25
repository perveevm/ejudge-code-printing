package ru.strategy48.ejudge.printer.client.objects;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 * Describes configuration of {@link ru.strategy48.ejudge.printer.client.PrintingClient}
 *
 * <code>token</code> is an access token for accessing to server
 * <code>printerURL</code> is an URL of server
 */
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
