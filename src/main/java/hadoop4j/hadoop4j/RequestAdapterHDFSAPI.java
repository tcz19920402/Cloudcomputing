package hadoop4j.hadoop4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import performance.Task_Performance;

import swift4j.swift4j.RequestAdapter;
import tools.request_convertor;
import tools.time_tool;

public class RequestAdapterHDFSAPI implements RequestAdapter {
	Task_Performance task_performance;

	public RequestAdapterHDFSAPI(Task_Performance task_performance) {
		this.task_performance = task_performance;
	}

	public void transfer(String originalRequests) {
		int request_id = task_performance
				.single_request_start(originalRequests);
		String[] command = commandType(originalRequests);
		 
		// ExecutorService pool = Executors.newFixedThreadPool(16);
		// pool.execute(new NewAdapter(token,commandType(originalRequests)));
		// new Thread(new NewAdapter(command)).start();
		NewAdapter new_adapter = new NewAdapter(command,task_performance,request_id );
	    new Thread(new_adapter).start();
		//  run(command,task_performance, request_id);
	}

	/**
	 * @param originalRequests
	 * @return comand[0] command type comand[1] file_path comand[2] offset start
	 *         comand[3] offset end
	 */

	public String[] commandType(String originalRequests) {
		request_convertor rc = new request_convertor();
		String[] comand = rc.convert(originalRequests);
		return comand;

	}

	public void run(String[] command, Task_Performance task_performance,int request_id ) {
		String command_type = command[0];
		String file_path = command[1];
		String offset_start = command[2];
		String offset_end = command[3];
		HDFS_Client hdfs=new HDFS_Client();
		try {
		if (command_type == "RANDOM_READ") {

		} else if (command_type == "RANDOM_WRITE") {

		} else if (command_type == "SEQ_READ") {

		} else if (command_type == "SEQ_WRITE") {

		} else if (command_type == "DELETE") {

		} else if (command_type == "RMDIR") {

		} else if (command_type == "APPEND") {

		} else if (command_type.startsWith("CREATE_FILE")) {
			long size = Long.valueOf(offset_end) - Long.valueOf(offset_start);
			time_tool tt=new time_tool();
			System.out.println("Start "+request_id+" : "+tt.get_time());
				hdfs.create_file(file_path, size);
				task_performance.single_request_end_success(request_id);
				System.out.println("End "+request_id+" : "+tt.get_time());
		} else if (command_type == "CREATE_DIR") {

		} else if (command_type == "LS") {

		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			task_performance.single_request_end_fail(request_id,
					e.toString());
			//e.printStackTrace();
		}
	}

}

class NewAdapter implements Runnable {
	Task_Performance task_performance;
	String command_type;
	String file_path;
	String offset_start;
	String offset_end;
	HDFS_Client hdfs;
	int request_id;
	public NewAdapter(String[] command, Task_Performance task_performance,int request_id ) {
		this.command_type = command[0];
		this.file_path = "/" + command[1];
		this.offset_start = command[2];
		this.offset_end = command[3];
		this.hdfs = new HDFS_Client();
		this.task_performance = task_performance;
		this.request_id=request_id;
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
		if (command_type == "RANDOM_READ") {

		} else if (command_type == "RANDOM_WRITE") {

		} else if (command_type == "SEQ_READ") {

		} else if (command_type == "SEQ_WRITE") {

		} else if (command_type == "DELETE") {

		} else if (command_type == "RMDIR") {

		} else if (command_type == "APPEND") {

		} else if (command_type.startsWith("CREATE_FILE")) {
			time_tool tt=new time_tool();
			System.out.println("Start "+request_id+" : "+tt.get_time());
			long size = Long.valueOf(offset_end) - Long.valueOf(offset_start);
			hdfs.create_file(file_path, size);
			System.out.println("End "+request_id+" : "+tt.get_time());
		 
		} else if (command_type == "CREATE_DIR") {

		} else if (command_type == "LS") {

		}	
		task_performance.single_request_end_success(request_id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			task_performance.single_request_end_fail(request_id,
					e.toString());
			//e.printStackTrace();
		}
		
	}
}
