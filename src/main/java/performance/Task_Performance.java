package performance;

import java.text.DecimalFormat;
import java.util.LinkedList;

import javassist.expr.NewArray;

import tools.file_size_convertor;
import tools.time_tool;

public class Task_Performance {
	String task_start_time ="";
	String task_end_time ="";
	LinkedList<Request_Performance> rp_list = new LinkedList<Request_Performance>();
	public Task_Performance(){
		 
	}
	
	public void get_task_time(){
	time_tool tt=new time_tool();
	Long max=tt.get_time_long(rp_list.get(0).request_start_time);
	Long min=tt.get_time_long(rp_list.get(0).request_start_time);
	int max_index=0;
	int min_index=0;
	for (int i = 0; i < rp_list.size(); i++) {
		if(tt.get_time_long(rp_list.get(i).request_end_time)>max){
			max = tt.get_time_long(rp_list.get(i).request_end_time);
			max_index = i;
		}
		if(tt.get_time_long(rp_list.get(i).request_start_time)<min){
			min=tt.get_time_long(rp_list.get(i).request_start_time);
			min_index = i;
		}
	}
	 task_start_time = rp_list.get(min_index).request_start_time;
	 task_end_time = rp_list.get(max_index).request_end_time;
		
	}
	 
	
	public synchronized int single_request_start(String requests){
		int request_id = rp_list.size();
		Request_Performance new_request_performance = new Request_Performance(request_id,requests);	
		new_request_performance.request_start();
		rp_list.add(new_request_performance);
		return request_id;
	}
	
	public void single_request_end_success(int request_id){
		time_tool tt=new time_tool();
		rp_list.get(request_id).request_end_time = tt.get_time();
		rp_list.get(request_id).success=true;
		rp_list.get(request_id).complete=true;
	}
	
	public void single_request_end_fail(int request_id,String error){
		time_tool tt=new time_tool();
		rp_list.get(request_id).request_end_time = tt.get_time();
		rp_list.get(request_id).success=false;
		rp_list.get(request_id).error=error;
		rp_list.get(request_id).complete=true;
	}
	
	public Request_Performance get_request_performance(int request_id){
		return rp_list.get(request_id);
	}
	
	public boolean all_request_complete(){
		for (int i = 0; i < rp_list.size(); i++) {
			if(rp_list.get(i).complete==false){
				return false;
			}
		}
		return true;
	}
	
	public void task_summary(){
		get_task_time();
		System.out.println("Task start time : "+task_start_time);
		System.out.println("Task end time : "+task_end_time);
		System.out.println("Task total request : "+rp_list.size());
		System.out.println();
		for (int i = 0; i < rp_list.size(); i++) {
			Request_Performance r=rp_list.get(i);
			System.out.println("=====================================================================================");
			System.out.println("Request : "+(r.request_id+1)+ "  Start: "+r.request_start_time+" End:"+r.request_end_time);
			System.out.println("File : "+r.file_path);
			System.out.println("Request Type: "+r.request_type+" Operation range: "+r.offset_start+" : "+r.offset_end);
			System.out.println("Success: "+(r.success?"Yes":"No"));
			if(!r.success){
				System.out.println("Error: "+r.error);
			}
		}
		
	}
	
	public void file_propagate_report(){
		long total_size=0;
		long total_time = 0;
		int file_num = rp_list.size();
		file_size_convertor fsc=new file_size_convertor();
		time_tool t= new time_tool();
		long time_elapse = t.get_time_gap(task_start_time,task_end_time);
		for (int i = 0; i < rp_list.size(); i++) {
			total_size+=(Long.valueOf(rp_list.get(i).offset_end)-Long.valueOf(rp_list.get(i).offset_start));
			total_time += t.get_time_gap(rp_list.get(i).request_start_time,rp_list.get(i).request_end_time);
		}
		double time_per_file =  ( (double)time_elapse) / ((double)file_num) ;
		double speed = ((double)total_size)/((double)time_elapse);
		
	 
		
	    System.out.println(); 
		System.out.println("============ File propagation report ============");
		System.out.println("File number : "+file_num);
		System.out.println("Total file size : "+fsc.readable_file_size(total_size));
		System.out.println("Total time spend : "+total_time + " s");
		System.out.println("Time elapse : "+time_elapse + " s");
		System.out.println("Time per file : "+(int)time_per_file+" s");
		System.out.println("Write speed : "+ fsc.readable_file_size(speed)+ " /s");
		System.out.println("============ File propagation report ============");
	}
	
	
}
