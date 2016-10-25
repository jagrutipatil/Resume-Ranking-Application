package resume.ranker.model;

import java.util.Arrays;

/**
 * TODO Add documentation
 */
public class SearchParameters {

	private String[] skill;
	private String[] previousEmployer;

	private double minGPA = 0;
	private double maxGPA = 4;
	
	private int id;

	public SearchParameters() {
		super();
	}

	public SearchParameters(String[] skill, String[] previousEmployer, int id) {
		super();
		this.skill = skill;
		this.previousEmployer = previousEmployer;
		this.id = id;
	}

	public String[] getSkill() {
		return skill;
	}

	public void setSkill(String[] skill) {
		this.skill = skill;
	}
	
	public String[] getPreviousEmployer() {
		return previousEmployer;
	}

	public void setPreviousEmployer(String[] previousEmployer) {
		this.previousEmployer = previousEmployer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public double getMinGPA() {
		return minGPA;
	}

	public void setMinGPA(double minGPA) {
		this.minGPA = minGPA;
	}

	public double getMaxGPA() {
		return maxGPA;
	}

	public void setMaxGPA(double maxGPA) {
		this.maxGPA = maxGPA;
	}

	@Override
	public String toString() {
		return "SearchParameters [skill=" + Arrays.toString(skill) + " Previous Employer=" + Arrays.toString(previousEmployer) + ", id=" + id + "]";
	}
}
