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
import resume.ranker.model.User;
import resume.ranker.service.LoginService;
import resume.ranker.service.RegisterService;

/**
 * TODO Add documentation
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ObjectMapper mapper = new ObjectMapper();
	private final String POSTRequest = "POST [%s] [%s]";
	LoginService loginService = new LoginService();

	public RegisterServlet() {
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

		User newUserDetails = mapper.readValue(inputrequest, User.class);

		User newUserCreation = new User(newUserDetails.getEmailId(), newUserDetails.getPassword());

		RegisterService registerService = new RegisterService();
		boolean result = registerService.register(newUserCreation);

		if (result)
			response.setStatus(HttpServletResponse.SC_OK);
	}
}
