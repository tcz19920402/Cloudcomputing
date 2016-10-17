package swift4j.swift4j;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TESTRequestAdapterRESTAPITest extends TestCase {
	private RequestAdapterRESTAPI api = null;
	
	public  static void main(String[] args){
		RequestAdapterRESTAPI api= new RequestAdapterRESTAPI();
		String[] res=api.commandType("CREATE_FILE : test2\6c33a983eec1b409,0:2291 : 5");
		for(String s:res){
			System.out.println(s);}
	}
		
	@Before
	public void init(){
		api= new RequestAdapterRESTAPI();
	}
	
	
	public void testCommandType(){
		String[] res=api.commandType("CREATE_FILE : test2\6c33a983eec1b409,0:2291 : 5");
		for(String s : res)
			System.out.println(s);
		assertEquals(res[0],"create_file");
	}
	

}
