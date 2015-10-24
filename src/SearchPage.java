import java.io.BufferedReader;
import java.util.ArrayList;


public class SearchPage {

	private String url_;
	private String pattern_;
	private ArrayList<String> patterns_;
	private int version_;
	
	private static final int SINGLE_VERSION = 1;
	private static final int MULTI_VERSION = 2;	
	
	/**
	 * constructor for the single version 
	 * @param url
	 * @param pattern
	 */
	public SearchPage(String url, String pattern){
		//Single search
		url_ = url;
		version_ = SINGLE_VERSION;
		pattern_ = pattern;		
	}
	
	/**
	 * Constructor for the multi version
	 * @param url
	 * @param patterns
	 */
	public SearchPage(String url, ArrayList<String> patterns){
		//Multi search
		url_ = url;
		version_ = MULTI_VERSION;
		patterns_ = patterns;
	}
	
	/**
	 * Runs the search on the page using the version initialized with
	 */
	public ArrayList<String> search(){

		//Check that the version is initialized
		if(version_ == 0){
			System.out.println("Search Page is not initialized");
			return null;
		}
		
		ArrayList<String> results = new ArrayList<String>();
		if(version_ == SINGLE_VERSION){			
			try{
				SearchReader http = new SearchReader(url_, pattern_);
				BufferedReader response = http.sendGet();
				int result = http.searchResponse(response);
				if(result != -1) results.add(pattern_);
				return results;
			} catch (Exception e){
				System.out.println("Unexpected error: " + e);
			}
		}
		else if(version_ == MULTI_VERSION){
			try{
				SearchMapReader http = new SearchMapReader(url_, patterns_);
				BufferedReader response = http.sendGet();
				results = http.searchMapResponse(response);
				return results;
			} catch (Exception e){
				System.out.println("Unexpected error: " + e);
			}
		}
		//only reached through exceptions
		return null;
	}

}
