import static org.junit.Assert.*;

import java.io.BufferedReader;

import org.junit.Test;


public class SearchReaderTest {

	@Test
	public void testInContent(){
		SearchReader http = new SearchReader("https://github.com/AdamJackman", "Adam");
		try{
			System.out.println("Test In Content - Sending GET request");
			BufferedReader response = http.sendGet();
			int result = http.searchResponse(response);
			System.out.println("Result - " + result);
			assert(result != -1);			
		}
		catch (Exception e){
			fail("Error: " + e);
		}
	}

	@Test
	public void testBottomOfContent(){
		SearchReader http = new SearchReader("https://github.com/AdamJackman", "</html>");
		try{
			System.out.println("Test Bottom of Content - Sending GET request");
			BufferedReader response = http.sendGet();
			int result = http.searchResponse(response);
			System.out.println("Result - " + result);
			assert(result != -1);			
		}
		catch (Exception e){
			fail("Error: " + e);
		}
	}
	
	@Test
	public void testNotInContent(){
		SearchReader http = new SearchReader("https://github.com/AdamJackman", "failure");
		try{
			System.out.println("Test Not In Content - Sending GET request");
			BufferedReader response = http.sendGet();
			int result = http.searchResponse(response);
			System.out.println("Result - " + result);
			assert(result == -1);			
		}
		catch (Exception e){
			fail("Error: " + e);
		}
	}
	
}
