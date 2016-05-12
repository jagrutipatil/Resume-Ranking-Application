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
import resume.ranker.service.RegisterService;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegisterServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("POST request of RegisterServlet working...");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
        	System.out.println("Inside IF");
            json = br.readLine();
        }
        
        System.out.println("Requested String: " + json);
        
        ObjectMapper mapper = new ObjectMapper();
        User newUserDetails = mapper.readValue(json, User.class);
		
        User newUserCreation = new User(newUserDetails.getEmailId(), newUserDetails.getPassword());
        
        RegisterService registerService = new RegisterService();
        boolean result = registerService.register(newUserCreation);   

        System.out.println("Result: " + result);
        //response.setStatus(HttpServletResponse.SC_OK);
		
	}

}
