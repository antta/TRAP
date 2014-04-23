package fr.univsavoie.serveurbeta.trap;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

/**
 * Created by patrick-edouard on 4/23/14.
 */
public class PackageManagerImplementation extends PackageManager{

    private ArrayList<Element> packages;
    private Element root;
    public PackageManagerImplementation(){

    }

    public static void main(String[] args){

        PackageManager packageManager = new PackageManagerImplementation();

        /*
        String repoGomez = "http://download.opensuse.org/repositories/home:/henri_gomez:/devops-incubator/openSUSE_13.1/";
        packageManager.addRepository("/",repoGomez,"RepoGomez");
        */
        String repoOfficielDeTousLesInternets = "http://download.opensuse.org/distribution/13.1/repo/oss/suse/";
        packageManager.addRepository("/",repoOfficielDeTousLesInternets,"offiSuse");
    }

    private void retrieveMetaData(String url){


        SAXBuilder sxb = new SAXBuilder();
        String urlRepomd = url+"repodata/repomd.xml";

        try {
            Element rootRepomd = sxb.build(new URL(urlRepomd).openStream()).getRootElement();
            System.out.println(rootRepomd.getNamespace());
            Namespace ns = rootRepomd.getNamespace();

            for(Element e : rootRepomd.getChildren("data", ns)){
                if(e.getAttribute("type").getValue().equals("primary")){
                    this.downloadMetaData(url + e.getChild("location", ns).getAttribute("href").getValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }
    }

    private void downloadMetaData(String url){
        System.out.println("On veut dl tout les internets pour : "+url);
        SAXBuilder sxb = new SAXBuilder();
        try {
            this.root = sxb.build(new GZIPInputStream(new URL(url).openStream())).getRootElement();
            Namespace ns = this.root.getNamespace();

            Element packages =  this.root.getChild("package",ns);

            for(Element e : root.getChildren("package",ns)){
                if(!e.getChild("arch",ns).getValue().equals("src"))
                System.out.println(e.getChild("name",ns).getValue());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }
    }

    @Override
    boolean isAValidRepository(String url) {
        return new JZypp().isAValidRepository(url);
    }

    @Override
    String getPackage(String sysRoot, String packageName) {
        try{
            throw new UnimplemenedMethodeException();
        }catch(UnimplemenedMethodeException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    void addRepository(String sysRoot, String url, String alias) {
        this.retrieveMetaData(url);
    }

    @Override
    void addRepository(String sysRoot, String url, String urlGPG, String alias) {

    }

    @Override
    String getPackagesFromName(String sysRoot, String packageName, String repoName) {
        return null;
    }

    @Override
    String getPackagesFromRepo(String sysRoot, String repoName) {
        return null;
    }

    @Override
    void refreshRepo(String sysRoot, String repoName) {

    }

    @Override
    void setPathName(String pathName) {

    }
}
