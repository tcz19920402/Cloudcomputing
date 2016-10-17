package swift4j.swift4j;


import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.openstack.OSFactory;
import sample.Request.FSPropagateAdapter;
import sample.Request.LListenRequests;
 


public class Controller {
	
	public static void main(String[] args){
		
		String projectName="admin";
		String domainName="default";
		String userId="admin";
		String password="a11";
		String url="http://controller:5000/v3";
		String[] input = {"files/test2.txt","files/rank.txt"};
		String requestType="rest";
		//OSClientV3 os = authenticate(url,userId,password,domainName,projectName);
		
		LListenRequests broker = new RequestBroker(requestType);
		FSPropagateAdapter adapter = new FSPropagateAdapter();
		adapter.registerListener(broker);
		System.out.println("ok");
		adapter.init(input);
		System.out.println("ok");
		
		
	}
	
	private static OSClientV3 authenticate(String url,String userId,String password,String domainName,String projectName){
		Identifier domainIdent = Identifier.byName(domainName);
		Identifier projectIdent = Identifier.byName(projectName);
		
		OSClientV3 os = OSFactory.builderV3()
	              .endpoint(url)
	              .credentials(userId, password,Identifier.byName(domainName))
	              .scopeToProject(projectIdent, domainIdent)
	              .authenticate();
		return os;
	}

}
