#include "trap.h"

Trap::Trap():m_pathName("/")
{

}

Trap::~Trap()
{
	
}

std::string Trap::getPackagesFromName(std::string name, std::string repo)
{
	m_buildString = "";

	zypp::ZYpp::Ptr zyppPtr = zypp::ZYppFactory::instance().getZYpp();

	zypp::PoolQuery query;

	query.setMatchGlob();
	query.setCaseSensitive(false);

	zypp::Capability cap = zypp::Capability::guessPackageSpec( std::string("*") + name + std::string("*"));
	std::string packageName = cap.detail().name().asString();
	query.addKind(zypp::ResKind::package);

	zypp::sat::SolvAttr attr = zypp::sat::SolvAttr::provides;
	query.addDependency( attr , packageName, cap.detail().op(), cap.detail().ed(), zypp::Arch(cap.detail().arch()) );

	//query.addRepo( repo_it->alias());

	zypp::Pathname sysRoot( m_pathName );

	zyppPtr->initializeTarget( sysRoot, false );
	zyppPtr->target()->load();
	zyppPtr->resolver()->resolvePool();
	
	zypp::invokeOnEach(query.poolItemBegin(), query.poolItemEnd(), m_result);
	saveQueryResult();
	return m_resultString;
}

std::string Trap::lastQueryResult()
{
	return m_resultString;
}

void Trap::setPathName(std::string pathName)
{
	m_pathName = pathName;
}

void Trap::addBuildResult(std::string addString)
{
	m_buildString += addString;
}

void Trap::setBuildResult(std::string buildString)
{
	m_buildString = buildString;
}

void Trap::saveQueryResult()
{
	m_resultString = m_buildString;
}

QueryResult::QueryResult()
{
}

QueryResult::~QueryResult()
{
}

bool QueryResult::operator()( const zypp::PoolItem & pi )
{
	Trap &trap = Trap::getInstance();
	trap.addBuildResult(pi->name() + ",");
	return true;
}
