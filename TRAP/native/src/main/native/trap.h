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

	static Trap &getInstance()
	{
		static Trap s_instance;
		return s_instance;
	}

	void setPathName(std::string pathName = "/");
	std::string getPathName();

	std::string getAllPackages(std::string repoAlias = "");
	std::string getPackagesFromName(std::string name = "", std::string repoAlias = "");//Act very strange
	std::string lastQueryResult();
	bool isRepositoryExists(std::string repoAlias); 
	bool addRepo(std::string repoAlias, std::string repoURL, std::string gpgCheckURL = "");
	bool checkRepo(std::string repoURL);
	bool refreshRepo(std::string repoAlias);//Does not work at all
	
	void clean();
	
	void setBuildResult(std::string buildString = "");
	void addBuildResult(std::string addString);

private:

	QueryResult m_result;
	std::string m_pathName;
	zypp::RepoManager *m_repoManager;
	
	void initRepoManager();
	
	std::string m_buildString;//Used to overwrite the result string only when the querry is finished.
	std::string m_resultString;

	Trap();
	~Trap();

	void saveQueryResult();

	Trap(Trap const&);              // Don't Implement.
        void operator=(Trap const&); // Don't implement
};
