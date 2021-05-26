package ru.strategy48.ejudge.printer.client;

import org.apache.http.HttpResponse;
import ru.strategy48.ejudge.printer.client.exceptions.PrinterClientException;
import ru.strategy48.ejudge.printer.client.exceptions.WebPrinterClientException;
import ru.strategy48.ejudge.printer.client.objects.ClientConfig;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 * Class for talking with printing server
 */
public class PrintingClient implements AutoCloseable {
    private final ClientConfig config;
    private final CloseableHttpClient client = HttpClients.createDefault();
    private int counter = 0;

    /**
     * Constructs client using {@link ClientConfig}
     *
     * @param config given client config
     */
    public PrintingClient(final ClientConfig config) {
        this.config = config;
    }

    /**
     * Starts talking with server
     *
     * @throws PrinterClientException if a fatal error happened while talking with server
     */
    public void startListening() throws PrinterClientException {
        Thread thread = new Thread(() -> {
            while (true) {
                String source;

                try {
                    source = getSource();
                } catch (WebPrinterClientException ignored) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored1) {
                    }
                    continue;
                }
                if (source.isEmpty()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    continue;
                }

                StringBuilder sourceToPrint = new StringBuilder();
                PrintService printer = PrintServiceLookup.lookupDefaultPrintService();
                
                for (DocFlavor curFlavor : printer.getSupportedDocFlavors()) {
                    System.out.println(curFlavor.getMediaType() + " " + curFlavor.getMimeType() + " " + curFlavor.getRepresentationClassName());
                }

                sourceToPrint.append("Принтер: ").append(printer.getName());
                sourceToPrint.append(System.lineSeparator());
                sourceToPrint.append(System.lineSeparator());
                sourceToPrint.append(source);

                source = sourceToPrint.toString();

                PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
                attributeSet.add(new Copies(1));
                attributeSet.add(MediaSizeName.ISO_A4);

                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
                Doc doc = new SimpleDoc(new ByteArrayInputStream(source.getBytes()), flavor, null);
                DocPrintJob job = printer.createPrintJob();

                counter++;
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(counter) + ".txt"))) {
                    writer.append(source);
                } catch (IOException e) {
                    System.out.println("Cannot save file!");
                }

                System.out.println("Printing code using: " + printer.getName());
                try {
                    job.print(doc, attributeSet);
                } catch (PrintException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new PrinterClientException("Thread was interrupted: " + e.getMessage(), e);
        }
    }

    private String getSource() throws WebPrinterClientException {
        try {
            HttpResponse response = sendPost();
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                return "";
            }
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new WebPrinterClientException("Error happened while getting GET response: " + e.getMessage(), e);
        }
    }

    private HttpResponse sendPost() throws WebPrinterClientException {
        HttpPost post = new HttpPost(config.getPrinterURL());

        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("token", config.getToken()));

        try {
            post.setEntity(new UrlEncodedFormEntity(parameters));
        } catch (UnsupportedEncodingException e) {
            throw new WebPrinterClientException("Cannot encode parameters: " + e.getMessage(), e);
        }

        try {
            return client.execute(post);
        } catch (IOException e) {
            throw new WebPrinterClientException("Error happened while executing POST query: " + e.getMessage(), e);
        }
    }

    private HttpEntity sendGet() throws WebPrinterClientException {
        HttpGet get = new HttpGet(config.getPrinterURL());

        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("token", config.getToken()));

        URI uri;
        try {
            uri = new URIBuilder(get.getURI()).addParameters(parameters).build();
        } catch (URISyntaxException e) {
            throw new WebPrinterClientException("Error happened when creating GET query: " + e.getMessage(), e);
        }
        get.setURI(uri);

        try {
            CloseableHttpResponse response = client.execute(get);
            return response.getEntity();
        } catch (IOException e) {
            throw new WebPrinterClientException("Error happened while executing GET query: " + e.getMessage(), e);
        }
    }

    @Override
    public void close() throws Exception {
        client.close();
    }
}
