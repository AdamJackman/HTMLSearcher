package com.HTMLSearcher.src.searchers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class RabinKarpMapSearcher extends RabinKarpSearcher{
	
	//Stores the precomputed value and an integer to represent the offset
	protected HashMap<Long, String> hashedPatternMap_;
	protected HashMap<Long, Long> hashedRMMap_;
	
	  /**
     * Precompute the patterns that we want to search for in the String
     * @param patterns - The patterns we are looking for in an ArrayList
     */
    public void precompute(ArrayList<String> patterns){    	
    	
    	hashedPatternMap_ = new HashMap<Long, String>();
     	hashedRMMap_ = new HashMap<Long, Long>();
    	
    	for (int i=0; i<patterns.size(); i++){
    		pattern_ = patterns.get(i);
    		patternLength_ = pattern_.length();            
            //Precompute R^(M-1) % Q for use in removing leading digit
            RM = 1;
            for (int j = 1; j < patternLength_; j++){
            	//Here is where Rabin and Karp earned their PHD
            	//Why this helps might require a Masters's in math
            	RM = (radix_ * RM) % randomPrime_; 
            }                      
            patternHashValue_ = super.hash(pattern_, patternLength_);
            //Update the HashMap_
            hashedPatternMap_.put(patternHashValue_, pattern_);
            hashedRMMap_.put(patternHashValue_, RM);
            //TODO: store the RM for each of the values
    	}
    	
    }
    
  
    public ArrayList<String> searchMap(String txt) {
        int txtLength = txt.length();
        ArrayList<String> foundList = new ArrayList<String>();

        //Get the hash value of the length characters in the base case        
        Iterator<Map.Entry<Long, String>> iterator = hashedPatternMap_.entrySet().iterator() ;
        while(iterator.hasNext()){
            Map.Entry<Long, String> patternEntry = iterator.next();
            //Set up the variables
            patternLength_ = patternEntry.getValue().length();
            pattern_ = patternEntry.getValue();
            patternHashValue_ = patternEntry.getKey();
            if(patternLength_ > txt.length()){
            	return foundList;
            }
            
            long txtHash = hash(txt, patternLength_); 
            if ((patternHashValue_ == txtHash) && check(txt, 0)){
            	iterator.remove();
            	foundList.add(pattern_);
            	hashedPatternMap_.remove(patternHashValue_);
            }               
        }
        
        //Get the hash value of the length characters in the base case        
        iterator = hashedPatternMap_.entrySet().iterator() ;
        while(iterator.hasNext()){
            Map.Entry<Long, String> patternEntry = iterator.next();
            //Set up the variables
            patternLength_ = patternEntry.getValue().length();
            pattern_ = patternEntry.getValue();
            patternHashValue_ = patternEntry.getKey();            
            long txtHash = hash(txt, patternLength_);
            
            //Check for hash match; if hash match, check for exact match
            for (int i = patternLength_; i < txtLength; i++) {
            	int offset = i - patternLength_ + 1;
            	
            	// Remove leading digit, add trailing digit, check for match.
            	//TODO: This RM is not accurate for all hashes
            	RM = hashedRMMap_.get(patternHashValue_);
                txtHash = (txtHash + randomPrime_ - RM*txt.charAt(i-patternLength_) % randomPrime_) % randomPrime_; 
                txtHash = (txtHash*radix_ + txt.charAt(i)) % randomPrime_; 
                            
                if ((patternHashValue_ == txtHash) && check(txt, offset)){
                	iterator.remove();
                	foundList.add(pattern_);
                	break;
                }            	
            }           
        }        
        return foundList;
    }


	
}
