package resume.ranker.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

import resume.ranker.constants.PathConfigurations;

public class LuceneSearchDocs {

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
		QueryBuilder obj = new QueryBuilder();
		String formattedQuery = obj.orQuery(line);
		Query query = null;
		try {
			query = parser.parse(formattedQuery);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		ArrayList<String> files = doPagingSearch(in, searcher, query, hitsPerPage, raw,
				queries == null && queryString == null);
		reader.close();

		files.toArray();
		Map<String, Object> filesMap = new HashMap<String, Object>();
		filesMap.put("files", files);

		return filesMap;
	}
}
