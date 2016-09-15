package resume.ranker.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import resume.ranker.constants.ServerConstants;
import resume.ranker.files.util.ResumeRankerUtils;
import resume.ranker.model.JobProfileConfiguration;
import resume.ranker.service.GetConfigurationService;

/**
 * TODO Add documentation
 */
@WebServlet("/GetConfiguration")
public class GetConfiguration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ObjectMapper mapper = new ObjectMapper();
	private final String POSTRequest = "POST [%s] [%s]";
	
	public GetConfiguration() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println(String.format(POSTRequest, "GetConfiguration"));
		String inputrequest = ResumeRankerUtils.inputstreamToJSON(request.getInputStream());
		System.out.println(String.format(POSTRequest, "SearchResumes", inputrequest));
		
		JobProfileConfiguration JobProfileConfigurationDetails = mapper.readValue(inputrequest, JobProfileConfiguration.class);

		GetConfigurationService getConfigurationService = new GetConfigurationService();
		List<JobProfileConfiguration> configurationList = getConfigurationService
				.getConfigurationDetails(JobProfileConfigurationDetails.getEmailId());

		Map<String, List<JobProfileConfiguration>> configurationMap = new HashMap<String, List<JobProfileConfiguration>>();
		configurationMap.put("config", configurationList);

		response.setContentType(ServerConstants.APPLICATION_TYPE_JSON);
		response.setCharacterEncoding(ServerConstants.ENCODING_TYPE_UTF);
		response.getWriter().write(ResumeRankerUtils.mapToJson(configurationMap));
	}
}
