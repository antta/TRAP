#include "trap.h"

int main(int argc, char **argv)
{
	std::cout << "Starting Tests for TRAP"<< std::endl;
	
	int testCount = 0, testPassed = 0;
	if(argc < 2)
	{
		std::cout << "Unit testing : you can set custom values for other tests :" << std::endl;
		std::cout << "./test <PackageToSearch> <URLOfRepo> <RepoAlias> <PathForLibZypp>" << std::endl;
	}
	else
	{
		std::cout << "Parametters for the test : <PackageToSearch: " << argv[1] << ">";
		if(argc > 2)
		{
			std::cout << " <URLOfRepo: " << argv[2] << ">";
			if(argc > 3)
			{
				std::cout << " <RepoAlias: " << argv[3] << ">";
				if(argc > 4)
					std::cout << " <PathForLibZypp: " << argv[4] << ">";
				else
					std::cout << " <PathForLibZypp: default>";
			}
			else
				std::cout << " <RepoAlias: default> <PathForLibZypp: default>";
		}
		else
			std::cout << " <URLOfRepo: default> <RepoAlias: default> <PathForLibZypp: default>";
		
		std::cout << std::endl;
	}
		
	std::cout << "***************************************************" << std::endl;
	Trap &trap = Trap::getInstance();
	if(argc > 4)
		trap.setPathName(argv[4]);
	else
	{
		trap.setPathName("zypperRoot/");
	}	
	std::cout << "[INFO] Trap instance get and path set" << std::endl;
	std::cout << "---------------------------------------------------" << std::endl;
	
	if(argc < 2)
	{
		std::cout << "[INFO] Cleaning efore starting" << std::endl;
		trap.clean();
	}	std::cout << "[TEST] Probe Repository" << std::endl;
	std::cout << "---------------------------------------------------" << std::endl;
	if(argc > 2)
	{
		if(trap.checkRepo(argv[2]))
			std::cout << "[INFO] URL " << argv[2] << " is a good Package Repository"  << std::endl;
		else
			std::cout << "[INFO] URL " << argv[2] << " is not Repository"  << std::endl;
	}
	else
	{
		testCount += 2;
		if(trap.checkRepo("http://download.opensuse.org/repositories/home:/henri_gomez:/devops-incubator/openSUSE_13.1/"))
		{
			std::cout << "[OK] URL http://download.opensuse.org/repositories/home:/henri_gomez:/devops-incubator/openSUSE_13.1/  is a good Package Repository"  << std::endl;
			testPassed ++;
		}
		else
			std::cout << " [ERROR] URL http://download.opensuse.org/repositories/home:/henri_gomez:/devops-incubator/openSUSE_13.1/ should be a repository"  << std::endl;
		
		if(trap.checkRepo("http://AnythingButNotARealRepo.fr/"))
			std::cout << " [ERROR] URL http://AnythingButNotARealRepo.fr/ should not be considered as a repository"  << std::endl;
		else
		{
			std::cout << "[OK] URL http://AnythingButNotARealRepo.fr/ is not a Package Repository"  << std::endl;
			testPassed ++;
		}
	}
	std::cout << "---------------------------------------------------" << std::endl;
	
	std::cout << "[TEST] Adding Repository" << std::endl;
	if(argc > 3)
	{
		trap.addRepo(argv[3],argv[2]);
		if(trap.isRepositoryExists(argv[3]))
			std::cout << "[INFO] Repository : "<< argv[3] << " Successfully added or updated"  << std::endl;
		else
			std::cout << "[INFO]Fail to add Repository : "<< argv[3] << std::endl;
	}
	else
	{
		testCount ++;
		trap.addRepo("hgomez","http://download.opensuse.org/repositories/home:/henri_gomez:/devops-incubator/openSUSE_13.1/","http://download.opensuse.org/repositories/home:/henri_gomez:/devops-incubator/openSUSE_13.1/repodata/repomd.xml.key");
		if(trap.isRepositoryExists("hgomez"))
		{
			std::cout << "[OK] Repository : hgomez successfully added or updated"  << std::endl;
			testPassed ++;
		}
		else
			std::cout << "[ERROR] Failed to add Repository : hgomez"  << std::endl;
	}
	std::cout << "---------------------------------------------------" << std::endl;
	
	std::cout << "[TEST] Refreshing Repository" << std::endl;
	if(argc > 3)
	{
		
		if(trap.refreshRepo(argv[3]))
			std::cout << "[INFO] Repository : "<< argv[3] << " Successfully refreshed"  << std::endl;
		else
			std::cout << "[INFO]Fail to refresh Repo : "<< argv[3] << std::endl;
	}
	else
	{
		testCount ++;
		if(trap.refreshRepo("hgomez"))
		{
			std::cout << "[OK] Repository : hgomez successfully refreshed"  << std::endl;
			testPassed ++;
		}
		else
			std::cout << "[ERROR] Failed to refreshed Repository : hgomez"  << std::endl;
	}
	std::cout << "---------------------------------------------------" << std::endl;
	
	std::cout << "[TEST] Search in all repositories" << std::endl;
	if(argc > 2)
	{
		std::cout << "[INFO] Searching for package with name : "<< argv[1] << " In all repositories. Result : " << trap.getPackagesFromName(argv[1]) << std::endl;
	}
	else
	{
		testCount ++;
		std::string packages = trap.getPackagesFromName("myjenkins");
		if(packages == "myjenkins,myjenkins-lts")
		{
			std::cout << "[OK] Packages successfully found : " << packages <<  std::endl;
			testPassed ++;
		}
		else
			std::cout << "[ERROR] Packages not found : Result : " << packages << "Shloud be : myjenkins,myjenkins-lts" << std::endl;
	}
	std::cout << "---------------------------------------------------" << std::endl;
	
	std::cout << "[TEST] Search in the specified repository" << std::endl;
	if(argc > 4)
	{
		std::cout << "[INFO] Searching for package with name : "<< argv[1] << " In repository "<< argv[3] <<". Result : " << trap.getPackagesFromName(argv[1],argv[3]) << std::endl;
	}
	else
	{
		testCount ++;
		std::string packages = trap.getPackagesFromName("myjenkins","hgomez");
		if(packages == "myjenkins,myjenkins-lts")
		{
			std::cout << "[OK] Packages successfully found in repo hgomez : " << packages <<  std::endl;
			testPassed ++;
		}
		else
			std::cout << "[ERROR] Packages not found in repo hgomez : Result : " << packages << "Should be : myjenkins,myjenkins-lts" << std::endl;
	}
	std::cout << "---------------------------------------------------" << std::endl;
	
	std::cout << "[TEST] Search all packages in the specified repository" << std::endl;
	if(argc > 4)
	{
		std::cout << "[INFO] Searching for package with name : "<< argv[1] << " In repository "<< argv[3] <<". Result : " << trap.getPackagesFromName(argv[1],argv[3]) << std::endl;
	}
	else
	{
		testCount ++;
		std::string packages = trap.getAllPackages("hgomez");
		if(packages != "")
		{
			std::cout << "[OK] Packages successfully found in repo hgomez : " << packages <<  std::endl;
			testPassed ++;
		}
		else
			std::cout << "[ERROR] Packages not found in repo hgomez" << std::endl;
	}
	std::cout << "---------------------------------------------------" << std::endl;
	
	std::cout << "[TEST] Search all packages anywhere" << std::endl;
	testCount ++;
	std::string packages = trap.getAllPackages();
	if(packages != "")
	{
		std::cout << "[OK] Packages successfully found : " << packages <<  std::endl;
		testPassed ++;
	}
	else
		std::cout << "[ERROR] Packages not found" << std::endl;
	std::cout << "---------------------------------------------------" << std::endl;
	
	std::cout << "***************************************************" << std::endl;
	std::cout << "[RESULT] Test passed : " << testPassed << "/" << testCount << std::endl;
	
	return EXIT_SUCCESS;
}
