package org.wso2.carbon.utility.svn;

import java.net.MalformedURLException;

import org.tigris.subversion.svnclientadapter.ISVNClientAdapter;
import org.tigris.subversion.svnclientadapter.ISVNInfo;
import org.tigris.subversion.svnclientadapter.SVNClientAdapterFactory;
import org.tigris.subversion.svnclientadapter.SVNClientException;
import org.tigris.subversion.svnclientadapter.SVNUrl;
import org.tigris.subversion.svnclientadapter.commandline.CmdLineClientAdapterFactory;
import org.tigris.subversion.svnclientadapter.svnkit.SvnKitClientAdapterFactory;

public class Test {

	public static void main(String[] args) throws SVNClientException, MalformedURLException {
		
		String url = "https://epid-project.googlecode.com/svn/trunk/test/";
        String username = "cnapagoda@gmail.com";
		String password = "Hs2dX5Nz7CQ9";
		
		try {
            SvnKitClientAdapterFactory.setup();
        } catch (Throwable t) {}
		
		try {
            CmdLineClientAdapterFactory.setup();
        } catch (Throwable t) {}
		
		
		SVNUrl svnUrl = new SVNUrl(url);
		String clientType = null;
        if (clientType == null) {
            try {
                clientType = SVNClientAdapterFactory.getPreferredSVNClientType();
            } catch (SVNClientException e) {
                System.out.println("Error while retrieving the preferred SVN client type" + e);
            }
        }

		ISVNClientAdapter svnClient = SVNClientAdapterFactory.createSVNClient(clientType);
        svnClient.setUsername(username);
        svnClient.setPassword(password);
        

        try {

            ISVNInfo info = svnClient.getInfo(svnUrl);
            if (info != null) {
                System.out.println("Remote directory: " + svnUrl + " exists");
            }
        } catch (SVNClientException ex) {
            System.out.println("Error while retrieving information from the directory: " + svnUrl);
                System.out.println("Attempting to create the directory: " + svnUrl);
            
            try {
                svnClient.mkdir(svnUrl, true, "Directory creation by deployment synchronizer");
            } catch (SVNClientException e) {
            	System.out.println("Error while attempting to create the directory: " + svnUrl);
            }
        }

	}

}
