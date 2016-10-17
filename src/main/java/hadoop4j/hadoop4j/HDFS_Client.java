package hadoop4j.hadoop4j;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import tools.time_tool;

public class HDFS_Client {
	private Configuration conf;
	private String hdfs_path = "";

	public HDFS_Client() {
		conf = new Configuration();
		conf.addResource(new Path("conf/hadoop_conf/core-site.xml"));
		conf.addResource(new Path("conf/hadoop_conf/hdfs-site.xml"));
		conf.addResource(new Path("conf/hadoop_conf/mapred-site.xml"));
		hdfs_path = conf.get("fs.defaultFS");

	}

	public boolean create_file(String file_path, long size) throws Exception{
		time_tool time = new time_tool();
		// System.out.print(time.get_time() + " : Start create file : size "
		// + size + " bytes");
			if (!file_path.startsWith("/")) {
				System.out
						.println("Error: The input file path should start with /");
			}
			Path path = new Path(hdfs_path + file_path);
			FileSystem fs = FileSystem.get(conf);
				// Create a new file and write data to it.
				FSDataOutputStream out = fs.create(path, false);
				byte[] b = new byte[102400];
				while (size > 0) {
					if (size > 102400) {
						out.write(b);
						size = size - 102400;
					} else {
						b = new byte[(int) size];
						for (int i = 0; i < b.length; i++) {
							b[i] = 0;
						}
						out.write(b);
						size = 0;
					}
				}
				out.close();
				//fs.close();
				
				// System.out.print(time.get_time() + " : Finished");
				return true;
			 
		 

	}

}
