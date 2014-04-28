package fr.univsavoie.serveurbeta.trap;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

//import fr.univsavoie.serveurbeta.trap.JZypp;

/**
 * Created by patrick-edouard on 3/20/14.
 */
public class Trap {
	
	private static final String LIB_NAME = "trap-native-1.1-SNAPSHOT.so";
	
	private PackageManager zypp;

	/**
	 * Path to a system root folder
	 */
	private String sysRoot = "/";

    /**
     * New instance for Trap.
     * Due to cpp issues, it is highly recommended to use only one instance of Trap in your whole program.
     * @param sysRoot refer to your snapshot system sample : ~/myVM/snapshot-1-0-2/root witch contain a etc/zypp/repos.d repository
     */
	public Trap(String sysRoot) {
		//this.zypp = new JZypp();
        this.zypp = new PackageManagerImplementation();

		InputStream resource = this.getClass().getClassLoader().getResourceAsStream(LIB_NAME);
		
		File tempFile;
		try {
			tempFile = File.createTempFile("lib", "so");
			tempFile.deleteOnExit();
	        FileOutputStream out = new FileOutputStream(tempFile);
	        IOUtils.copy(resource, out);
			System.load(tempFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}

        this.sysRoot = sysRoot;
        this.initSystem();
	}

    /**
     * New instance for Trap.
     * Due to cpp issues, it is highly recommended to use only one instance of Trap in your whole program.
     * @param sysRoot refer to your snapshot system sample : ~/myVM/snapshot-1-0-2/root witch contain a etc/zypp/repos.d repository
     * @param nativeMode switch between JZypp (witch call native cpp files) and our own java way (witch download the xml metada for the repos)
     */
    public Trap(String sysRoot, boolean nativeMode) {
        if(nativeMode){
            this.zypp = new JZypp();
            File tempFile;

            try {
                InputStream resource = this.getClass().getClassLoader().getResourceAsStream(LIB_NAME);
                tempFile = File.createTempFile("lib", "so");
                tempFile.deleteOnExit();
                FileOutputStream out = new FileOutputStream(tempFile);
                IOUtils.copy(resource, out);
                System.load(tempFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else{
            this.zypp = new PackageManagerImplementation();
        }

        this.sysRoot = sysRoot;
        this.initSystem();
    }

    /**
     *
     */
    private void initSystem(){
        this.zypp.setPathName(this.sysRoot);
    }

    /**
     *
     * @param packageName
     * @return
     */
	public ArrayList<Package> searchPackage(String packageName) {
		ArrayList<Package> packages = new ArrayList<Package>();
		String stringPackage = this.zypp.getPackage(packageName, this.sysRoot);

		for (String s : stringPackage.split(";")) {
			packages.add(new Package(s, "", false));
		}

		return packages;
	}

    /**
     *
     * @param repoURL
     * @return
     */
    public boolean isAValidRepository(String repoURL) {
        return zypp.isAValidRepository(repoURL);
    }

    /**
     *
     * @param repoAlias
     */
    public void refreshRepo(String repoAlias){
        this.zypp.refreshRepo(sysRoot, repoAlias);
    }

    /**
     *
     * @param repoAlias
     * @param repoURL
     */
    public void addRepository(String repoAlias, String repoURL){
        this.zypp.addRepository(this.sysRoot, repoURL, repoAlias);
    }

    /**
     *
     * @param repoAlias
     * @param repoURL
     */
    public void addRepository(String repoAlias, String repoURL, String gpgURL){
        this.zypp.addRepository(this.sysRoot, repoURL, gpgURL, repoAlias);
    }

    /**
     *
     * @param packageName
     * @param repoAlias
     * @return
     */
    public String getPackagesFromName(String packageName, String repoAlias){
        return this.zypp.getPackagesFromName(this.sysRoot, packageName, repoAlias);
    }

    /**
     *
     * @param repoAlias
     * @return
     */
    public String getPackagesIn(String repoAlias){
        return this.zypp.getPackagesFromRepo(this.sysRoot, repoAlias);
    }
}
