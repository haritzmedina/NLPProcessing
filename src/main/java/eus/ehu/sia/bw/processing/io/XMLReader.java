package eus.ehu.sia.bw.processing.io;

import eus.ehu.sia.bw.processing.Item;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

/**
 * Created by Haritz on 27/11/2015.
 */
public class XMLReader {

    public static final String TAG_ITEM = "Item";
    public static final String TAG_ID = "Id";
    public static final String TAG_COMMENTARY = "Comentario";
    public static final String TAG_SOURCE = "Fuente";
    public static final String TAG_DATE = "Fecha";
    public static final String TAG_LANGUAGE = "Idioma";

    public XMLReader(){

    }

    public List<Item> read(File file) {
        // Prepare DOM parser
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(file);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        doc.getDocumentElement().normalize();

        // Get node list
        NodeList nList = doc.getElementsByTagName(this.TAG_ITEM);

        // Create a vector to save items
        List<Item> items = new Vector<>();

        // Loop for each item
        for (int temp = 0; temp < nList.getLength(); temp++) {
            // Get an item node
            Node nNode = nList.item(temp);
            // Create item to insert read data
            Item item = new Item();
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                item.setId(eElement.getElementsByTagName(this.TAG_ID).item(0).getTextContent());
                item.setCommentary(eElement.getElementsByTagName(this.TAG_COMMENTARY).item(0).getTextContent());
                item.setSource(eElement.getElementsByTagName(this.TAG_SOURCE).item(0).getTextContent());
                item.setDate(DatatypeConverter.parseDateTime(eElement.getElementsByTagName(this.TAG_DATE).item(0).getTextContent()).getTime());
                item.setLanguage(eElement.getElementsByTagName(this.TAG_LANGUAGE).item(0).getTextContent());
            }
            items.add(item);
        }
        return items;
    }
}
