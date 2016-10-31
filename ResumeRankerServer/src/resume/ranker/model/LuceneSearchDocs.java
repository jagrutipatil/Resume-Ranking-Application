package resume.ranker.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import resume.ranker.constants.PathConfigurations;

public class LuceneSearchDocs {

	public ArrayList<String> doRangeSearch(BufferedReader in, IndexSearcher searcher, Query query) throws IOException {

		ArrayList<String> searchFiles = new ArrayList<String>();
		TopDocs results = searcher.search(query, 10);
		ScoreDoc[] hits = results.scoreDocs;

		for (int i = 0; i < results.totalHits; i++) {
			Document doc = searcher.doc(hits[i].doc);
			String path = doc.get("path");
			if (path != null)
				System.out.println((i + 1) + ". " + path);

			String name = doc.get("name");
			searchFiles.add(name);
		}

		return searchFiles;
	}

	public ArrayList<String> doPagingSearch(BufferedReader in, IndexSearcher searcher, Query query, int hitsPerPage,
			boolean raw, boolean interactive) throws IOException {

		ArrayList<String> searchFiles = new ArrayList<String>();

		// Collect enough docs to show 5 pages
		TopDocs results = searcher.search(query, 5 * hitsPerPage);

		ScoreDoc[] hits = results.scoreDocs;

		for (int i = 0; i < results.totalHits; i++) {
			Document doc = searcher.doc(hits[i].doc);
			String path = doc.get("path");
			if (path != null)
				System.out.println((i + 1) + ". " + path);

			String name = doc.get("name");
			searchFiles.add(name);
		}

		return searchFiles;
	}

	public Map<String, Object> searchParams(SearchParameters search) throws IOException {
		String index = PathConfigurations.INDEX_RESUME;
		String field = "contents";
		String queries = null;
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
		Query query = null;
//		SpanQuery query;
		Map<String, Object> filesMap = new HashMap<String, Object>();
		try {
//			query = new SpanMultiTermQueryWrapper<RegexpQuery>(new RegexpQuery(new Term(buildQuery(search))));
			String strQuery = buildQuery(search);			
			
			MultiPhraseQuery multiPhraseQuery = new MultiPhraseQuery();
			Set<String> set = new HashSet<String>();			
			
			if (search.isExperience()) {
//				multiPhraseQuery.add(new Term[] {new Term(field, "previous"), new Term(field, ""), new Term(field, "past"), new Term(field, "earlier")});
				multiPhraseQuery.add(new Term[] {new Term(field, "work"), new Term(field, "job"), new Term(field, "employment"), new Term(field, "professional"), new Term(field, "software"), new Term(field, "mechanical"), new Term(field, "engineering"), new Term(field, "full stack")});
				multiPhraseQuery.add(new Term[] {new Term(field, "experience"), new Term(field, "projects"), new Term(field, "history"), new Term(field, "intern"), new Term(field, "engineer"), new Term(field, "developer")});
				ArrayList<String> arrayList = null;
				arrayList = doPagingSearch(in, searcher, multiPhraseQuery, hitsPerPage, raw,
						queries == null && queryString == null);
				set.addAll(arrayList);
			}				

			if (strQuery != null && !strQuery.equals("")) {
				query = parser.parse(strQuery);												
				ArrayList<String> files = null;
				files = doPagingSearch(in, searcher, query, hitsPerPage, raw,
						queries == null && queryString == null);
				
				set.addAll(files);
				set.toArray();
			}			
			
			if (set.size() != 0) {
				filesMap.put("files", set);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return filesMap;
	}

	private String buildQuery(SearchParameters searchParams) {
		String[] skill = searchParams.getSkill();
		String[] previousEmployer = searchParams.getPreviousEmployer();
		double minGPA = searchParams.getMinGPA();
		double maxGPA = searchParams.getMaxGPA();

		QueryBuilder queryBuilder = new QueryBuilder();
		ArrayList<String> list = new ArrayList<String>();
		StringBuilder stb = new StringBuilder();

		if (skill.length != 0)
			list.add(queryBuilder.orQuery(skill));

		if (previousEmployer.length != 0)
			list.add(queryBuilder.orQuery(previousEmployer));

		String formattedQuery = queryBuilder.orQueryList(list);
		
		if (minGPA != 0)
			formattedQuery = queryBuilder.andQuery(formattedQuery, "gpa[ " + minGPA + " TO " + maxGPA + "]");		
		
		if (searchParams.isMasters()) {
			formattedQuery = queryBuilder.andQuery(formattedQuery, queryBuilder.orQuery("M.S.", "Masters", "M.B.A", "Ph.D", "MS"));
		}
		
		if (searchParams.isBachlors()) {
			formattedQuery = queryBuilder.andQuery(formattedQuery, queryBuilder.orQuery("B.E.", "Bachelor*", "B.S.", "graduation", "BE", "M.S.", "Masters", "M.B.A", "Ph.D", "MS", "university"));
		}
		
		System.out.println("Query: " + formattedQuery);
		return formattedQuery;
//		return "\b(January|Jan|February|Feb|March|Mar|April|Apr|June|Jun|July|Jul|August|Aug|September|Sept|October|Oct|November|Dec|December) (19[7-9][0-9]|2[0-9][0-9][0-9]) - \b(January|Jan|February|Feb|March|Mar|April|Apr|June|Jun|July|Jul|August|Aug|September|Sept|October|Oct|November|Dec|December) (19[7-9][0-9]|2[0-9][0-9][0-9])";
	}
}
