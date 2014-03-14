#include "trap.h"

int main(int argc, char **argv)
{
	Trap &trap = Trap::getInstance();
	std::cout << " packages : " << trap.getPackagesFromName(argv[1]) << std::endl;
	return EXIT_SUCCESS;
}
