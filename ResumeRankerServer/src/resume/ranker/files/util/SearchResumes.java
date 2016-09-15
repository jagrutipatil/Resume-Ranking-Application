package resume.ranker.files.util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import resume.ranker.constants.ServerConstants;
import resume.ranker.model.LuceneSearchDocs;
import resume.ranker.model.SearchParameters;

/**
 * TODO Add java docs
 */
@WebServlet("/SearchResumes")
public class SearchResumes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ObjectMapper mapper = new ObjectMapper();
	private LuceneSearchDocs luceneSearch = new LuceneSearchDocs();
	private final String POSTRequest = "POST [%s] [%s]";

	public SearchResumes() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String inputrequest = ResumeRankerUtils.inputstreamToJSON(request.getInputStream());
		System.out.println(String.format(POSTRequest, "SearchResumes", inputrequest));

		SearchParameters search = mapper.readValue(inputrequest, SearchParameters.class);

		System.out.println("Search Parameters: " + search.toString());

		Map<String, Object> resumeResult = luceneSearch.searchParams(search);

		response.setContentType(ServerConstants.APPLICATION_TYPE_JSON);
		response.setCharacterEncoding(ServerConstants.ENCODING_TYPE_UTF);
		response.getWriter().write(ResumeRankerUtils.mapToJson(resumeResult));
	}
}