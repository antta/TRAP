#include "trap.h"
#include <zypp/RepoInfo.h>

Trap::Trap():m_pathName("/")
{	
	m_repoManager = NULL;
	setRepoManager();
}

Trap::~Trap()
{
	if(m_repoManager != NULL)
		delete m_repoManager;
}

void Trap::setRepoManager()
{
	if(m_repoManager != NULL)
		delete m_repoManager;
	m_repoManager = new zypp::RepoManager(zypp::RepoManagerOptions(zypp::Pathname(m_pathName)));
}

std::string Trap::getPackagesFromName(std::string name, std::string repoAlias)
{
	m_buildString = "";

	zypp::ZYpp::Ptr zyppPtr = zypp::ZYppFactory::instance().getZYpp();

	zypp::PoolQuery query;

	query.setMatchGlob();
	query.setCaseSensitive(false);

	query.addAttribute(zypp::sat::SolvAttr::provides , name);
	
	if(repoAlias != "")
	{
		query.addRepo(repoAlias);
	}

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
	if(m_pathName != pathName)
	{
		m_pathName = pathName;
		setRepoManager();
	}
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

void Trap::addRepo(std::string repoAlias, std::string repoURL)
{
	zypp::RepoInfo repo;
        repo.addBaseUrl(repoURL);
        repo.setEnabled(true);
        repo.setAutorefresh(true);
        repo.setAlias(repoAlias);
        repo.setName(repoAlias);
	m_repoManager->addRepository(repo);
}

bool Trap::checkRepo(std::string repoURL)
{
	if (m_repoManager->probe(repoURL).asString() != std::string("NONE"))
		return true;
	return false;
}

void Trap::refreshRepo(std::string repoAlias)
{
	m_repoManager->refreshMetadata(m_repoManager->getRepositoryInfo(repoAlias));
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

std::string getAllPackages(std::string repoAlias = "")
{

}

/*
Récupérer le repoManageur :
zypp::RepoManager::RepoManager (const RepoManagerOptions & options = RepoManagerOptions())
	->zypp::RepoManagerOptions::RepoManagerOptions (const Pathname & root_r = Pathname())	


Regarder si le repo existe :
repo::RepoType zypp::RepoManager::probe	(const Url & url) const
	-> const std::string & 	asString () const
		->(doit être égal à tout sauf NONE_e)

Refresh le service :
void zypp::RepoManager::refreshService	(const std::string & alias)

Rajouter un repo :
void zypp::RepoManager::addService(const std::string & alias, const Url & url)	

Récupérer les info d'un service :
ServiceInfo zypp::RepoManager::getService( const std::string & alias) const
*/
