package Messenger3;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class XMLHandler {
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private String color;
	private String name;
	private String systemMsg;
	private boolean connStat = false;
	
	public XMLHandler(){
		docFactory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	public String createXML(String txt, String c, String name, String systemMsg){
		txt = txt.trim();		// Remove possible end of line
		
		Document doc = docBuilder.newDocument();
		
		//Create the root element
		Element root = doc.createElement("message");
		doc.appendChild(root);
		root.setAttribute("name",name);
//		root.setAttribute("system",systemMsg);
		
		//Create the children
		Element text = doc.createElement("text");
		root.appendChild(text);
		text.setAttribute("color", c);
	
		if(systemMsg == "disconnecting"){
			Element disconnect = doc.createElement("disconnect");
			root.appendChild(disconnect);
		}
		//Check if there is any XML tags in text
	//	if(txt.contains("<")) txt.replace("<", "&lt");
	//	if(txt.contains(">")) txt.replace(">", "&gt");
		
		text.appendChild(doc.createTextNode(txt));

		//Create a string from the DOM document
		try {
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			return writer.toString();			
		} catch (TransformerException e) {
			e.printStackTrace();
			return null;
		}
	}
		
	public String readXML(String xml){
		String txt = "";
		try {
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    InputSource is = new InputSource(new StringReader(xml));
		    Document doc = builder.parse(is);			
			
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("message");
			Node nNode = nList.item(0);
			if(nNode.getNodeType() == Node.ELEMENT_NODE){
				Element element = (Element) nNode;
				name = element.getAttribute("name");
//				systemMsg = element.getAttribute("system");
//				if(systemMsg != ""){
	//				System.out.println(systemMsg);
		//		}
				nList = doc.getElementsByTagName("text");
				nNode = nList.item(0);
				element = (Element) nNode;
				color = element.getAttribute("color");
				txt = element.getTextContent();
				nList = doc.getElementsByTagName("disconnect");
				if(nList.getLength() != 0){
				//	nNode = nList.item(0);
//					systemMsg = "disconnecting";
					connStat = true;
					String str = name + " har loggat ut";
					return str;	
					}
				else{
					connStat = false;
				}
				
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return txt;
		
	}
	public String getColor(){
		return color;
	}
	public String getName(){
		return name;
	}
	public String getSystemMsg(){
		return systemMsg;
	}
	public boolean connectionState(){
		return connStat;
	}
}
