package com.HTMLSearcher.main;

import java.io.BufferedReader;
import java.util.ArrayList;

import com.HTMLSearcher.src.readers.SearchMapReader;
import com.HTMLSearcher.src.readers.SearchReader;


public class HTMLSearcher {

	private String url_;
	private String pattern_;
	private ArrayList<String> patterns_;
	private int version_ = 0;
	
	private static final int SINGLE_VERSION = 1;
	private static final int MULTI_VERSION = 2;	
	
	/**
	 * constructor for the single String version 
	 * @param url
	 * @param pattern
	 */
	public HTMLSearcher(String url, String pattern){
		//Single search
		url_ = url;
		version_ = SINGLE_VERSION;
		pattern_ = pattern;		
	}
	
	/**
	 * Constructor for the multi String version
	 * @param url
	 * @param patterns
	 */
	public HTMLSearcher(String url, ArrayList<String> patterns){
		//Multi search
		url_ = url;
		version_ = MULTI_VERSION;
		patterns_ = patterns;
	}
	
	/**
	 * Runs the search on the page using the version initialized
	 * @return - Returns and array List of Strings searched for that are also on the page 
	 */
	public ArrayList<String> search(){

		//Check that the version is initialized
		if(version_ == 0){
			System.out.println("Search Page is not initialized");
			return null;
		}		
		try{
			ArrayList<String> results = new ArrayList<String>();
			if(version_ == SINGLE_VERSION){
				SearchReader searcher = new SearchReader(url_, pattern_);
				BufferedReader response = searcher.sendGet();
				int result = searcher.searchResponse(response);
				if(result != -1) results.add(pattern_);
			} else if (version_ == MULTI_VERSION){
				SearchMapReader searcher = new SearchMapReader(url_, patterns_);
				BufferedReader response = searcher.sendGet();
				results = searcher.searchMapResponse(response);						
			}
			return results;
		} catch (Exception e){
			System.out.println("Unexpected error: " + e);
			return null;
		}		
	}

}
