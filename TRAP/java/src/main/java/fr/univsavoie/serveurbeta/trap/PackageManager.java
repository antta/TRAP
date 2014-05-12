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
     * Search a package in all repositories
     *
     * @param sysRoot Path to the "virtual" file system root : where will be put the information of the repository without changing the system repositories
     * @param packageName Name of the package to search
     * @return A string containing all package separated with comas
     */
    abstract String getPackage(String sysRoot, String packageName);

    /**
     * Add the given repository to the current repo.d
     *
     * Need root privilege if the sysroot is your distribution's root
     *
     * @param sysRoot Path to the "virtual" file system root : where will be put the information of the repository without changing the system repositories
     * @param url of a repository
     * @param alias name for the repo
     */
    abstract void addRepository(String sysRoot, String url, String alias);

    /**
     * Add the given repository to the current repo.d
     *
     * Need root privilege if the sysroot is your distribution's root
     *
     * @param sysRoot Path to the "virtual" file system root : where will be put the information of the repository without changing the system repositories
     * @param url of a repository
     * @param urlGPG Url used for the GpG check
     * @param alias name for the repo

     */
    abstract void addRepository(String sysRoot, String url, String urlGPG, String alias);

    /**
     * Search a package in the given repo
     *
     * @param sysRoot Path to the "virtual" file system root : where will be put the information of the repository without changing the system repositories
     * @param packageName package to search
     * @param repoName name of the repo where you want to search
     * @return A string containing all package separated with comas
     */
    abstract String getPackagesFromName(String sysRoot, String packageName, String repoName);

    /**
     *  Get all packages from a given repository
     *
     * @param sysRoot Path to the "virtual" file system root : where will be put the information of the repository without changing the system repositories
     * @param repoName name of the repository you want to get packages
     * @return A string containing all package separated with comas
     */
    abstract String getPackagesFromRepo(String sysRoot, String repoName);

    /**
     * Refresh the metadata of the given repository
     *
     * @param sysRoot Path to the "virtual" file system root : where will be put the information of the repository without changing the system repositories
     * @param repoName Name of the repository you want to get package you want to refresh
     */
    abstract void refreshRepo(String sysRoot, String repoName);

    /**
     *  Depending on the implementation may be used to create all folders needed for the other functions
     *
     *  /!\ cannot be used to set a common path Sysroot for all other functions
     *
     * @param pathName Path to the "virtual" file system root : where will be put the information of the repository without changing the system repositories
     */
    abstract void setPathName(String pathName);

    /**
     *
     * Check if there is a local repository with given name
     *
     * search a file with name {alia}.repo in the repo.d repository of the sysroot
     *
     * @param sysRoot Path to the "virtual" file system root : where will be put the information of the repository without changing the system repositories
     * @param alias Name of the repository
     * @return True if the local repository exists false
     */
    abstract boolean localRepositoryExists(String sysRoot, String alias);

    /**
     *
     * Check if there is a local repository with given name and given url
     *
     * search a file with name {alia}.repo in the repo.d repository of the sysroot with the same url in the file
     *
     * @param sysRoot Path to the "virtual" file system root : where will be put the information of the repository without changing the system repositories
     * @param alias Name of the repository
     * @param url Url of the repository
     * @return True if the repository exists and the two parameters are the same in the file
     */
    abstract boolean localRepositoryExists(String sysRoot, String alias, String url);
}
