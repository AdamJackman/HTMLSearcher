package com.HTMLSearcher.src.readers;
import java.io.BufferedReader;
import java.util.ArrayList;

import com.HTMLSearcher.src.searchers.RabinKarpMapSearcher;


public class SearchMapReader extends SearchReader{		
	
	ArrayList<String> patterns_;
	
	public SearchMapReader(){
		url_ = "https://github.com/AdamJackman";
	}
	
	public SearchMapReader(String url, ArrayList<String> patterns){
		url_ = url;
		patterns_ = patterns;
	}

	/**
	 * Will take the Content line by line and search for the pattern
	 * @param in
	 * @return Will return the position of the first occurrence, otherwise -1 for not found
	 * @throws Exception
	 */
	public ArrayList<String> searchMapResponse(BufferedReader in) throws Exception {

		String inputLine;		
		RabinKarpMapSearcher searcher = new RabinKarpMapSearcher();
		//Force into lower case
		for(int i=0; i<patterns_.size(); i++){
			patterns_.set(i, patterns_.get(i).toLowerCase());
		}		
		//precompute the list
		searcher.precompute(patterns_);		
		String txt = "";
		ArrayList<String> matches = new ArrayList<String>();
		
		//Search line at a time through the patterns
		while ((inputLine = in.readLine()) != null) {
			//RabinKarp each line
			txt = inputLine.toLowerCase();
			ArrayList<String> lineMatches = searcher.searchMap(txt);			
	        if (lineMatches.size() > 0){
	        	matches.addAll(lineMatches);
	        	System.out.println("Matches updated");
	        	if(matches.size() >= patterns_.size()){
	        		System.out.println("All Matches Found");
		        	break;
	        	}
	        }
		}
		in.close();
		return matches;
	}			
}
