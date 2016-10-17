package hadoop4j.hadoop4j;

import tools.time_tool;

public class hadoop_test {
	public static void main(String[] args) {
//		HDFS_Client hdfs = new HDFS_Client();
//		hdfs.create_file("/aa/bb",1024); 
		time_tool tt=new time_tool();
		System.out.println(tt.get_time_long("2016-11-04 06:06:33"));
	}
}
