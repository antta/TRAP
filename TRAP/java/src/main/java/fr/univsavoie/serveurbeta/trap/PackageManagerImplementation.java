package fr.univsavoie.serveurbeta.trap;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import java.io.*;
import java.net.HttpURLConnection;
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

        String home = (System.getProperty("user.home"));

        packageManager.setPathName(home+"/testTRAP/");

        /*
        String repoGomez = "http://download.opensuse.org/repositories/home:/henri_gomez:/devops-incubator/openSUSE_13.1/";
        packageManager.addRepository(home+"/testTRAP/",repoGomez,"RepoGomez");
        */

        String repoOfficielDeTousLesInternets = "http://download.opensuse.org/distribution/13.1/repo/oss/suse/";
        System.out.println(packageManager.isAValidRepository(repoOfficielDeTousLesInternets));
        packageManager.addRepository(home+"/testTRAP/",repoOfficielDeTousLesInternets,"offiSuse");
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

            for(Element e : root.getChildren("package",ns)){
                if(!e.getChild("arch",ns).getValue().equals("src"))
                    packages.add(e.getChild("name",ns));
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

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }
    }

    @Override
    boolean isAValidRepository(String url) {
        //return new JZypp().isAValidRepository(url);

        try {
            HttpURLConnection httpUrlC =  ( HttpURLConnection ) new URL(url+"/repodata/repomd.xml").openConnection();
            httpUrlC.setInstanceFollowRedirects(false);
            httpUrlC.setRequestMethod("HEAD");
            return (httpUrlC.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            return false;
        }
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

        File repoFile = new File(sysRoot+"/etc/zypp/repo.d/"+"/"+alias+".repo");

        try {
            FileWriter writer = new FileWriter(repoFile);

            writer.write("["+alias+"]\nname="+alias+"\nenabled=1\nautorefresh=1\nbaseurl="+url+"\ntype=rpm-md");

            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


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
       return null;//new File(sysRoot+"/var/cache/zypp/raw/"+repoName+"/packages.txt").;
    }

    @Override
    void refreshRepo(String sysRoot, String repoName) {
        //File repoFile = new File(sysRoot+"/etc/zypper/repo.d/"+"/"+alias+".repo");
        File metadataFile = new File(sysRoot+"/var/cache/zypp/raw/"+repoName+"/packages.txt");

        try {
            FileWriter writer = new FileWriter(metadataFile);

            for (Element e : packages)
            {
                writer.write(e.getValue()+";");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    void setPathName(String pathName) {
        File root = new File(pathName);
        File repod = new File(root.getAbsolutePath()+"/etc/zypp/repo.d/");
        File metadataDirectory = new File(root.getAbsolutePath()+"/var/cache/zypp/raw/");
        repod.mkdirs();
        metadataDirectory.mkdir();
    }
}
