CPPSRC=main/cpp/

all:
	g++ -I $(JAVA_HOME)/include -I $(JAVA_HOME)/include/linux -shared $(CPPSRC)ZyppImpl.cc $(CPPSRC)trap.cc -o $(CPPSRC)/libZyppImpl.so -fPIC -lzypp -std=c++11 -fpermissive
