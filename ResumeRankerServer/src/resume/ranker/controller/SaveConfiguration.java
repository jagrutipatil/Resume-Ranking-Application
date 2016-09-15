package resume.ranker.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import resume.ranker.files.util.ResumeRankerUtils;
import resume.ranker.model.JobProfileConfiguration;
import resume.ranker.service.LoginService;
import resume.ranker.service.SaveConfigurationService;

/**
 * TODO Add Documentation
 */
@WebServlet("/SaveConfiguration")
public class SaveConfiguration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ObjectMapper mapper = new ObjectMapper();
	private final String POSTRequest = "POST [%s] [%s]";
	LoginService loginService = new LoginService();

	public SaveConfiguration() {
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
		System.out.println(String.format(POSTRequest, "RegisterServlet", inputrequest));

		JobProfileConfiguration configurationDetails = mapper.readValue(json, JobProfileConfiguration.class);

		SaveConfigurationService saveConfigurationService = new SaveConfigurationService();
		boolean result = saveConfigurationService.save(configurationDetails);

		if (result)
			response.setStatus(HttpServletResponse.SC_OK);
		else
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	}

}
