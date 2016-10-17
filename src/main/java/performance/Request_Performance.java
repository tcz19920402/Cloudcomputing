package performance;

import tools.request_convertor;
import tools.time_tool;

public class Request_Performance {
	int request_id ;
	String request_start_time ="";
	String request_end_time ="";
	
	String request_type ="";
	String file_path ="";
	String offset_start ="";
	String offset_end ="";
	
	boolean success=false;
    String error="";
	
    boolean complete=false;
    
	public Request_Performance(int request_id,String request){
		request_convertor rc = new request_convertor();
		String[] tmp = rc.convert(request);
		this.request_id = request_id;
		this.request_type = tmp[0];
		this.file_path =tmp[1];
		this.offset_start =tmp[2];
		this.offset_end =tmp[3];
	}
	
	public void request_start(){
		time_tool tt=new time_tool();
		this.request_start_time = tt.get_time();
	};
	
	public void request_end_success(){
		time_tool tt=new time_tool();
		this.request_end_time = tt.get_time();
		this.success=true;
	}

	public void request_end_fail(String error){
		time_tool tt=new time_tool();
		this.request_end_time = tt.get_time();
		this.success=false;
		this.error=error;
	}
	
	
	

}
