package fr.univsavoie.serveurbeta.trap;

/**
 * Created by patrick-edouard on 4/23/14.
 */
public abstract class PackageManager {

    /**
     * Check if the given repo url is valid.
     * @param url to test
     * @return true if the repo is valid false otherwise
     */
    abstract boolean isAValidRepository(String url);

    /**
     *
     * @param packageName
     * @return
     */
    abstract String getPackage(String sysRoot, String packageName);

    /**
     * Add the given repository to the current repo.d
     *
     * Need root privilège if the sysroot is your distribution's root
     *
     * @param url of a repository
     * @param alias name for the repo
     */
    abstract void addRepository(String sysRoot, String url, String alias);

    /**
     * Add the given repository to the current repo.d
     *
     * Need root privilège if the sysroot is your distribution's root
     *
     * @param url of a repository
     * @param alias name for the repo
     */
    abstract void addRepository(String sysRoot, String url, String urlGPG, String alias);

    /**
     *
     * @param packageName
     * @param repoName
     * @return
     */
    abstract String getPackagesFromName(String sysRoot, String packageName, String repoName);

    /**
     *
     * @param repoName
     * @param
     * @param
     * @return
     */
    abstract String getPackagesFromRepo(String sysRoot, String repoName);

    /**
     *
     * @param repoName
     */
    abstract void refreshRepo(String sysRoot, String repoName);

    /**
     *
     * @param pathName
     */
    abstract void setPathName(String pathName);

    abstract boolean localRepositoryExists(String sysRoot, String alias);

    abstract boolean hasRepositoryFor(String sysRoot, String url);
}
