#include <iostream>
#include <string>
#include <sstream>

#include <zypp/ZYpp.h>
#include <zypp/ZYppFactory.h>
#include <zypp/PathInfo.h>
#include <zypp/Capability.h>
#include <zypp/sat/Solvable.h>
#include <zypp/PoolItem.h>
#include <zypp/base/Algorithm.h>
#include <zypp/PoolQuery.h>
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

	std::string getAllPackages(std::string repoAlias = "");
	std::string getPackagesFromName(std::string name = "", std::string repoAlias = "");
	std::string lastQueryResult();
	void addRepo(std::string repoAlias, std::string repoURL);
	bool checkRepo(std::string repoURL);
	void refreshRepo(std::string repoAlias);
	

private:

	QueryResult m_result;
	std::string m_pathName;
	zypp::RepoManager *m_repoManager;
	
	std::string m_buildString;//used to overwrite the result string only when the querry is finished.
	std::string m_resultString;

	Trap();
	~Trap();

	void setBuildResult(std::string buildString = "");
	void addBuildResult(std::string addString);

	void setRepoManager();
	void saveQueryResult();

	Trap(Trap const&);              // Don't Implement.
        void operator=(Trap const&); // Don't implement
};
