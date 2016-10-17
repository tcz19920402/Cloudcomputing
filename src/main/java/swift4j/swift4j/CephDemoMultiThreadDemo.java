package swift4j.swift4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.openstack.OSFactory;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.common.payloads.InputStreamPayload;
import org.openstack4j.model.identity.v3.Token;
import org.openstack4j.model.storage.object.SwiftContainer;
import org.openstack4j.model.storage.object.SwiftObject;

/**
 * 
 * @author tongxin
 * you need to add 
 * ceph keystone and radosgw ipaddrs in your /etc/hosts 
 * since the return address contains "controller-ceph" and "radosgw" as string
 * instead of ip address
 */
public class CephDemoMultiThreadDemo {

	public static void main(String[] args){
		
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
		System.out.println(token.toString());
		long start=System.currentTimeMillis();
		
		ExecutorService pool =   Executors.newFixedThreadPool(16);
		
		
		
		
		
		for(int i=0;i<=999;i++){
			System.out.println("put file number :"+i);
			pool.execute(new NewThread(token));
		}
		
		
		long duration=(System.currentTimeMillis()-start);
		long size = 1024*1000;
		System.out.println("bw:"+(size/duration));

		
			List<? extends SwiftContainer> containers = os.objectStorage().containers().list();
			for(SwiftContainer o:containers){
				System.out.println("container name:"+o.getName());
				List<? extends SwiftObject> objs = os.objectStorage().objects().list(o.getName());
				for(SwiftObject i:objs){
					System.out.println("object name:"+i.getName());
				}
				
			}
				
			
			
		}

		
	
	

}

class NewThread implements Runnable{
	Token token=null;
	public NewThread(Token token){
		this.token=(Token) token;
		
		
	}


	public void run() {
		// TODO Auto-generated method stub
		OSClientV3 os = OSFactory.clientFromToken(token);
		
		System.out.println("new thread");
		InputStream inputstream = new ByteArrayInputStream(new byte[1024000]);
		InputStreamPayload inputload = new InputStreamPayload(inputstream);
		String etag = os.objectStorage().objects().put("myContainerName", "testfile_number_"+System.nanoTime(), inputload);
		System.out.println(etag);
		
	}

}
