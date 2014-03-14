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


	std::string getPackagesFromName(std::string name = "", std::string repo = "");
	std::string lastQueryResult();
	void setBuildResult(std::string buildString = "");
	void addBuildResult(std::string addString);
	void saveQueryResult();
	void setPathName(std::string pathName = "/");

private:

	QueryResult m_result;
	std::string m_pathName;
	
	std::string m_buildString;//used to overwrite the result string only when the querry is finished.
	std::string m_resultString;

	Trap();
	~Trap();
	Trap(Trap const&);              // Don't Implement.
        void operator=(Trap const&); // Don't implement
};
