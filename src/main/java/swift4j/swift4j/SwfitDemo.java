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

/**
 * 
 * @author tongxin
 * you need to add 
 * swift controller and proxy ipaddrs in your /etc/hosts 
 * since the return address contains "controller" and "proxy" as string
 * instead of ip address
 */
public class SwfitDemo {
	public static void main(String[] args){
		
		String projectName="swiftservice";
		String domainName="default";
		String userId="user01";
		String password="a11";
		String url="http://controller:5000/v3";
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

			
		
			InputStream inputstream = new ByteArrayInputStream(new byte[20]);
			InputStreamPayload inputload = new InputStreamPayload(inputstream);
			String etag=null;
		

			etag = os.objectStorage().objects().put("myContainerName", "testfile_number_"+1, inputload);
			System.out.println(etag);
			
		}
		
	
	

}
