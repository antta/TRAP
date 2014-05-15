package fr.univsavoie.serveurbeta.trap;

/*
    Fonctions à implémenter :
    std::string getPackagesFromName(std::string name = "", std::string repoAlias = "");
	void setPathName(std::string pathName = "/");
	void addRepo(std::string repoAlias, std::string repoURL);
	bool checkRepo(std::string repoURL);
	void refreshRepo(std::string repoAlias);
 */

/**
 * Native implementation of the PackageManager usig libzypp and C++.<br/>
 * <br/>
 * All methods are not implemented and some does not work at all, we have give up on the native part because it was making Jenkins Crash while using the library in the plugin.
 */
public class JZypp extends PackageManager{

    @Override
    native boolean isAValidRepository(String url);

    @Override
    native String getPackage(String sysRoot, String packageName);

    @Override
    native void addRepository(String sysRoot, String url, String alias);

    @Override
    native void addRepository(String sysRoot, String url, String urlGPG, String alias);

    @Override
    native String getPackagesFromName(String sysRoot, String packageName, String repoName);

    @Override
    native String getPackagesFromRepo(String sysRoot, String repoName);

    @Override
    native void refreshRepo(String sysRoot, String repoName);

    @Override
    native void setPathName(String pathName);

    /**
     * Not implemented in native yet
     *
     * @param sysRoot Path to the "virtual" file system root : where will be put the information of the repository without changing the system repositories
     * @param alias Name of the repository
     * @return True if the local repository exists false
     */
    @Override
    boolean localRepositoryExists(String sysRoot, String alias) {
        return false;
    }

    /**
     * Not implemented in native yet
     *
     * @param sysRoot Path to the "virtual" file system root : where will be put the information of the repository without changing the system repositories
     * @param alias Name of the repository
     * @param url Url of the repository
     * @return True if the repository exists and the two parameters are the same in the file
     */
    @Override
    boolean localRepositoryExists(String sysRoot, String alias, String url) {
        return false;
    }
}

