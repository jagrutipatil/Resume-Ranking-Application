package resume.ranker.model;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="JOB_PROFILE_CONFIGURATION")
public class JobProfileConfiguration {

	@Id
	private String jobId;
	private String jobTitle;
	private String keywords;
	private String emailId;
	
	
	
	public JobProfileConfiguration() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JobProfileConfiguration(String jobId, String jobTitle, String keywords, String emailId) {
		super();
		this.jobId = jobId;
		this.jobTitle = jobTitle;
		this.keywords = keywords;
		this.emailId = emailId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "JobProfileConfiguration [jobId=" + jobId + ", jobTitle=" + jobTitle + ", keywords="
				+ keywords + ", emailId=" + emailId + "]";
	}
	
		
	
	
	
}
