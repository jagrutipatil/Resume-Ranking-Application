package resume.ranker.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import resume.ranker.model.JobProfileConfiguration;
import resume.ranker.model.User;
import resume.ranker.service.GetConfigurationService;
import resume.ranker.service.LoginService;

/**
 * Servlet implementation class GetConfiguration
 */
@WebServlet("/GetConfiguration")
public class GetConfiguration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetConfiguration() {
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
		
		System.out.println("POST request of GetConfiguration working...");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
        	System.out.println("Inside IF");
            json = br.readLine();
        }
        
        System.out.println("Requested String: " + json);
        
        ObjectMapper mapper = new ObjectMapper();
        JobProfileConfiguration JobProfileConfigurationDetails = mapper.readValue(json, JobProfileConfiguration.class);
		
        //User newUserCreation = new User(newUserDetails.getEmailId(), newUserDetails.getPassword());
        
        GetConfigurationService getConfigurationService = new GetConfigurationService();
        List<JobProfileConfiguration> configurationList = getConfigurationService.getConfigurationDetails(JobProfileConfigurationDetails.getEmailId());   

        System.out.println("Sizeeee: " + configurationList.size());
        
        Map<String, List<JobProfileConfiguration>> configurationMap = new HashMap<String, List<JobProfileConfiguration>>();
        configurationMap.put("config", configurationList);
        
        String jsonConfig = null ;
        jsonConfig=new Gson().toJson(configurationMap);		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonConfig);
        
	}

}
