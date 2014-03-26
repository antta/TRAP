package fr.univsavoie.serveurbeta.trap;

import java.util.ArrayList;

class Main
{
  public static void main(String[] args) 
  {
    Trap trap = Trap.getInstance();
    String packageName = "kiwi";
    
    /*ArrayList<Package> listPackage = trap.searchPackage(packageName);
	System.out.println("[Java]"+listPackage);

/*
      String repoIncorrecte = "http://mauvaiseurlderepo.com/repository";
      System.out.println("L'url "+repoIncorrecte+" est "+trap.isAValidRepository(repoIncorrecte));
*/

      String repoCorrecte = "http://download.opensuse.org/repositories/home:/henri_gomez:/devops-incubator/openSUSE_13.1/";
      System.out.println("L'url "+repoCorrecte+" est "+trap.isAValidRepository(repoCorrecte));
  }
}
