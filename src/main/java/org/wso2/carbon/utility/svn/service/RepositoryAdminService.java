package org.wso2.carbon.utility.svn.service;

import org.tigris.subversion.svnclientadapter.ISVNClientAdapter;
import org.tigris.subversion.svnclientadapter.ISVNInfo;
import org.tigris.subversion.svnclientadapter.SVNClientAdapterFactory;
import org.tigris.subversion.svnclientadapter.SVNClientException;
import org.tigris.subversion.svnclientadapter.SVNUrl;
import org.tigris.subversion.svnclientadapter.commandline.CmdLineClientAdapterFactory;
import org.tigris.subversion.svnclientadapter.svnkit.SvnKitClientAdapterFactory;
import org.wso2.carbon.utility.versioncontrol.VersionControlArtifact;
import org.wso2.carbon.utility.versioncontrol.exception.GenericArtifactException;

import java.net.MalformedURLException;


public class RepositoryAdminService implements VersionControlArtifact {

	/**
	 * Create SVN repository.
	 *
	 * @param username
	 * @param password
	 * @param repositoryPath
	 */
	public void createRepository(String username, String password, String repositoryPath)
	  throws GenericArtifactException {
		SVNUrl url;
		String clientType;
		ISVNClientAdapter svnClient;
		ISVNInfo info;

		try {
			SvnKitClientAdapterFactory.setup();
		} catch (SVNClientException exception) {
			throw new GenericArtifactException(
			  "SVN Client Adaptor Connection Failed", exception,
			  "Connection_Failed_On_SVN_Client_Adaptor");
		}
		try {
			CmdLineClientAdapterFactory.setup();
		} catch (SVNClientException exception) {
			throw new GenericArtifactException(
			  "CmdLineClientAdapterFactory Connection Failed", exception,
			  "Connection_Failed_On_CmdLineClientAdapterFactory");
		}
		try {
			url = new SVNUrl(repositoryPath);
		} catch (MalformedURLException exception) {
			throw new GenericArtifactException(
			  "Invalid SVN URL", exception, "Invalid_SVN_URL");
		}
		try {
			clientType = SVNClientAdapterFactory.getPreferredSVNClientType();
		} catch (SVNClientException exception) {
			throw new GenericArtifactException(
			  "SVN Preferred Client type error, Failed to retrieve SVN PreferredClientType ",
			  exception, "Connection_Failed_On_CmdLineClientAdapterFactory");

		}
		svnClient = SVNClientAdapterFactory.createSVNClient(clientType);
		svnClient.setUsername(username);
		svnClient.setPassword(password);
		try {
			info = svnClient.getInfo(url);
		} catch (SVNClientException exception) {
			throw new GenericArtifactException(
			  "Invalid SVN repository URL", exception, "Invalid SVN Repository URL");
		}

		if (info != null) {
			throw new GenericArtifactException(
			  "Project is already exists, Please provide" +
			  "a suitable project name. ", "Project_Already_Exists");
		} else {
			try {
				if (svnClient != null) {
					svnClient.mkdir(url, true, "Directory creation by deployment synchronizer");
				}
			} catch (SVNClientException exception) {
				throw new GenericArtifactException(
				  "Failed to create the repository", exception, "Failed_To_Create_Repository");
			}
		}
	}

	public String getRepositoryType() {
		return "svn";
	}

}
