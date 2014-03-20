CPPSRC=main/cpp/
LIBDIR=$(shell echo $(LD_LIBRARY_PATH) | cut -d ':' -f 1)
all:
	g++ -I $(JAVA_HOME)/include -I $(JAVA_HOME)/include/linux -shared $(CPPSRC)ZyppImpl.cc $(CPPSRC)trap.cc -o $(LIBDIR)/libZyppImpl.so -fPIC -lzypp -std=c++11 -fpermissive
