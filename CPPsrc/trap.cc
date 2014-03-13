#include "trap.h"

std::string getPackagesFromName(std::string name, std::string repo, std::string pathName)
{
	zypp::ZYpp::Ptr zyppPtr = zypp::ZYppFactory::instance().getZYpp();

	zypp::PoolQuery query;

	std::cout << "Looking for packages with name : " << name << std::endl;

	query.setMatchGlob();
	query.setCaseSensitive(false);
	zypp::Capability cap = zypp::Capability::guessPackageSpec( std::string("*") + name + std::string("*"));
	std::string packageName = cap.detail().name().asString();
	query.addKind(zypp::ResKind::package);
	zypp::sat::SolvAttr attr = zypp::sat::SolvAttr::provides;
	query.addDependency( attr , packageName, cap.detail().op(), cap.detail().ed(), zypp::Arch(cap.detail().arch()) );
	//query.addRepo( repo_it->alias());

	//Chemin vers le workspace jenkins 
	zypp::Pathname sysRoot( "/" );

	zyppPtr->initializeTarget( sysRoot, false );
	zyppPtr->target()->load();
	zyppPtr->resolver()->resolvePool();


	QueryResult result;
	zypp::invokeOnEach(query.poolItemBegin(), query.poolItemEnd(), result);
	return result.getResultString();
}

QueryResult::QueryResult()
{
	m_resultStream = new std::stringstream();
}
QueryResult::~QueryResult()
{
	delete m_resultStream;
}

bool QueryResult::operator()( const zypp::PoolItem & pi )
{
	(*m_resultStream) << pi->name() << ";";
	for debug : std::cout<< "name : " << pi->name()  << std::endl;
	return true;
}

std::string QueryResult::getResultString()
{
	return m_resultStream->str();
}
