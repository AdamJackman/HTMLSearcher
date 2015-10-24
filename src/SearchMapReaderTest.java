
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;


public class SearchMapReaderTest {

	public ArrayList<String> testArrList; 
	
	@Test
	public void testSingleInContent(){
		//Set up
		testArrList = new ArrayList<String>();		
		testArrList.add("Jackman");				
		SearchMapReader http = new SearchMapReader("https://github.com/AdamJackman", testArrList);		
		//Test
		try{
			System.out.println("Test Single In Content - Sending GET request");
			BufferedReader response = http.sendGet();
			System.out.println("Test Single In Content - Processing GET request");
			long startTime = System.nanoTime();
			ArrayList<String> result = http.searchMapResponse(response);
			long endTime = System.nanoTime();			
			System.out.println("Result - " + result.toString());
			System.out.println("Calculation Time - " + ((double)(endTime- startTime) / 1000000000.0) + " seconds");
			assertTrue(result.size() == 1);
		}
		catch (Exception e){
			System.out.println(e);
			fail("Error: " + e);
		}
	}
	
	
	@Test
	public void testMultiInContent(){
		//Set up
		testArrList = new ArrayList<String>();		
		testArrList.add("Jackman");
		testArrList.add("Adam");	
		SearchMapReader http = new SearchMapReader("https://github.com/AdamJackman", testArrList);		
		//Test
		try{
			System.out.println("Test Multi In Content - Sending GET request");
			BufferedReader response = http.sendGet();
			System.out.println("Test Multi In Content - Processing GET request");
			long startTime = System.nanoTime();
			ArrayList<String> result = http.searchMapResponse(response);
			long endTime = System.nanoTime();			
			System.out.println("Result - " + result.toString());
			System.out.println("Calculation Time - " + ((double)(endTime- startTime) / 1000000000.0) + " seconds");
			assertTrue(result.size() == 2);
		}
		catch (Exception e){
			System.out.println(e);
			fail("Error: " + e);
		}
	}
	
	@Test
	public void testMulti3EndofContent(){
		//Set up
		testArrList = new ArrayList<String>();		
		testArrList.add("Jackman");
		testArrList.add("Adam");
		testArrList.add("Git");
		testArrList.add("</html>");
		SearchMapReader http = new SearchMapReader("https://github.com/AdamJackman", testArrList);		
		//Test
		try{
			System.out.println("Test Multi In Content - Sending GET request");
			BufferedReader response = http.sendGet();
			System.out.println("Test Multi In Content - Processing GET request");
			long startTime = System.nanoTime();
			ArrayList<String> result = http.searchMapResponse(response);
			long endTime = System.nanoTime();			
			System.out.println("Result - " + result.toString());
			System.out.println("Calculation Time - " + ((double)(endTime- startTime) / 1000000000.0) + " seconds");
			assertTrue(result.size() == 4);
		}
		catch (Exception e){
			System.out.println(e);
			fail("Error: " + e);
		}
	}
	
	@Test
	public void testSingleNotInContent(){
		//Set up
		testArrList = new ArrayList<String>();		
		testArrList.add("Bogus");
		SearchMapReader http = new SearchMapReader("https://github.com/AdamJackman", testArrList);		
		//Test
		try{
			System.out.println("Test Multi In Content - Sending GET request");
			BufferedReader response = http.sendGet();
			System.out.println("Test Multi In Content - Processing GET request");
			long startTime = System.nanoTime();
			ArrayList<String> result = http.searchMapResponse(response);
			long endTime = System.nanoTime();			
			System.out.println("Result - " + result.toString());
			System.out.println("Calculation Time - " + ((double)(endTime- startTime) / 1000000000.0) + " seconds");
			assertTrue(result.size() == 0);
		}
		catch (Exception e){
			System.out.println(e);
			fail("Error: " + e);
		}
	}

	
}
