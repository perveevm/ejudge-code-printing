package ru.strategy48.ejudge.printer.client.utils;

import ru.strategy48.ejudge.printer.client.objects.ClientConfig;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 * Some useful methods working with XML files
 */
public class XMLUtils {
    private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    /**
     * Loads {@link ClientConfig} from given XML config file
     *
     * @param configPath a {@link Path} to config file
     * @return config parsed into {@link ClientConfig} instance
     * @throws ParserConfigurationException if there is a problem with parsing config file
     * @throws IOException                  if there is a problem with parsing config file
     * @throws SAXException                 if there is a problem with parsing config file
     */
    public static ClientConfig loadClientConfig(final Path configPath)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(configPath.toFile());

        document.getDocumentElement().normalize();

        Node tokenNode = document.getElementsByTagName("token").item(0);
        Node urlNode = document.getElementsByTagName("url").item(0);

        return new ClientConfig(tokenNode.getTextContent(), urlNode.getTextContent());
    }
}
