package fr.univsavoie.serveurbeta.trap;

/*
    Fonctions à implémenter :
    std::string getPackagesFromName(std::string name = "", std::string repoAlias = "");
	void setPathName(std::string pathName = "/");
	void addRepo(std::string repoAlias, std::string repoURL);
	bool checkRepo(std::string repoURL);
	void refreshRepo(std::string repoAlias);
 */
public class JZypp{

    /**
     * Check if the given repo url is valid.
     * @param url to test
     * @return true if the repo is valid false otherwise
     */
    native boolean isAValidRepository(String url);

    /**
     *
     * @param packageName
     * @return
     */
    native String getPackage(String sysRoot, String packageName);

    /**
     * Add the given repository to the current repo.d
     *
     * Need root privilège if the sysroot is your distribution's root
     *
     * @param url of a repository
     * @param alias name for the repo
     */
    native void addReposiory(String sysRoot, String url, String alias);

    /**
     *
     * @param packageName
     * @param repoName
     * @return
     */
    native String getPackagesFromName(String sysRoot, String packageName, String repoName);

    /**
     *
     * @param repoName
     * @return
     */
    native String getPackagesFromRepo(String sysRoot, String repoName);

    /**
     *
     * @param repoName
     */
    native void refreshRepo(String sysRoot, String repoName);

    /**
     *
     * @param pathName
     */
    native void setPathName(String pathName);
}

