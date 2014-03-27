package fr.univsavoie.serveurbeta.trap;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

class JZypp{

    public JZypp(){

        File lib = new File("TRAP/resources/libZyppImpl.so"); //local developpement

        System.out.println(lib);

        if(!lib.exists()){
            System.out.println("Chemin de dev innexistant\nmode jar activé");
            try{
                lib = new File(new URL("jar:file:\"TRAP-1.0-SNAPSHOT.jar!/libZyppImpl.so\"").toURI());
                System.out.println(lib.getAbsolutePath().toString());
            }catch(URISyntaxException e){
                e.printStackTrace();
            }catch(MalformedURLException e2){
                e2.printStackTrace();
            }

        }

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

