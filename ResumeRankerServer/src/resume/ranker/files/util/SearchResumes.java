package resume.ranker.files.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * Servlet implementation class SearchResumes
 */
@WebServlet("/SearchResumes")
public class SearchResumes extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SearchResumes() {
    	super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("POST request working...");
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
        	System.out.println("Inside IF");
            json = br.readLine();
        }
        
        System.out.println("Requested String: " + json);
        
        //JSONObject jsonObj = new JSONObject(json);
        
        //System.out.println("Converted jsonobject: " + jsonObj);
        
        // 2. initiate jackson mapper
        ObjectMapper mapper = new ObjectMapper();
        
        // 3. Convert received JSON to Article
        SearchParameters search = mapper.readValue(json, SearchParameters.class);
		
        System.out.println("Search Parameters");
		System.out.println(search.toString());
		
		LuceneSearchDocs obj = new LuceneSearchDocs();
		Map<String, Object> filesMap = obj.searchParams(search);
		
		String json1 = null ;
		json1=new Gson().toJson(filesMap);		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json1);
		
	}

}


class SearchParameters{
	
	private String[] skill;
	private int id;
	
	public SearchParameters() {
		super();
	}

	/*public SearchParameters(String[] skill) {
		super();
		this.skill = skill;
	}*/

	public SearchParameters(String[] skill, int id) {
		super();
		this.skill = skill;
		this.id = id;
	}
	
	public String[] getSkill() {
		return skill;
	}

	public void setSkill(String[] skill) {
		this.skill = skill;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "SearchParameters [skill=" + Arrays.toString(skill) + ", id=" + id + "]";
	}

	/*@Override
	public String toString() {
		return "SearchParameters [skill=" + Arrays.toString(skill) + "]";
	}*/
	
}


class QueryFormat{
	
	public String andQuery(String[] query){
		
		String formattedQuery = "";
		
		for (int i=0; i<query.length; i++){
			if (i!=query.length-1)
				formattedQuery = formattedQuery + query[i] + " AND ";
			else
				formattedQuery = formattedQuery + query[i];
		}
		
		System.out.println("Formatted Query: " + formattedQuery);
		return formattedQuery;
	}
	
}

class LuceneSearchDocs{
	
	public ArrayList<String> doPagingSearch(BufferedReader in, IndexSearcher searcher, Query query, 
            int hitsPerPage, boolean raw, boolean interactive) throws IOException {

		ArrayList<String> files = new ArrayList<String>();
		
		// Collect enough docs to show 5 pages
		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;

		int numTotalHits = results.totalHits;
		
		int start = 0;
		int end = numTotalHits;
		
		for (int i = start; i < end; i++) {
			Document doc = searcher.doc(hits[i].doc);
			String path = doc.get("path");
			if (path!=null)
				System.out.println((i+1) + ". " + path);
			
			String name = doc.get("name");
			files.add(name);
		}
		
		return files;
		
	}
	
	
	public Map<String, Object> searchParams(SearchParameters search) throws IOException{
		String index = "C:\\Users\\prajwalk\\Desktop\\SJSU - Fall2015\\Resume Ranking Project\\IndexD";
        String field = "contents";
        String queries = null;
        int repeat = 0;
        boolean raw = false;
        String queryString = null;
        int hitsPerPage = 10;
        
        
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new StandardAnalyzer();

        BufferedReader in = null;
        if (queries != null) {
          in = Files.newBufferedReader(Paths.get(queries), StandardCharsets.UTF_8);
        } else {
          in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        }
        QueryParser parser = new QueryParser(field, analyzer);
        
        String[] line = search.getSkill(); 
        QueryFormat obj = new QueryFormat();
        String formattedQuery = obj.andQuery(line);
        Query query = null;
		try {
			query = parser.parse(formattedQuery);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        ArrayList<String> files = doPagingSearch(in, searcher, query, hitsPerPage, raw, queries == null && queryString == null);
        reader.close();
        
        files.toArray();
		Map<String, Object> filesMap = new HashMap<String, Object>();
		filesMap.put("files", files);
		
		return filesMap;
			
	}
	
}
