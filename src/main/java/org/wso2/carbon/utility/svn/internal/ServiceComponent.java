package org.wso2.carbon.utility.svn.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.utility.svn.service.RepositoryAdminService;
import org.wso2.carbon.utility.versioncontrol.VersionControlArtifact;


/**
 * @scr.component name="org.wso2.carbon.utility.svn"
 * immediate="true"
 */
public class ServiceComponent {
	private ServiceRegistration registration;

	private static RepositoryAdminService adminService;
	private static BundleContext bundleContext;
	private static final Log LOGGER = LogFactory.getLog(ServiceComponent.class);

	protected void activate(ComponentContext context) {
		LOGGER.info("Version Control Service: SVN bundle is activated");
		adminService = new RepositoryAdminService();
		bundleContext = context.getBundleContext();
		registration = context.getBundleContext().registerService(
		  VersionControlArtifact.class.getName(), adminService, null);
	}

	protected void deactivate(ComponentContext context) {
		LOGGER.info("Version Control Service: SVN bundle is deactivated");
		registration.unregister();
		adminService = null;
		bundleContext = null;
	}

}
