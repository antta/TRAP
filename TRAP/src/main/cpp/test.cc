#include "trap.h"

int main(int argc, char **argv)
{
	Trap &trap = Trap::getInstance();
	if(argc >= 3)
		if(trap.checkRepo(argv[2]))
			std::cout << "le repository existe" << std::endl;
	if(argc >= 4)
		std::cout << " packages : " << trap.getPackagesFromName(argv[1],argv[3]) << std::endl;
	else
		std::cout << " packages : " << trap.getPackagesFromName(argv[1]) << std::endl;
	return EXIT_SUCCESS;
}
