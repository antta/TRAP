package fr.univsavoie.serveurbeta.trap;

import java.io.File;

class JZypp{

    public JZypp(){

        File lib = new File("TRAP/resources/libZyppImpl.so"); //local developpement

        System.out.println(lib);
        if(!lib.exists())
            lib = new File("TRAP-1.0-SNAPSHOT.jar"+"/libZyppImpl.so"); //For jar
        //Convert the relative path to absolute
        System.load((lib.getAbsolutePath()));

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
  native String searchPackage(String packageName);

  /*static
  {
      System.out.println(ClassLoader.getResource("libZyppImpl.so"));
    System.load("/home/patrick-edouard/IdeaProjects/TRAP/TRAP/lib/libZyppImpl.so");
  }*/

}

