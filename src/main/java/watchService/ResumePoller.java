package watchService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;

/**
 * Hello world!
 *
 */

import org.apache.commons.io.FilenameUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.parser.txt.TXTParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.xml.sax.SAXException;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.InputStream;
import org.apache.commons.io.IOUtils;

public class ResumePoller 
{
    public static void main( String[] args ) 
    {   
    	
    	Path myDir = Paths.get("C:\\Users\\prajwalk\\Desktop\\SJSU - Fall2015\\Resume Ranking Project\\Document Types");
    	
    	List<String> listOfFiles = new ArrayList<String>();
    	
    		try {
	           WatchService watcher = myDir.getFileSystem().newWatchService();
	           myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
	
	               WatchKey watckKey = watcher.take();
		
	               while (true){
	               
		               List<WatchEvent<?>> events = watckKey.pollEvents();
			           for (WatchEvent event : events) {
			                if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
			                    System.out.println("Created: " + event.context().toString());
			                    listOfFiles.add(event.context().toString());
			                    
			                }
			                if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
			                    System.out.println("Delete: " + event.context().toString());
			                }
			           }
			           
			           if (listOfFiles.size() > 0){
			        	   addDocument(listOfFiles);
			        	   listOfFiles.clear();
			           }
			           
		           
	               }
		           	           	           
	        } catch (Exception e) {
	            System.out.println("Error: " + e.toString());
	        }
		
   }
   
    static void addDocument(List<String> listOfFiles) throws IOException, TikaException, SAXException {
	    
    	String ext = "";
    	String s = "";
    	String indexPath = "C:\\Users\\prajwalk\\Desktop\\SJSU - Fall2015\\Resume Ranking Project\\IndexD";
    	
    	System.out.println("Indexing to directory '" + indexPath + "'...");

        Directory dir = FSDirectory.open(Paths.get(indexPath));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        
        //iwc.setOpenMode(OpenMode.CREATE);
        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND); // This is crucial
        
        IndexWriter writer = new IndexWriter(dir, iwc);
        
        String fileName = "";
        String filePath = ""; 
        
    	for (int i = 0; i < listOfFiles.size(); i++) {
    		
    			fileName = listOfFiles.get(i);
    			
    			ext = FilenameUtils.getExtension(fileName);
    			
    			filePath = "C:\\Users\\prajwalk\\Desktop\\SJSU - Fall2015\\Resume Ranking Project\\Document Types\\";
    			filePath = filePath + fileName;
    			
    			if (ext.equals("pdf")){
    				s = getPdfExtract(filePath);
    			}else if (ext.equals("docx")){
    				s = getDocxExtract(filePath);
    			}else if (ext.equals("txt")){
    				s = getTxtExtract(filePath);
    			}
    	    
    		InputStream stream = IOUtils.toInputStream(s);
    		indexDoc(writer, filePath, fileName, stream);
    		
    	}
    	writer.close();
    }
    
    static String getPdfExtract(String input) throws IOException,TikaException, SAXException{
    	
    	BodyContentHandler handler = new BodyContentHandler();
    	Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(new File(input));
        ParseContext pcontext = new ParseContext();
        
        //parsing the document using PDF parser
        PDFParser pdfparser = new PDFParser(); 
        pdfparser.parse(inputstream, handler, metadata,pcontext);
        
        return handler.toString();
    }
    
    static String getDocxExtract(String input) throws IOException,TikaException, SAXException{
    	
    	 BodyContentHandler handler = new BodyContentHandler();
         Metadata metadata = new Metadata();
         FileInputStream inputstream = new FileInputStream(new File(input));
         ParseContext pcontext = new ParseContext();
         
         //OOXml parser
         OOXMLParser  msofficeparser = new OOXMLParser (); 
         msofficeparser.parse(inputstream, handler, metadata,pcontext);
         //System.out.println("Contents of the docx" + handler.toString());
        
        return handler.toString();
    }
    
    static String getTxtExtract(String input) throws IOException,TikaException, SAXException{
    	
    	BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(new File(input));
        ParseContext pcontext=new ParseContext();
        
        //Text document parser
        TXTParser  TexTParser = new TXTParser();
        TexTParser.parse(inputstream, handler, metadata,pcontext);
        //System.out.println("Contents of the txt:" + handler.toString());
       
        return handler.toString();
   }
    
    
    static void indexDoc(IndexWriter writer, String path, String fileName, InputStream content) throws IOException {
        
          // make a new, empty document
          Document doc = new Document();
          
          Field pathField = new StringField("path", path, Field.Store.YES);
          doc.add(pathField);
          
          Field nameField = new StringField("name", fileName, Field.Store.YES);
          doc.add(nameField);
          
          doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(content, StandardCharsets.UTF_8))));
          
          if (writer.getConfig().getOpenMode() == OpenMode.CREATE_OR_APPEND) {
            // New index, so we just add the document (no old document can be there):
            System.out.println("adding...");
            writer.addDocument(doc);
          } 
     } 
    
}
