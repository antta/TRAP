package fr.univsavoie.serveurbeta.trap;

import java.io.File;

class JZypp{

    public JZypp(){

        File f = new File(System.getProperty("java.class.path"));
        File dir = f.getAbsoluteFile().getParentFile();

        File lib = new File(dir.toString()+"/libZyppImpl.so"); //For jar
        if(!lib.exists())
            lib = new File("TRAP/resources/libZyppImpl.so"); //local developpement
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

