package resume.ranker.model;

public class QueryBuilder {
	private static final String AND = " AND ";
	private static final String OR = " OR ";
	
	private static String queryLog = "Formatted Query: [%s]";
	
	/**
	 * TODO Add documentation
	 */
	public static String andQuery(String[] query) {
		StringBuilder andQuery = new StringBuilder();

		for (int i = 0; i < query.length; i++) {
			if (i != query.length - 1) {
				andQuery.append(query[i]).append(AND);
			} else {
				andQuery.append(query[i]);
			}				
		}

		System.out.println(String.format(queryLog, andQuery.toString()));
		return andQuery.toString();
	}
	
	/**
	 * TODO Add documentation
	 */
	public static String orQuery(String[] query) {

		StringBuilder orQuery = new StringBuilder();

		for (int i = 0; i < query.length; i++) {
			if (i != query.length - 1) {
				orQuery.append(query[i]).append(OR);
			} else {
				orQuery.append(query[i]);
			}				
		}

		System.out.println(String.format(queryLog, orQuery.toString()));
		return orQuery.toString();
	}

}