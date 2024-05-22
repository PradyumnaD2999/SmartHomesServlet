
/*********


http://www.saxproject.org/

SAX is the Simple API for XML, originally a Java-only API. 
SAX was the first widely adopted API for XML in Java, and is a �de facto� standard. 
The current version is SAX 2.0.1, and there are versions for several programming language environments other than Java. 

The following URL from Oracle is the JAVA documentation for the API

https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html


*********/
import org.xml.sax.InputSource;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import  java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


////////////////////////////////////////////////////////////

/**************

SAX parser use callback function  to notify client object of the XML document structure. 
You should extend DefaultHandler and override the method when parsin the XML document

***************/

////////////////////////////////////////////////////////////

public class SaxParserDataStore extends DefaultHandler {
    Speaker spkr;
    Doorbell dbl;
    Doorlock dlk;
	Lighting lght;
	Thermostat trmst;
    Accessory accessory;
    static HashMap<String,Speaker> spkrs;
    static HashMap<String, Doorbell> dbls;
    static HashMap<String,Doorlock> dlks;
	static HashMap<String,Lighting> lghts;
	static HashMap<String,Thermostat> trmsts;
    static HashMap<String,Accessory> accessories;
    String spkrXmlFileName;
	HashMap<String,String> accessoryHashMap;
    String elementValueRead;
	String currentElement="";
    public SaxParserDataStore()
	{
	}
	public SaxParserDataStore(String spkrXmlFileName) {
    this.spkrXmlFileName = spkrXmlFileName;
    spkrs = new HashMap<String, Speaker>();
	dbls=new  HashMap<String, Doorbell>();
	dlks=new HashMap<String, Doorlock>();
	lghts=new HashMap<String, Lighting>();
	trmsts=new HashMap<String, Thermostat>();
	accessories=new HashMap<String, Accessory>();
	accessoryHashMap=new HashMap<String,String>();
	parseDocument();
    }

   //parse the xml using sax parser to get the data
    private void parseDocument() 
	{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try 
		{
	    SAXParser parser = factory.newSAXParser();
	    parser.parse(spkrXmlFileName, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
	}

   

////////////////////////////////////////////////////////////

/*************

There are a number of methods to override in SAX handler  when parsing your XML document :

    Group 1. startDocument() and endDocument() :  Methods that are called at the start and end of an XML document. 
    Group 2. startElement() and endElement() :  Methods that are called  at the start and end of a document element.  
    Group 3. characters() : Method that is called with the text content in between the start and end tags of an XML document element.


There are few other methods that you could use for notification for different purposes, check the API at the following URL:

https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html

***************/

////////////////////////////////////////////////////////////
	
	// when xml start element is parsed store the id into respective hashmap for console,games etc 
    @Override
    public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {

        if (elementName.equalsIgnoreCase("spkr")) 
		{
			currentElement="spkr";
			spkr = new Speaker();
            spkr.setId(attributes.getValue("id"));
		}
        if (elementName.equalsIgnoreCase("dlk"))
		{
			currentElement="dlk";
			dlk = new Doorlock();
            dlk.setId(attributes.getValue("id"));
        }
        if (elementName.equalsIgnoreCase("dbl"))
		{
			currentElement="dbl";
			dbl= new Doorbell();
            dbl.setId(attributes.getValue("id"));
        }
		if (elementName.equalsIgnoreCase("lght"))
		{
			currentElement="lght";
			lght= new Lighting();
            lght.setId(attributes.getValue("id"));
        }
		if (elementName.equalsIgnoreCase("trmst"))
		{
			currentElement="trmst";
			trmst= new Thermostat();
            trmst.setId(attributes.getValue("id"));
        }
        if (elementName.equals("accessory") &&  !currentElement.equals("spkr"))
		{
			currentElement="accessory";
			accessory=new Accessory();
			accessory.setId(attributes.getValue("id"));
	    }


    }
	// when xml end element is parsed store the data into respective hashmap for console,games etc respectively
    @Override
    public void endElement(String str1, String str2, String element) throws SAXException {
 
        if (element.equals("spkr")) {
			spkrs.put(spkr.getId(),spkr);
			return;
        }
 
        if (element.equals("dlk")) {	
			dlks.put(dlk.getId(),dlk);
			return;
        }
        if (element.equals("dbl")) {	  
			dbls.put(dbl.getId(),dbl);
			return;
        }
		if (element.equals("lght")) {	  
			lghts.put(lght.getId(),lght);
			return;
        }
		if (element.equals("trmst")) {	  
			trmsts.put(trmst.getId(),trmst);
			return;
        }
        if (element.equals("accessory") && currentElement.equals("accessory")) {
			accessories.put(accessory.getId(),accessory);       
			return; 
        }
		if (element.equals("accessory") && currentElement.equals("spkr")) 
		{
			accessoryHashMap.put(elementValueRead,elementValueRead);
		}
      	if (element.equalsIgnoreCase("accessories") && currentElement.equals("spkr")) {
			spkr.setAccessories(accessoryHashMap);
			accessoryHashMap=new HashMap<String,String>();
			return;
		}
        if (element.equalsIgnoreCase("image")) {
		    if(currentElement.equals("spkr"))
				spkr.setImage(elementValueRead);
        	if(currentElement.equals("dbl"))
				dbl.setImage(elementValueRead);
            if(currentElement.equals("dlk"))
				dlk.setImage(elementValueRead);
			if(currentElement.equals("lght"))
				lght.setImage(elementValueRead);
			if(currentElement.equals("trmst"))
				trmst.setImage(elementValueRead);
            if(currentElement.equals("accessory"))
				accessory.setImage(elementValueRead);          
			return;
        }
        

		if (element.equalsIgnoreCase("discount")) {
            if(currentElement.equals("spkr"))
				spkr.setDiscount(Double.parseDouble(elementValueRead));
        	if(currentElement.equals("dbl"))
				dbl.setDiscount(Double.parseDouble(elementValueRead));
            if(currentElement.equals("dlk"))
				dlk.setDiscount(Double.parseDouble(elementValueRead));
			if(currentElement.equals("lght"))
				lght.setDiscount(Double.parseDouble(elementValueRead));
			if(currentElement.equals("trmst"))
				trmst.setDiscount(Double.parseDouble(elementValueRead));
            if(currentElement.equals("accessory"))
				accessory.setDiscount(Double.parseDouble(elementValueRead));          
			return;
	    }


		if (element.equalsIgnoreCase("condition")) {
            if(currentElement.equals("spkr"))
				spkr.setCondition(elementValueRead);
        	if(currentElement.equals("dbl"))
				dbl.setCondition(elementValueRead);
            if(currentElement.equals("dlk"))
				dlk.setCondition(elementValueRead);
			if(currentElement.equals("lght"))
				lght.setCondition(elementValueRead);
			if(currentElement.equals("trmst"))
				trmst.setCondition(elementValueRead);
            if(currentElement.equals("accessory"))
				accessory.setCondition(elementValueRead);          
			return;  
		}

		if (element.equalsIgnoreCase("manufacturer")) {
            if(currentElement.equals("spkr"))
				spkr.setRetailer(elementValueRead);
        	if(currentElement.equals("dbl"))
				dbl.setRetailer(elementValueRead);
            if(currentElement.equals("dlk"))
				dlk.setRetailer(elementValueRead);
			if(currentElement.equals("lght"))
				lght.setRetailer(elementValueRead);
			if(currentElement.equals("trmst"))
				trmst.setRetailer(elementValueRead);
            if(currentElement.equals("accessory"))
				accessory.setRetailer(elementValueRead);          
			return;
		}

        if (element.equalsIgnoreCase("name")) {
            if(currentElement.equals("spkr"))
				spkr.setName(elementValueRead);
        	if(currentElement.equals("dbl"))
				dbl.setName(elementValueRead);
            if(currentElement.equals("dlk"))
				dlk.setName(elementValueRead);
			if(currentElement.equals("lght"))
				lght.setName(elementValueRead);
			if(currentElement.equals("trmst"))
				trmst.setName(elementValueRead);
            if(currentElement.equals("accessory"))
				accessory.setName(elementValueRead);          
			return;
	    }
	
        if(element.equalsIgnoreCase("price")){
			if(currentElement.equals("spkr"))
				spkr.setPrice(Double.parseDouble(elementValueRead));
        	if(currentElement.equals("dbl"))
				dbl.setPrice(Double.parseDouble(elementValueRead));
            if(currentElement.equals("dlk"))
				dlk.setPrice(Double.parseDouble(elementValueRead));
			if(currentElement.equals("lght"))
				lght.setPrice(Double.parseDouble(elementValueRead));
			if(currentElement.equals("trmst"))
				trmst.setPrice(Double.parseDouble(elementValueRead));
            if(currentElement.equals("accessory"))
				accessory.setPrice(Double.parseDouble(elementValueRead));          
			return;
        }

		if(element.equalsIgnoreCase("description")){
			if(currentElement.equals("spkr"))
				spkr.setDescription(elementValueRead);
        	if(currentElement.equals("dbl"))
				dbl.setDescription(elementValueRead);
            if(currentElement.equals("dlk"))
				dlk.setDescription(elementValueRead);
			if(currentElement.equals("lght"))
				lght.setDescription(elementValueRead);
			if(currentElement.equals("trmst"))
				trmst.setDescription(elementValueRead);
            if(currentElement.equals("accessory"))
				accessory.setDescription(elementValueRead);
			return;
        }
	}
	//get each element in xml tag
    @Override
    public void characters(char[] content, int begin, int end) throws SAXException {
        elementValueRead = new String(content, begin, end);
    }


    /////////////////////////////////////////
    // 	     Kick-Start SAX in main       //
    ////////////////////////////////////////
	
//call the constructor to parse the xml and get product details
 public static void addHashmap() {
		String TOMCAT_HOME = System.getProperty("catalina.home");	
		new SaxParserDataStore(TOMCAT_HOME+"\\webapps\\Assignment_1\\ProductCatalog.xml");
    } 
}
