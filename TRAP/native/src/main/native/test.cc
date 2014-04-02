#include "trap.h"

int main(int argc, char **argv)
{
	Trap &trap = Trap::getInstance();
	if(argc >= 5)
		trap.setPathName(argv[1]);
		if(trap.checkRepo(argv[2]))
		{
			std::cout << "le repository existe" << std::endl;
			trap.addRepo(argv[3], argv[2]);
			std::cout << " package trouvés pour " << argv[4] << " : " << trap.getPackagesFromName(argv[4], argv[3]);
		}
		else 
			std::cout << "le repository n'existe pas" << std::endl;

	return EXIT_SUCCESS;
}
