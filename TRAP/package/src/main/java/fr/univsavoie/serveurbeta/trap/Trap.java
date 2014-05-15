package fr.univsavoie.serveurbeta.trap;

import org.jdom2.JDOMException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * TRAP Research Any Package is used to search on distant repository a list of the existing package and search in the list with a simple API
 * It use the same folders as Zypper the Suse package manager (and can use the same library than zypper).
 *
 * In   etc/zypper/repo.d/      Is stored a list of .repo files that store the repositories information
 * In   var/cache/zypp/raw/    Is stored the metadata of each repository, depending of the package manager used to trap, it can be .xml.gz files or just .txt files
 *
 *      By default, it use the root folder "/" but this should be changed to not interfere with the system package manager.
 *
 * Example of use of TRAP :
 *
 * Trap trap = new Trap("/home/user/virtualRoot");
 * if (trap.isAValidRepository("http://url_to_my_repo/opensuse12_3/")) {
 *     trap.addRepository(myrepo,"http://url_to_my_repo/opensuse12_3/");
 *     trap.refresh(myrepo);
 *     system.out.println(trap.getPackagesIn(myrepo))
 * }
 *
 * Because all information concerning packages and repositories are stored on the file system, if the path is a same during two different executions the repositories and packages will be saved.
 */
public class Trap {
	
	private static final String LIB_NAME = "trap-native-1.2-SNAPSHOT.so";
	
	private PackageManager zypp;

	/**
	 * Path to a system root folder, can be a virtual root folder
	 */
	private String sysRoot = "/";

    /**
     * New instance for Trap.
     * @param sysRoot refer to your snapshot system. example : ~/myVM/snapshot-1-0-2/root witch contain a etc/zypp/repos.d repository
     */
	public Trap(String sysRoot) {
        this.zypp = new PackageManagerImplementation();

		/*InputStream resource = this.getClass().getClassLoader().getResourceAsStream(LIB_NAME);
		
		File tempFile;
		try {
			tempFile = File.createTempFile("lib", "so");
			tempFile.deleteOnExit();
	        FileOutputStream out = new FileOutputStream(tempFile);
	        IOUtils.copy(resource, out);
			System.load(tempFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}*/

        this.sysRoot = new File(sysRoot).getAbsolutePath();
        this.initSystem();
	}

    /**
     * New instance for Trap.
     * Due to cpp issues when using the native Pckagemanager, it is highly recommended to use only one instance of Trap in your whole program or set native to false
     * @param sysRoot refer to your snapshot system sample : ~/myVM/snapshot-1-0-2/root witch contain a etc/zypp/repos.d repository
     * @param packageManager Change the Packagemanager used (between native with libzypp or Java)
     */
    public Trap(String sysRoot, PackageManager packageManager) {

        this.zypp = packageManager;

        this.sysRoot = new File(sysRoot).getAbsolutePath();
        this.initSystem();
    }

    private void initSystem(){
        this.zypp.setPathName(this.sysRoot);
    }

    /**
     *
     * Search packages from name in all repositories and return a package list
     *
     * @param packageName Package to search
     * @return List of the packages.
     */
	public String searchPackage(String packageName) {
		return this.zypp.getPackage(packageName, this.sysRoot);
	}

    /**
     *
     * Check if the given URL is a valid repository
     *
     * @param repoURL URL to check
     * @return true if the URL is a repository
     */
    public boolean isAValidRepository(String repoURL) {
        return zypp.isAValidRepository(repoURL);
    }

    /**
     * Get the metadata of the repository.
     * If no refresh is done at least once, no package can be searched.
     *
     * Note that refreshing a repository is an important thing to do before getting the package, it may take a long time but if there is no change on the distant repository, the operation take less than a second
     *
     * @param repoAlias Name of the repository to refresh
     */
    public void refreshRepo(String repoAlias) throws IOException, JDOMException {
        this.zypp.refreshRepo(sysRoot, repoAlias);
    }

    /**
     *
     * Add a repository on the file system in {this.sysroot}/etc/zypp/repo.d/{repoAlias}.repo
     *
     * @param repoAlias Name of the repository to add
     * @param repoURL Url of the repository to add
     */
    public void addRepository(String repoAlias, String repoURL){
        this.zypp.addRepository(this.sysRoot, repoURL, repoAlias);
    }

    /**
     *
     * Not totaly implemented in all of the package Managers
     *
     * Add a repository on the file system in {this.sysroot}/etc/zypp/repo.d/{repoAlias}.repo
     *
     * @param repoAlias Name of the repository to add
     * @param repoURL Url of the repository to add
     * @param gpgURL Url of the file used for the Gdg check
     */
    public void addRepository(String repoAlias, String repoURL, String gpgURL){
        this.zypp.addRepository(this.sysRoot, repoURL, gpgURL, repoAlias);
    }

    /**
     *
     * Search packages in the given repository
     *
     * @param packageName Name of the package to search
     * @param repoAlias Name of the repository to search into
     * @return list of the package found with comma separated values
     */
    public String getPackagesFromName(String packageName, String repoAlias){
        return this.zypp.getPackagesFromName(this.sysRoot, packageName, repoAlias);
    }

    /**
     *
     * Get all package in the given repository
     *
     * @param repoAlias Name of the repository we want to get the packages
     * @return list of the package found with comma separated values
     */
    public String getPackagesIn(String repoAlias){
        return this.zypp.getPackagesFromRepo(this.sysRoot, repoAlias);
    }

    /**
     * Check if {sysRoot}/etc/zypp/repo.d/{alias}.repo exists
     *
     * @param alias Repository to check
     * @return true if the file exists, false otherwise
     */
    public boolean localRepositoryExists(String alias){
        return this.zypp.localRepositoryExists(this.sysRoot, alias);
    }

    /**
     * Check if a repository is referenced in local (if {sysRoot}/etc/zypp/repo.d/{alias}.repo exists ) with the given url
     *
     * @param alias Repository to check
     * @param url Url to check
     * @return true if the file exists with the same URL false if the file does not exists, or if there is a different url in the file
     */
    public boolean localRepositoryExists(String alias, String url){
        return this.zypp.localRepositoryExists(this.sysRoot, alias ,url);
    }
}
