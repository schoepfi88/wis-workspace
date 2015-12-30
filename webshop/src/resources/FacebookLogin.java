package resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class FacebookLogin {
	public static final String FB_APP_ID = "1507326402896938";
	public static final String FB_APP_SECRET = "1fd3eb014db6cca8ac89a81393ad515b";
	public static final String REDIRECT_URI = "http://localhost:8080/webshop";

	static String accessToken = "";

	// send user registration link
	public String getFBAuthUrl() {
		String fbLoginUrl = "";
		try {
			fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id=" + FacebookLogin.FB_APP_ID
					+ "&redirect_uri=" + URLEncoder.encode(FacebookLogin.REDIRECT_URI, "UTF-8") + "&scope=email";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fbLoginUrl;
	}

	// fetch url for profile data
	public String getFBGraphUrl(String code) {
		String fbGraphUrl = "";
		try {
			fbGraphUrl = "https://graph.facebook.com/oauth/access_token?" + "client_id=" + FacebookLogin.FB_APP_ID
					+ "&redirect_uri=" + URLEncoder.encode(FacebookLogin.REDIRECT_URI, "UTF-8") + "&client_secret="
					+ FB_APP_SECRET + "&code=" + code;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fbGraphUrl;
	}

	// check access token - return null if login fails
	public String getAccessToken(String code) {
		if ("".equals(accessToken)) {
			URL fbGraphURL;
			try {
				fbGraphURL = new URL(getFBGraphUrl(code));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				System.out.println("invalid code");
				return null;
			}
			URLConnection fbConnection;
			StringBuffer b = null;
			try {
				fbConnection = fbGraphURL.openConnection();
				BufferedReader in;
				in = new BufferedReader(new InputStreamReader(fbConnection.getInputStream()));
				String inputLine;
				b = new StringBuffer();
				while ((inputLine = in.readLine()) != null)
					b.append(inputLine + "\n");
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("connection failed");
				return null;
			}

			accessToken = b.toString();
			if (accessToken.startsWith("{")) {
				System.out.println("token invalid");
				return null;
			}
		}
		return accessToken;
	}
}