package fr.univsavoie.serveurbeta.trap

class Main 
{
  public static void main(String[] args) 
  {
    JZypp zypp = new JZypp();
    String packageName = "kiwi";
    
    String wp = zypp.whatProvides(packageName);
	System.out.println("[Java]"+wp);
  }
}