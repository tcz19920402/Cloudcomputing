package tools;

import java.text.DecimalFormat;

public class test {
public static void main(String[] args) {

time_tool t=new time_tool();
System.out.println(t.get_time_gap("2010-04-02 02:02:02", "2010-04-02 02:03:03"));
DecimalFormat df=new DecimalFormat("#.##");   
System.out.println(df.format(3.142587));
file_size_convertor fsc =new file_size_convertor();
System.out.println(fsc.readable_file_size(764416));

 

}
}
