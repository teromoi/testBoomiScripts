package nl.Boomi.GroovyDebugger.Main

import nl.Boomi.GroovyDebugger.Context.*;

import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import java.util.Properties;
import java.io.InputStream;
import java.util.Properties;
import java.io.InputStream;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



public class Debug {

	private static ContextCreator dataContext;
	//Path to your testfiles.
	private static String path ="c:\\Groovy\\Data\\";
	private static  Logger logger;
	private static ExecutionUtil ExecutionUtil;
	
	// This method is used to set up all the components
	private static void Startup(){
		dataContext = new ContextCreator();
		ExecutionUtil = new ExecutionUtil();
		
		// load files to streams
		dataContext.AddFiles(path);
		
		//create empty property objects
		dataContext.createEmptyProperties(dataContext.getDataCount());
		
		//create properties per stream;
		for( int i = 0; i < dataContext.getDataCount(); i++ ) {

			dataContext.addPropertyValues(i,"host_name" , "value"); // You can create multiple property key/value pairs per stream;
		}
	}
	public static main(args) {
		Startup();
		BoomiLogic();
	}

	private static void BoomiLogic(){
		
		
		// Demo for using logger and setting + getting (Dynamic) ProcessProperties.
		// This does absolutely nothing, but simulates some of the workings of  ExecutionUtil
		// logger prints the output to the console window
		logger = ExecutionUtil.getBaseLogger();
		
		ExecutionUtil.setDynamicProcessProperty("test", "Test", true);
		
		String result = ExecutionUtil.getDynamicProcessProperty("test");
		logger.info(result);
		
		String lastrun = ExecutionUtil.getProcessProperty(null, "lastrun");
		logger.info(lastrun);
		// end demo
		
		// main loop in ProcessData shape
		for( int i = 0; i < dataContext.getDataCount(); i++ ) {
			  InputStream is = dataContext.getStream(i);
			  Properties props = dataContext.getProperties(i);

			  //Example of JSON Parsing
			  print("\nDoc nr: " + i);
			  //ObjectMapper mapper = new ObjectMapper();
			  //JsonNode rootNode = mapper.readValue(is, JsonNode.class);
			  //def namesList = rootNode.findValuesAsText("popup");
			  
			  //if(namesList.size() > 0){
				//  def  firstNameFound = namesList[0];
				  //is = new  ByteArrayInputStream(firstNameFound.getBytes("UTF-8"));
				  
			  //}
			  
			  //Example of XML Parsing
			  
			   /// SAXBuilder builder = new SAXBuilder();
			   /// Document doc = null;
				
				///try {
					
					///doc = builder.build(is); // Load stream in XML Document
					
				///} catch (JDOMException e) {
					
					///e.printStackTrace();
				///} catch (IOException e) {
					
					///e.printStackTrace();
				///}
			
				///XMLOutputter outputter = new XMLOutputter();
				
			   ///logger.info(outputter.outputString(doc));
			
			  
			  dataContext.storeStream(is, props);
			}
	}	
}
