CPPSRC=main/cpp/

all:
	pwd
	g++ -I $(JAVA_HOME)/include -I $(JAVA_HOME)/include/linux -shared $(CPPSRC)ZyppImpl.cc $(CPPSRC)trap.cc -o $(LD_LIBRARY_PATH)/libZyppImpl.so -fPIC -lzypp -std=c++11 -fpermissive
