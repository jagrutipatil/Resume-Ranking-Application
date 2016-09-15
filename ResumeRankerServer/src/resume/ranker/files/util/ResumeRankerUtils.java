package resume.ranker.files.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;

/**
 * TODO Add documentation
 */
public class ResumeRankerUtils {
	private static final Gson GSON = new Gson();
		
	public static String inputstreamToJSON(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String json = "";
		if (br != null) {
			System.out.println("Inside IF");
			json = br.readLine();
		}

		return json;
	}

	public static String mapToJson(Object source) {
		return GSON.toJson(source);
	}
}
