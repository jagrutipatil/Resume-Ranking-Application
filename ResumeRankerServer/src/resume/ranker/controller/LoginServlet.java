package resume.ranker.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import resume.ranker.files.util.ResumeRankerUtils;
import resume.ranker.model.User;
import resume.ranker.service.LoginService;

/**
 * TODO Add documentation
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ObjectMapper mapper = new ObjectMapper();
	private final String POSTRequest = "POST [%s] [%s]";
	LoginService loginService = new LoginService();

	public LoginServlet() {
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
		System.out.println(String.format(POSTRequest, "LoginServlet", inputrequest));

		User loginUserDetails = mapper.readValue(inputrequest, User.class);

		boolean result = loginService.authenticateUser(loginUserDetails.getEmailId(), loginUserDetails.getPassword());

		if (result) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
