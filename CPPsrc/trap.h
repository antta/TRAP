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

std::string getPackagesFromName(std::string name, std::string repo = "", std::string pathName = "/");

class QueryResult
{
public:
	QueryResult();
	~QueryResult();

	bool operator()( const zypp::PoolItem & pi );
	std::string getResultString();

private:
	std::stringstream *m_resultStream;
};
