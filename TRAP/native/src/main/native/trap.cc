#include "trap.h"

#include <zypp/RepoInfo.h>
#include <zypp/Repository.h>
#include <zypp/ResPool.h>
#include <zypp/ZYpp.h>
#include <zypp/ZYppFactory.h>
#include <zypp/base/Algorithm.h>
#include <zypp/PoolQuery.h>
#include <zypp/PathInfo.h>
#include <zypp/Capability.h>
#include <zypp/sat/Solvable.h>
#include <zypp/Url.h>

#include <exception>

#include <stdio.h>
#include <unistd.h>
    #define GetCurrentDir getcwd

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
	
	try
	{
		
		zypp::ZYpp::Ptr zyppPtr = zypp::ZYppFactory::instance().getZYpp();

		zypp::PoolQuery query;

		query.setCaseSensitive(false);
		query.setMatchGlob();
		
		zypp::Capability cap = zypp::Capability::guessPackageSpec( name );
		std::string newName = cap.detail().name().asString();

		query.addDependency( zypp::sat::SolvAttr::name , newName, cap.detail().op(), cap.detail().ed(), zypp::Arch(cap.detail().arch()) );
		query.addDependency( zypp::sat::SolvAttr::provides , newName, cap.detail().op(), cap.detail().ed(), zypp::Arch(cap.detail().arch()) );
		
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
	
	}
	catch (const std::exception &e)
	{
		std::cerr << "[ERROR] Exception caught in Trapp::getPackagesFromName : "<< e.what() << std::endl;
		return "";
	}
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
		if(pathName[0] != '/')
		{
			char cCurrentPath[FILENAME_MAX];

			if (!GetCurrentDir(cCurrentPath, sizeof(cCurrentPath)))
			{
				std::cerr << "[ERROR] When trying to get the current directory : "<< errno << std::endl;
			}
			else
			{
				std::cout << "[INFO] : Changing Path from relative toAbsolute, adding : " << cCurrentPath << std::endl;
				cCurrentPath[sizeof(cCurrentPath) - 1] = '\0';
				pathName = std::string(cCurrentPath) + std::string("/") + pathName;
			}
		}
		m_pathName = pathName;
		setRepoManager();
	}
}

std::string Trap::getPathName()
{
	return m_pathName;
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
	m_resultString = m_resultString.substr(0,m_resultString.size()-1);
}

bool Trap::isRepositoryExists(std::string repoAlias)
{
	return m_repoManager->hasRepo(repoAlias);
}

bool Trap::addRepo(std::string repoAlias, std::string repoURL, std::string gpgCheckURL)
{
	if(!checkRepo(repoURL))
	{
		std::cerr << "[ERROR] in Trapp::addRepo : " << repoURL << " is not a valid URL Repository" << std::endl;
		return false;
	}
	if(m_repoManager->hasRepo(repoAlias))
	{
		std::cerr << "[WARNING] in Trapp::addRepo : Repository Already Exist" << std::endl;
		std::cerr << "[TODO] : Change the repository data instead of doing nothing" << std::endl;
		return true;
	}
	else
	{
		try
		{
			zypp::RepoInfo repo;
			repo.setBaseUrl(repoURL);
			repo.setEnabled(true);
			repo.setAutorefresh(false);
			if(gpgCheckURL != "")
			{
				repo.setGpgCheck(true);
				repo.setGpgKeyUrl(zypp::Url(gpgCheckURL));
			}
			repo.setKeepPackages(false);
			repo.setAlias(repoAlias);
			repo.setName(repoAlias);
			repo.setProbedType(m_repoManager->probe(repoURL));
			repo.setPath(zypp::Pathname(m_pathName));
			m_repoManager->addRepository(repo);
		}
		catch(const std::exception &e)
		{
			std::cerr << "[ERROR] Exception caught in Trapp::addRepo : "<< e.what() << std::endl;
			return false;
		}
		return true;
	}
}

bool Trap::checkRepo(std::string repoURL)
{
	try
	{
		if (m_repoManager->probe(repoURL).asString() != std::string("NONE"))
			return true;
		return false;
	}
	catch (const std::exception &e)
	{
		std::cerr << "[INFO] in Trap::checkRepo, unable to reach : " << repoURL << " (verify you are connected to the network) : Exception Caught :" << e.what() << std::endl;
		return false;
	}
}

bool Trap::refreshRepo(std::string repoAlias)
{
	bool refreshSuccessfull = false;
	try 
	{
	zypp::Pathname path( m_pathName );
	zypp::ZYppFactory::instance().getZYpp()->initializeTarget( path, false );

	zypp::RepoInfo repo = m_repoManager->getRepositoryInfo(repoAlias);
	
		for(zypp::RepoInfo::urls_const_iterator it = repo.baseUrlsBegin(); it != repo.baseUrlsEnd(); ++it)
		{
			try
			{
				//RepoStatus metadataStatus (const RepoInfo &info) const
				if(m_repoManager->checkIfToRefreshMetadata (repo, *it) == zypp::RepoManager::REFRESH_NEEDED)
				{
					m_repoManager->refreshMetadata(repo, zypp::RepoManager::RefreshIfNeeded);
					m_repoManager->buildCache(repo, zypp::RepoManager::BuildIfNeeded);
					refreshSuccessfull = true;
					break;
				}
			} 
			catch(const std::exception &e)
			{
				std::cerr << "[ERROR] in Trap::refreshRepo : " << *it << " doesn't look good : Exception Caught :" << e.what() << std::endl;
			}
		}
	}
	catch (const std::exception &e)
	{
		std::cerr << "[ERROR] in Trap::refreshRepo : Exception Caught :" << e.what() << std::endl;
	}
	return refreshSuccessfull;
}


std::string Trap::getAllPackages(std::string repoAlias)
{
	return getPackagesFromName("",repoAlias);
}

void Trap::clean()
{
	for(zypp::RepoManager::RepoConstIterator it = m_repoManager->repoBegin(); it !=  m_repoManager->repoEnd(); it = m_repoManager->repoBegin())
	m_repoManager->removeRepository(*it);
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
