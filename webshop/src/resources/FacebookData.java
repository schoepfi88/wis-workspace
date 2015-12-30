package resources;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class FacebookData {
	private String accessToken;

	public FacebookData(String accessToken) {
		this.accessToken = accessToken;
	}
	// fetch profile data from user
	public String getFacebookData() {
		String profile = null;
		try {
			String g = "https://graph.facebook.com/me?" + accessToken;
			URL u = new URL(g);
			URLConnection c = u.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					c.getInputStream()));
			String inputLine;
			StringBuffer b = new StringBuffer();
			while ((inputLine = in.readLine()) != null)
				b.append(inputLine + "\n");
			in.close();
			profile = b.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR in getting FB graph data. " + e);
		}
		return profile;
	}
	// convert to json object
	public JsonObject getUserData(String userDataJson) {
		JsonObject profile = new Gson().fromJson(userDataJson, JsonObject.class);
		return profile;
	}
}
