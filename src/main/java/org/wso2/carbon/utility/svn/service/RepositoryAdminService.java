package org.wso2.carbon.utility.svn.service;

import java.net.MalformedURLException;

import org.tigris.subversion.svnclientadapter.ISVNClientAdapter;
import org.tigris.subversion.svnclientadapter.ISVNInfo;
import org.tigris.subversion.svnclientadapter.SVNClientAdapterFactory;
import org.tigris.subversion.svnclientadapter.SVNClientException;
import org.tigris.subversion.svnclientadapter.SVNUrl;
import org.tigris.subversion.svnclientadapter.commandline.CmdLineClientAdapterFactory;
import org.tigris.subversion.svnclientadapter.svnkit.SvnKitClientAdapterFactory;
import org.wso2.carbon.utility.versioncontrol.IRepository;


public class RepositoryAdminService implements IRepository {

	
	public boolean createRepository(String username, String password, String repositoryPath){
		try {
            SvnKitClientAdapterFactory.setup();
        } catch (Throwable t) {}
		
		try {
            CmdLineClientAdapterFactory.setup();
        } catch (Throwable t) {}
		
		SVNUrl url = null;
		try {
			url = new SVNUrl(repositoryPath);
		} catch (MalformedURLException e) {
			return false;
		}
		
		String clientType = null;
		try {
            clientType = SVNClientAdapterFactory.getPreferredSVNClientType();
        } catch (SVNClientException e) {
        	return false;
        }
		
		ISVNClientAdapter svnClient = SVNClientAdapterFactory.createSVNClient(clientType);
        svnClient.setUsername(username);
        svnClient.setPassword(password);        
        try {

            ISVNInfo info = svnClient.getInfo(url);
            if (info != null) {
                return false;
            }
        } catch (SVNClientException ex) {
            try {
                svnClient.mkdir(url, true, "Directory creation by deployment synchronizer");
                return true;
            } catch (SVNClientException e) {
            	return false;
            }
        }
        return false;
		
	}
	
	public boolean isRepositoryExist(String username, String password, String repositoryPath){
		try {
            SvnKitClientAdapterFactory.setup();
        } catch (Throwable t) {}
		
		try {
            CmdLineClientAdapterFactory.setup();
        } catch (Throwable t) {}
		
		SVNUrl url = null;
		try {
			url = new SVNUrl(repositoryPath);
		} catch (MalformedURLException e) {
			return false;
		}
		
		String clientType = null;
		try {
            clientType = SVNClientAdapterFactory.getPreferredSVNClientType();
        } catch (SVNClientException e) {
        	return false;
        }
		
		ISVNClientAdapter svnClient = SVNClientAdapterFactory.createSVNClient(clientType);
		if(username != null){
			svnClient.setUsername(username);
	        svnClient.setPassword(password); 
		}
               
        try {

            ISVNInfo info = svnClient.getInfo(url);
            if (info != null) {
                return true;
            }
        } catch (SVNClientException ex) {
            return false;
        }
        return false;
	}
	
	public String getRepositoryType() {
		return "svn";
	}

}
