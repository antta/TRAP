package fr.univsavoie.serveurbeta.trap;

class JZypp{

    public JZypp(){


        //Runtime.getRuntime().load("fr.univsavoie.serveurbeta.trap.resources/libZyppImpl.so");
        System.out.println(getClass().getResource("./resources/libZyppImpl.so").toString().substring(("file:").length()));
        System.load(getClass().getResource("./resources/libZyppImpl.so").toString().substring(("file:").length()));
        //System.load("/home/patrick-edouard/IdeaProjects/TRAP/TRAP/lib/libZyppImpl.so");

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
  /*static
  {
      System.out.println(ClassLoader.getResource("libZyppImpl.so"));
    System.load("/home/patrick-edouard/IdeaProjects/TRAP/TRAP/lib/libZyppImpl.so");
  }*/

}

