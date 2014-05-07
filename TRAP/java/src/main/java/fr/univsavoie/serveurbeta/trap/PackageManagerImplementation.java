package fr.univsavoie.serveurbeta.trap;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

/**
 * Created by patrick-edouard on 4/23/14.
 */
public class PackageManagerImplementation extends PackageManager{

    private ArrayList<Element> packages;
    private String revision;
    private Element root;
    private Namespace ns;
    private String urlPackageMetaData;

    public PackageManagerImplementation(){
        packages=new ArrayList<Element>();
    }

    public static void main(String[] args) throws IOException, JDOMException {

        PackageManager packageManager = new PackageManagerImplementation();

        String unPaquet = "kiwi";
        String home = (System.getProperty("user.home"));

        packageManager.setPathName(home+"/testTRAP/");

        /*
        String repoGomez = "http://download.opensuse.org/repositories/home:/henri_gomez:/devops-incubator/openSUSE_13.1/";
        packageManager.addRepository(home+"/testTRAP/",repoGomez,"RepoGomez");
        */

        String repoOfficielDeTousLesInternets = "http://download.opensuse.org/distribution/13.1/repo/oss/suse/";
        System.out.println("[TEST]Is a valid repository ? "+packageManager.isAValidRepository(repoOfficielDeTousLesInternets + "yolo"));

        System.out.println("[TEST]Is a valid repository ? "+packageManager.isAValidRepository(repoOfficielDeTousLesInternets));
        packageManager.addRepository(home + "/testTRAP/", repoOfficielDeTousLesInternets, "offiSuse");
        packageManager.refreshRepo(home + "/testTRAP/", "offiSuse");
        System.out.println("Recherche de tout les packets : "+packageManager.getPackagesFromRepo(home+"/testTRAP/","offiSuse"));
        System.out.println("Recherche de "+unPaquet+" : "+packageManager.getPackagesFromName(home+"/testTRAP/", unPaquet, "offiSuse"));
    }

    private void retrieveMetaData(String url) throws IOException, JDOMException {

        SAXBuilder sxb = new SAXBuilder();
        String urlRepomd = url+"repodata/repomd.xml";

        //try {
            Element rootRepomd = sxb.build(new URL(urlRepomd).openStream()).getRootElement();
            ns = rootRepomd.getNamespace();

            Element t = rootRepomd.getChild("revision",ns);

            if(t!=null){
                this.revision = rootRepomd.getChild("revision",ns).getValue();
            }else{
                this.revision="undefined";
            }

            for(Element e : rootRepomd.getChildren("data", ns)){
                if(e.getAttribute("type").getValue().equals("primary")){
                    urlPackageMetaData = url + e.getChild("location", ns).getAttribute("href").getValue();
                    break;
                }
            }

        /*} catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }*/
    }

    private void getAllPackage() {
        for(Element e : root.getChildren("package",ns)){
            if(!e.getChild("arch",ns).getValue().equals("src")) {
                packages.add(e.getChild("name", ns));
            }
        }
    }

    private void downloadMetaData(String url){
        SAXBuilder sxb = new SAXBuilder();
        try {
            this.root = sxb.build(new GZIPInputStream(new URL(url).openStream())).getRootElement();
            ns = this.root.getNamespace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }
    }

    @Override
    boolean isAValidRepository(String url) {

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
            throw new UnimplementedMethodException();
        }catch(UnimplementedMethodException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    void addRepository(String sysRoot, String url, String alias) {

        File repoFile = new File(sysRoot+"/etc/zypp/repo.d/"+alias+".repo");

        try {
            FileWriter writer = new FileWriter(repoFile);

            writer.write("[" + alias + "]\nname=" + alias + "\nenabled=1\nautorefresh=1\nbaseurl=" + url + "\ntype=rpm-md");

            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    void addRepository(String sysRoot, String url, String urlGPG, String alias) {
        this.addRepository(sysRoot, url, alias);
    }

    @Override
    String getPackagesFromName(String sysRoot, String packageName, String repoName) {
        String allPackages = this.getPackagesFromRepo(sysRoot, repoName);
        String correspondingPackage = "";

        for(String p : allPackages.split(",")){
            if(p.contains(packageName)){
                correspondingPackage+=p+",";
            }
        }
        correspondingPackage = correspondingPackage.substring(0,correspondingPackage.length() -1);
        return correspondingPackage;
    }

    @Override
    String getPackagesFromRepo(String sysRoot, String repoName) {
        String pathname = sysRoot+"/var/cache/zypp/raw/"+repoName+"/packages.txt";
        File repoFile = new File(pathname);

        if(!repoFile.exists()){
            System.err.println("[ERROR]The specified repo file doesn't exists : "+pathname);
            return "";
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(repoFile));

            reader.readLine();
            return reader.readLine();

        } catch (FileNotFoundException e) {
            System.err.println("[ERROR]The specified repo file doesn't exists : "+pathname);
            return "";
        } catch (IOException e) {
            System.err.println("[ERROR]Cannot open the specified file : "+pathname);
            return "";
        }
    }

    @Override
    void refreshRepo(String sysRoot, String repoName) throws IOException, JDOMException {
        String pathname = sysRoot + "/etc/zypp/repo.d/" + "/" + repoName + ".repo";
        File repoFile = new File(pathname);
        String url = "";
        String repoRevision = "";

        if(!repoFile.exists()){
            System.err.println("[ERROR]The specified repo file doesn't exists : "+ pathname);
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(repoFile));

            String line = reader.readLine();

            while(line!=null){
                if(line.startsWith("baseurl")){
                    url = line.substring("baseurl=".length());
                }

                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File metadataFile = new File(sysRoot+"/var/cache/zypp/raw/"+repoName+"/packages.txt");

        if(!metadataFile.exists()){
            try {
                File metadataDirectory = new File(sysRoot+"/var/cache/zypp/raw/"+repoName);
                if(!metadataDirectory.mkdir()){
                    System.err.println("[ERROR]Couldn't save cache at "+metadataDirectory);
                }

                if(!metadataFile.createNewFile()){
                    System.err.println("[ERROR]Couldn't save cache at "+metadataFile);
                }
                repoRevision = "undefined";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(metadataFile));

                repoRevision = reader.readLine();

                if(repoRevision==null){
                    repoRevision="undefined";
                }
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.retrieveMetaData(url);

        if(!repoRevision.equals(revision)){

            this.downloadMetaData(this.urlPackageMetaData);

            this.getAllPackage();

            this.storeRefreshedData(metadataFile);
        }
    }

    private void storeRefreshedData(File metadaFile){
        try {
            FileWriter writer = new FileWriter(metadaFile);
            writer.write(revision + "\n");
            for (int i = 0; i < packages.size(); i ++)
            {
                writer.write(packages.get(i).getValue());
                if(i < packages.size()-1)
                    writer.write(",");
            }
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    void setPathName(String pathName) {
        File root = new File(pathName);
        File repod = new File(root+"/etc/zypp/repo.d/");
        File metadataDirectory = new File(root.getAbsolutePath()+"/var/cache/zypp/raw/");
        if(!repod.mkdirs()){
            System.err.println("[ERROR]Couldn't save repod at "+repod);
        }
        if(!metadataDirectory.mkdir()){
            System.err.println("[ERROR]Couldn't save cache at "+metadataDirectory);
        }
    }

    @Override
    boolean localRepositoryExists(String sysRoot, String alias) {
        return new File(sysRoot+"/etc/zypp/repo.d/"+alias+".repo").exists();
    }

    @Override
    boolean hasRepositoryFor(String sysRoot, String url) {
        File root = new File(sysRoot);
        File repod = new File(root.getAbsolutePath()+"/etc/zypp/repo.d/");
        File[] repoFiles = repod.listFiles();
        if(repoFiles==null){
            return false;
        }
        for(File repoFile: repoFiles){

            try {
                BufferedReader reader = new BufferedReader(new FileReader(repoFile));

                String line = reader.readLine();

                while(line!=null){
                    if(line.startsWith("baseurl")){
                        if(url.equals(line.substring("baseurl=".length()))){
                            return true;
                        }
                    }
                    line = reader.readLine();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
