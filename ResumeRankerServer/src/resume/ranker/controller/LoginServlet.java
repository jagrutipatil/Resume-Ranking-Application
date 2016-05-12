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

import resume.ranker.model.User;
import resume.ranker.service.LoginService;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		// TODO Auto-generated method stub
		
		System.out.println("POST request of LoginServlet working...");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
        	System.out.println("Inside IF");
            json = br.readLine();
        }
        
        System.out.println("Requested String: " + json);
        
        ObjectMapper mapper = new ObjectMapper();
        User loginUserDetails = mapper.readValue(json, User.class);
		
        //User newUserCreation = new User(newUserDetails.getEmailId(), newUserDetails.getPassword());
        
        LoginService loginService = new LoginService();
        boolean result = loginService.authenticateUser(loginUserDetails.getEmailId(), loginUserDetails.getPassword());   

        System.out.println("Login Result: " + result);
        
        //response.setContentType("text/html;charset=UTF-8");
        
        if (result) 
        	response.setStatus(HttpServletResponse.SC_OK);
        else
        	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
	}

}
