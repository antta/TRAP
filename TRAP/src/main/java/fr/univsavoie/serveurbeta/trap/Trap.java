package fr.univsavoie.serveurbeta.trap;

import java.util.ArrayList;

/**
 * Created by patrick-edouard on 3/20/14.
 */
public class Trap {
    private JZypp zypp;
    /**
     *  Path to a system root folder
     */
    private String sysRoot = "/";
    private static Trap instance;

    private Trap(){
        this.zypp = new JZypp();
    }

    public static Trap getInstance(){
        if(instance == null){
            instance = new Trap();
        }
        return instance;
    }

    public String listRepositoryPackages(String repoUrl, String alias){

        // ajoute le répo

        // Recherche avec l'alias

        return this.zypp.searchPackage(repoUrl);
    }

    public ArrayList<Package> searchPackage(String packageName){
        ArrayList<Package> packages = new ArrayList<Package>();
        String stringPackage = this.zypp.searchPackage(packageName);

        for(String s : stringPackage.split(";")){
            packages.add(new Package(s,"",false));
        }

        return packages;
    }

    public void setSysRoot(String sysRoot){
        this.sysRoot = sysRoot;
    }

    public boolean isAValidRepository(String repoURL){
        return this.zypp.isAValidRepository(repoURL, "useless");
    }

    public void addRepository(String repoURL){

    }



    public static void main(String[] args){
        Trap trap = getInstance();

        for(Package p : trap.searchPackage("kiwi")){
            System.out.println(p);
        }
    }
}
