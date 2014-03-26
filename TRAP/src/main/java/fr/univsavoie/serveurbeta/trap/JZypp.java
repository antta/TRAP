package fr.univsavoie.serveurbeta.trap;

import java.util.ArrayList;

class JZypp{

    public JZypp(){

    }

    /**
     * Check if the given repo url is valide.
     * @param url to test
     * @param alias a needed name
     * @return true if the repo is valide false otherwise
     */
  native boolean isAValidRepository(String url, String alias);

    /**
     *
     * @param packageName
     * @return
     */
  native String SearchPackage(String packageName);
  static
  {
    System.load("/home/patrick-edouard/IdeaProjects/TRAP/TRAP/lib/libZyppImpl.so");
  }

}

