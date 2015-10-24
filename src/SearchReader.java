import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SearchReader {

	private final String USER_AGENT = "Chrome/46.0.2490.71";

	public static void main(String[] args) throws Exception {

		SearchReader http = new SearchReader();

		System.out.println("Testing 1 - Send Http GET request");
		String response = http.sendGet();
	}
	
	// HTTP GET request
	private String sendGet() throws Exception {

		String url = "https://github.com/AdamJackman";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		RabinKarpSearcher searcher = new RabinKarpSearcher();
		String pat = "git".toLowerCase();
		String txt = "";
		
		while ((inputLine = in.readLine()) != null) {
			System.out.println(inputLine);
			//RabinKarp the Line
			txt = inputLine.toLowerCase();
			
	        searcher.precompute(pat);
	        int offset = searcher.search(txt);
	        System.out.println("found:    " + offset);
	        System.out.println("length:    " + txt.length());
	        if (offset < txt.length()){
	        	System.out.println("Match found");
	        	System.out.println("Match found");
	        	System.out.println("Match found");
	        	System.out.println("Match found");
	        	System.out.println("Match found");
	        	System.out.println("Match found");
	        	System.out.println("Match found");
	        	break;
	        } else {
	        	System.out.println("No Match found");
	        }
			
			response.append(inputLine);
		}
		in.close();

		//print result
		return response.toString();

	}
		
			
	
}
