package resume.ranker.model;

import java.util.Arrays;

/**
 * TODO Add documentation
 */
public class SearchParameters {

	private String[] skill;
	
	private int id;

	public SearchParameters() {
		super();
	}

	public SearchParameters(String[] skill, int id) {
		super();
		this.skill = skill;
		this.id = id;
	}

	public String[] getSkill() {
		return skill;
	}

	public void setSkill(String[] skill) {
		this.skill = skill;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "SearchParameters [skill=" + Arrays.toString(skill) + ", id=" + id + "]";
	}
}
