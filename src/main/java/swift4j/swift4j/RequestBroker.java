package swift4j.swift4j;

import hadoop4j.hadoop4j.RequestAdapterHDFSAPI;

import java.util.LinkedList;

import performance.Task_Performance;

import sample.Request.LListenRequests;
/**
 * adapter for different file system
 * @author tongxin
 *
 */
public class RequestBroker implements LListenRequests{
	RequestAdapter requestAdapter=null;
	String requestType=null;
	//LinkedList<String> request_result=new LinkedList<String>();
	Task_Performance task_performance =null;
	
	
	public RequestBroker(){}
	
	public RequestBroker(String requestType){
		this.requestType=requestType;
		//change to factory pattern later
		if(requestType.equals("rest"))
			requestAdapter = new RequestAdapterRESTAPI();
		else
			System.out.println("wront request adapter type");
		
	}
	
	public RequestBroker(String requestType,Task_Performance task_performance){
		this.requestType=requestType;
		this.task_performance=task_performance;
		//change to factory pattern later
		if(requestType.equals("rest"))
			requestAdapter = new RequestAdapterRESTAPI();
		else if(requestType.equals("hadoop"))
			requestAdapter = new RequestAdapterHDFSAPI(task_performance);
		else
			System.out.println("wront request adapter type");
		
	}
	
	

	public void listenRequests(String requests) {
		//System.out.println(requests);
		//request_result.add(requests);
		requestAdapter.transfer(requests);
		
	}
	
//	public LinkedList<String> getRequests() {
//		return this.request_result ;
//	   // requestAdapter.transfer(requests);
//	}
	 

}
