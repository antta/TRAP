#include <iostream>
#include <string>
#include <sstream>

#include <zypp/PoolItem.h>
#include <zypp/RepoManager.h>

class QueryResult
{
public:
	QueryResult();
	~QueryResult();

	bool operator()( const zypp::PoolItem & pi );
};

class Trap
{
public:

    //singleton class
	static Trap &getInstance()
	{
		static Trap s_instance;
		return s_instance;
	}

    //set the path where etc/var/zypp/repo.d and other folder will be created
	void setPathName(std::string pathName = "/");
	std::string getPathName();

    //search all package from a repository or all repositories
	std::string getAllPackages(std::string repoAlias = "");
	//search a package from name, Act very strange, seems to not work anymore
	std::string getPackagesFromName(std::string name = "", std::string repoAlias = "");
	//get the last returned string (instead of redoing a search)
	std::string lastQueryResult();

	//find if the repo exists locally
	bool isRepositoryExists(std::string repoAlias);
	// add a repository
	bool addRepo(std::string repoAlias, std::string repoURL, std::string gpgCheckURL = "");
	//check if the URL is a repository
	bool checkRepo(std::string repoURL);
	//refresh the given repository
	bool refreshRepo(std::string repoAlias);

	//delete all repositories from the current path name
	void clean();

	//used for the search to create a string before changing the result string (not sure if it is useful)
	void setBuildResult(std::string buildString = "");
	void addBuildResult(std::string addString);

private:

	QueryResult m_result;
	std::string m_pathName;
	zypp::RepoManager *m_repoManager;
	
	void initRepoManager();
	
	std::string m_buildString;//Used to overwrite the result string only when the query is finished.
	std::string m_resultString;

	Trap();
	~Trap();

	void saveQueryResult();

	Trap(Trap const&);              // Don't Implement.
        void operator=(Trap const&); // Don't implement
};
