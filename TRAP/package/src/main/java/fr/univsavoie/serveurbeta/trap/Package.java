package fr.univsavoie.serveurbeta.trap;

/**
 * Class Package </br>
 *</br>
 * Is used as a storage class for packages containing :</br>
 * <ul>
 *     <li>The name</li>
 *     <li>The repository which provide the package</li>
 *     <li>A boolean if the package is installed</li>
 * </ul>
 */
public class Package {
    private String name;
    private String repository;
    private boolean isInstalled;

    public Package(String name, String repository, boolean isInstalled) {
        this.name = name.trim();
        this.repository = repository;
        this.isInstalled = isInstalled;
    }

    public String getName() {
        return name;
    }

    public String getRepository() {
        return repository;
    }

    public boolean isInstalled() {
        return isInstalled;
    }
}
