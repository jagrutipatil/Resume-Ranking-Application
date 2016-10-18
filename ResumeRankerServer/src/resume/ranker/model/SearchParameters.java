package resume.ranker.model;

import java.util.Arrays;

/**
 * TODO Add documentation
 */
public class SearchParameters {

	private String[] skill;
	private String[] previousEmployer;
	
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

	@Override
	public String toString() {
		return "SearchParameters [skill=" + Arrays.toString(skill) + " Previous Employer=" + Arrays.toString(previousEmployer) + ", id=" + id + "]";
	}
}
