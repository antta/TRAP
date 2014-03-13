#include "trap.h"

int main(int argc, char **argv)
{
	std::cout << getPackagesFromName(argv[1]) << std::endl;
	return EXIT_SUCCESS;
}
