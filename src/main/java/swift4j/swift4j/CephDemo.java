package swift4j.swift4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.openstack4j.api.OSClient;
import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.openstack.OSFactory;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.common.Payloads;
import org.openstack4j.model.common.payloads.InputStreamPayload;
import org.openstack4j.model.identity.v3.Authentication.Identity.Token;
import org.openstack4j.model.identity.v3.User;
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
public class CephDemo {
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
		
		System.out.println("!!!");
		os.objectStorage().containers().create("myContainerName");
		
		org.openstack4j.model.identity.v3.Token token =  os.getToken();
		System.out.println(token.toString());
		String etag=null;
			
		

			InputStream inputstream = new ByteArrayInputStream(new byte[20]);
			InputStreamPayload inputload = new InputStreamPayload(inputstream);
			//etag = os.objectStorage().objects().put("myContainerName", "testfile_number_", inputload);
			//System.out.println(etag);

			
			// Simple
			os.objectStorage().containers().create("mine");
			// Simple
			os.objectStorage().containers().create("you");
			os.objectStorage().containers().createPath("mine", "/my");
			os.objectStorage().containers().createPath("you", "/my/you");
			
			

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
