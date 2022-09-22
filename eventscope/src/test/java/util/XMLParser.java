package util;

import groovy.lang.Singleton;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

@Singleton
public class XMLParser {

    DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();

    Document doc;

    public void updateData(String filePath, String propertyName, int index, String value) throws IOException, SAXException, TransformerException, ParserConfigurationException {
        DocumentBuilder b = f.newDocumentBuilder();
        this.doc = b.parse(new File(filePath));
        Node zookeeperPort = doc.getElementsByTagName(propertyName).item(index);
        zookeeperPort.setTextContent(value);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }
}
