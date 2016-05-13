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

import resume.ranker.model.JobProfileConfiguration;
import resume.ranker.service.LoginService;
import resume.ranker.service.SaveConfigurationService;

/**
 * Servlet implementation class SaveConfiguration
 */
@WebServlet("/SaveConfiguration")
public class SaveConfiguration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveConfiguration() {
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
		
		System.out.println("POST request of SaveConfiguration working...");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
        	System.out.println("Inside IF");
            json = br.readLine();
        }
        
        System.out.println("Requested String: " + json);
        
        ObjectMapper mapper = new ObjectMapper();
        JobProfileConfiguration configurationDetails = mapper.readValue(json, JobProfileConfiguration.class);
		
        SaveConfigurationService saveConfigurationService = new SaveConfigurationService();
        boolean result = saveConfigurationService.save(configurationDetails);   

        System.out.println("Save Configuration Result: " + result);
        
        if (result) 
        	response.setStatus(HttpServletResponse.SC_OK);
        else
        	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
	}

}
