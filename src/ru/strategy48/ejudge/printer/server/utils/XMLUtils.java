package ru.strategy48.ejudge.printer.server.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.strategy48.ejudge.printer.server.objects.PrinterToken;
import ru.strategy48.ejudge.printer.server.objects.ServerConfig;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class XMLUtils {
    private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public static ServerConfig loadServerConfig(final Path configPath)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(configPath.toFile());

        document.getDocumentElement().normalize();

        Node hostNode = document.getElementsByTagName("host").item(0);
        Node portNode = document.getElementsByTagName("port").item(0);

        Node printerHtmlPath = document.getElementsByTagName("printer_html").item(0);
        Node successHtmlPath = document.getElementsByTagName("success_html").item(0);
        Node failHtmlPath = document.getElementsByTagName("fail_html").item(0);

        Node usersListPath = document.getElementsByTagName("users_list").item(0);
        Node tokensListPath = document.getElementsByTagName("tokens_list").item(0);

        return new ServerConfig(hostNode.getTextContent(), Integer.parseInt(portNode.getTextContent()),
                Path.of(printerHtmlPath.getTextContent()), Path.of(successHtmlPath.getTextContent()),
                Path.of(failHtmlPath.getTextContent()), Path.of(usersListPath.getTextContent()),
                Path.of(tokensListPath.getTextContent()));
    }

    public static Map<String, PrinterToken> loadPrinterTokens(final Path path)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(path.toFile());

        document.getDocumentElement().normalize();

        NodeList tokensNode = document.getElementsByTagName("token");
        Map<String, PrinterToken> tokens = new HashMap<>();
        for (int i = 0; i < tokensNode.getLength(); i++) {
            String token = ((Element) tokensNode.item(i)).getAttribute("value");
            String name = ((Element) tokensNode.item(i)).getAttribute("name");
            tokens.put(token, new PrinterToken(token, name));
        }

        return tokens;
    }
}
