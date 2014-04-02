package fr.univsavoie.serveurbeta.trap;

/*
    Fonctions à implémenter :
    std::string getPackagesFromName(std::string name = "", std::string repoAlias = "");
	std::string lastQueryResult();
	void setPathName(std::string pathName = "/");
	void addRepo(std::string repoAlias, std::string repoURL);
	bool checkRepo(std::string repoURL);
	void refreshRepo(std::string repoAlias);
 */
class JZypp{

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
    native String getPackage(String packageName);

    /**
     * Add the given repository to the current repo.d
     *
     * Need root privilège if the sysroot is your distribution's root
     *
     * @param url of a repository
     * @param alias name for the repo
     */
    native void addReposiory(String url, String alias);

    /**
     *
     * @param packageName
     * @param repoName
     * @return
     */
    native String getPackagesFromName(String packageName, String repoName);

    /**
     *
     * @param repoName
     * @return
     */
    native String getPackagesFromRepo(String repoName);

    /**
     *
     * @param repoName
     */
    native void refreshRepo(String repoName);

    /**
     *
     * @param pathName
     */
    native void setPathName(String pathName);
}

