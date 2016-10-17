package hadoop4j.hadoop4j;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import org.apache.commons.lang.StringEscapeUtils;

import performance.Task_Performance;

import sample.Request.FSPropagate;
import sample.Request.FSPropagateAdapter;
import sample.Request.LListenRequests;
import swift4j.swift4j.RequestBroker;
import tools.request_convertor;

public class test {
	public static void main(String[] args) {
 			String[] input = {"files/test2.txt","files/rank.txt"};
 			//Register broker the file_create adapter
 			Task_Performance task_performance = new Task_Performance();
 			LListenRequests broker = new RequestBroker("hadoop",task_performance);
 			FSPropagateAdapter adapter = new FSPropagateAdapter();
 			adapter.registerListener(broker);
 			adapter.init(input);
 			
 		    while(true){
 		    	if(task_performance.all_request_complete()){
 		    		task_performance.task_summary();
 		    		task_performance.file_propagate_report();
 		    		break;
 		    	}
 		    }
 			
	}
}
