package swift4j.swift4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.common.payloads.InputStreamPayload;
import org.openstack4j.model.identity.v3.Token;
import org.openstack4j.openstack.OSFactory;

public class RequestAdapterRESTAPI implements RequestAdapter {
	
	public RequestAdapterRESTAPI(){
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void transfer(String originalRequests) {
		String projectName="admin";
		String domainName="default";
		String userId="admin";
		String password="a11";
		String url="http://25.15.195.115:5000/v3";
		Identifier domainIdent = Identifier.byName(domainName);
		Identifier projectIdent = Identifier.byName(projectName);		
		OSClientV3 os = OSFactory.builderV3()
	              .endpoint(url)
	              .credentials(userId, password,Identifier.byName(domainName))
	              .scopeToProject(projectIdent, domainIdent)
	              .authenticate();
		Token token =  os.getToken();
		//ExecutorService pool =   Executors.newFixedThreadPool(16);
		

		//pool.execute(new NewAdapter(token,commandType(originalRequests)));
		System.out.println(originalRequests+"in adapter");
		new Thread(new NewAdapter(token,commandType(originalRequests))).start();
		
		
	}
	
	/**
	 *analysis command type 
	 * @param originalRequests
	 * @return
	 */
	public String[] commandType(String originalRequests){
		int precut=0;
		String[] res = new String[5];// 0 is cmd, 1 is file path, 3 is filesize
		for(int i=0;i<originalRequests.length();i++){
			if(originalRequests.charAt(i)==':' ){
				if(res[0]==null)
					res[0]=originalRequests.substring(precut, i).toLowerCase().trim();
				else if(res[2]==null)
					res[2]=originalRequests.substring(precut, i).trim();
				else if(res[3]==null)
					res[3]=originalRequests.substring(precut, i).trim();
				precut=i+1;
			}else if(originalRequests.charAt(i)==','){
				if(res[1]==null)
					res[1]=originalRequests.substring(precut, i).trim();
				precut=i+1;
			}		
		}
		return res;

	}
	
	



}

class NewAdapter implements Runnable{
	Token token=null;
	String[] command=null;
	public NewAdapter(Token token,String[] command){
		this.token=(Token) token;
		this.command=command;
		
	}


	public void run() {
		// TODO Auto-generated method stub
		OSClientV3 os = OSFactory.clientFromToken(token);
		
		System.out.println(command[3]);
		int size=Integer.valueOf(command[3]).intValue();
		System.out.println("inputstream size:"+size);
		InputStream inputstream = new ByteArrayInputStream(size==0?null:new byte[size]);
		InputStreamPayload inputload = new InputStreamPayload(inputstream);
		
		//sub command analysis
		String[] subCommands=new String[5];// 0 is container, 1 is path, 2 is filename
		int headCut=0;
		for(int i=0;i<command[1].length();i++){
			if(command[1].charAt(i)=='\\' ){			
					subCommands[0]=command[1].substring(headCut,i).trim();
					break;
					
			}			
		}
		int tailCut=0;
		for(int i=command[1].length()-1;i>=0;i--){
			if(command[1].charAt(i)=='\\' ){			
					subCommands[2]=command[1].substring(i+1).trim();
					tailCut=i;
					break;					
			}			
		}
		command[1]=command[1].substring(0,tailCut).trim();
		
		String etag=null;
		if(command[0].equals("create_file")){
			//create pseudo directories
			System.out.println(subCommands[0]);
			System.out.println(subCommands[1]);
			System.out.println(subCommands[2]);
			
			//os.objectStorage().containers().createPath(subCommands[0], "/test2");
			// Simple
			os.objectStorage().containers().create(subCommands[0]);
			etag=os.objectStorage().objects().put(subCommands[0], subCommands[2], inputload);
			System.out.println("etag:"+etag);
		}
			 
		
		
		System.out.println("put file"+subCommands[2]+"to container: "+subCommands[0]+"at path:"+subCommands[1]);
		
		
	}

}
