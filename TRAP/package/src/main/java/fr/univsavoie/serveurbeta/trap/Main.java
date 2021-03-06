package fr.univsavoie.serveurbeta.trap;

import java.util.ArrayList;

class Main {
	public static void main(String[] args) {
		Trap trap = new Trap("/");

		String repoCorrecte = "http://download.opensuse.org/repositories/home:/henri_gomez:/devops-incubator/openSUSE_13.1/";
		String gpgGomez = "http://download.opensuse.org/repositories/home:/henri_gomez:/devops-incubator/openSUSE_13.1/repodata/repomd.xml.key";

        System.out.println("L'url " + repoCorrecte + " est "
				+ trap.isAValidRepository(repoCorrecte));

        String packageName = "kiwi";

		ArrayList<Package> listPackage = trap.searchPackage(packageName);

		System.out.println("[Java]" + listPackage);
	}
}
