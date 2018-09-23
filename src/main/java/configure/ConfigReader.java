package configure;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ConfigReader {
	private Element root;
	public ConfigReader(String filename){
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(filename);
			root = doc.getDocumentElement();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected String getValue(Element parent,String tagName){
		NodeList list = parent.getElementsByTagName(tagName);
		Element element = (Element)list.item(0);
		//NodeList list1 = element.getChildNodes();    //��ȡȫ���ӽڵ�
		Node node = element.getChildNodes().item(0);
		if(node.getNodeType() == Node.TEXT_NODE)
			return node.getNodeValue();
		return null; 
	}
	
	public HashMap<String,DrawConfig> getDrawConfig(){
		
		HashMap<String,DrawConfig> configs = new HashMap<String,DrawConfig>();
		
		NodeList list = root.getElementsByTagName("Config");
		for(int i = 0 ; i < list.getLength() ; i++){
			Element element = (Element)list.item(i);
			String menuItemName = getValue(element, "MenuItemName");
			String classname = getValue(element,"ClassName");
			DrawConfig config = new DrawConfig();
			config.setMenuItemName(menuItemName);
			config.setClassName(classname);
			configs.put(classname, config);
		}
		return configs;
	}
	
	public void cleanup(){
		root = null;
	}
}
