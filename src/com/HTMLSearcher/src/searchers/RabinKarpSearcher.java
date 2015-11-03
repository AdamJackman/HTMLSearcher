package com.HTMLSearcher.src.searchers;
import java.math.BigInteger;
import java.util.Random;

public class RabinKarpSearcher {
	
	protected String pattern_;
    
    protected int patternLength_;
    protected int radix_;
    
    protected long randomPrime_;
    protected long patternHashValue_;
    protected long RM; // R^(M-1) % Q

    /**
     * Set up the RabinKarp's base variables 
     */
    public RabinKarpSearcher(){
    	radix_ = 256; //Our Hash size 
        randomPrime_ = longRandomPrime(31);
    }
    
    public RabinKarpSearcher(int radix, int primeBitLength){
    	radix_ = radix;  
        randomPrime_ = longRandomPrime(primeBitLength);
    }
    
    /**
     * Precompute the pattern that we want to search for in the String
     * @param pattern - The pattern we are looking for
     */
    public void precompute(String pattern){    	
    	pattern_ = pattern;
    	patternLength_ = pattern_.length();
    
        //Precompute R^(M-1) % Q for use in removing leading digit
        RM = 1;
        for (int i = 1; i < patternLength_; i++){
        	//Here is where Rabin and Karp earned their PHD
        	//Why this helps might require a Masters's in math
        	RM = (radix_ * RM) % randomPrime_; 
        }             
        patternHashValue_ = hash(pattern_, patternLength_);
    }

    /**
     * Find the summed hash value of the String up to size characters
     * @param key
     * @param size
     * @return
     */
    protected long hash(String key, int size) { 
        long h = 0; 
        for (int i = 0; i < size; i++) 
            h = (radix_ * h + key.charAt(i)) % randomPrime_; 
        return h; 
    }     
    
    /**
     * The hash value is a match now we manually check the characters
     * to ensure that it actually is a match
     * @param txt
     * @param i
     * @return
     */
    protected boolean check(String txt, int i) {
        for (int j = 0; j < patternLength_; j++) 
            if (pattern_.charAt(j) != txt.charAt(i + j)){
            	return false;
            }                 
        return true;
    }
 
    /**
     * Returns the index of the first occurrence of the pattern string
     * in the text string.
     * 
     * The premise is that by assigning each character a value (hash) and then
     * computing with a prime modulus we can create a unique output for each pattern
     * so if the output numbers are the same we have a matching string 
     *
     * @param  txt the text string
     * @return the index of the first occurrence of the pattern string
     *         in the text string; N if no such match
     */
    public int search(String txt) {
        int txtLength = txt.length();
        //if text length is too small we cannot have a match
        if (txtLength < patternLength_){
        	return txtLength;
        }
        //Get the hash value of the length characters in the base case
        long txtHash = hash(txt, patternLength_); 
        if ((patternHashValue_ == txtHash) && check(txt, 0)){
        	return 0;
        }            
        //Check for hash match; if hash match, check for exact match
        for (int i = patternLength_; i < txtLength; i++) {
        	int offset = i - patternLength_ + 1;
        	
        	// Remove leading digit, add trailing digit, check for match.
        	// We heard Modulus is good for hashing, so Rabin Karp modulused the modulused modulus
            txtHash = (txtHash + randomPrime_ - RM*txt.charAt(i-patternLength_) % randomPrime_) % randomPrime_; 
            txtHash = (txtHash*radix_ + txt.charAt(i)) % randomPrime_; 
                        
            if ((patternHashValue_ == txtHash) && check(txt, offset)){
            	// Match found
                return offset;
            }            	
        }
        // No match in the text
        return txtLength;
    }

    /**
     * Because for some reason the last prime wasn't up to par
     * @param bitLength
     */
    public void recomputePrime(int bitLength){
    	randomPrime_ = longRandomPrime(bitLength);
    }
    
    //Create random bitLength bit prime
    private long longRandomPrime(int bitLength) {
        BigInteger prime = BigInteger.probablePrime(bitLength, new Random());
        return prime.longValue();
    }
}