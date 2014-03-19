package fr.univsavoie.serveurbeta.trap;

import java.util.ArrayList;

class JZypp
{
    public JZypp(){

    }

    /**
     * Check if the given repo url is valide.
     * @param url to test
     * @param alias a needed name
     * @return true if the repo is valide false otherwise
     */
    public boolean isAValidRepository(String url, String alias){
        // not implemented yet
        return false;
    }

    public ArrayList<Package> searchPackage(String packageName){
        ArrayList<Package> packages = new ArrayList<Package>();
        return null;
    }

    /**
     *
     * @param packageName
     * @return
     */
  public native String whatProvides(String packageName);
  static
  {
    System.loadLibrary("ZyppImpl");
  }
}

