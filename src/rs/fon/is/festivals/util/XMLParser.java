package rs.fon.is.festivals.util;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;

public class XMLParser {

	public static ArrayList<String> parseDocument(String url) {
		ArrayList<String> festivalsID = new ArrayList<>();

		try {

			// File fXmlFile = new File("xml/festivals.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder
					.parse(url);
			// Document doc = dBuilder.parse(fXmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("event");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				Element eElement = (Element) nNode;
				String id = eElement.getElementsByTagName("id").item(0).getTextContent();
				festivalsID.add(id);

			}
			return festivalsID;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

}
