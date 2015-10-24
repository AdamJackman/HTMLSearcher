import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchReader {
	
	private static final String USER_AGENT = "Chrome/46.0.2490.71";
	private String url_;
	private String pattern_;
	
	public SearchReader(){
		url_ = "https://github.com/AdamJackman";
	}
	
	public SearchReader(String url, String pattern){
		url_ = url;
		pattern_ = pattern;
	}
	
	/**
	 * Goes to the URL and will grab the Content
	 * @return BufferedReader - Contains page content
	 * @throws Exception
	 */
	public BufferedReader sendGet() throws Exception {		
		//Change the url String into a URL
		URL obj = new URL(url_);
		//Create a Connection
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();

		System.out.println("\nSending 'GET' request to URL : " + url_);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader response = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));		
		return response;		
	}
				
	/**
	 * Will take the Content line by line and search for the pattern
	 * @param in
	 * @return Will return the position of the first occurrence, otherwise -1 for not found
	 * @throws Exception
	 */
	public int searchResponse(BufferedReader in) throws Exception {

		String inputLine;
		StringBuffer response = new StringBuffer();
		
		//Use the Rabin Karp Searcher to find a match
		RabinKarpSearcher searcher = new RabinKarpSearcher();
		String pat = pattern_.toLowerCase();
		searcher.precompute(pat);
		
		String txt = "";
		boolean found = false;
		int offset = 0;
		
		while ((inputLine = in.readLine()) != null) {
			//RabinKarp each line
			txt = inputLine.toLowerCase();
	        offset = searcher.search(txt);
	        if (offset < txt.length()){
	        	System.out.println("Match found");
	        	found = true;
	        	break;
	        }
			response.append(inputLine);
		}
		in.close();
		return (found)? offset : -1;
	}			
}
