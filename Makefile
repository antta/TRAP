CPPSRC=TRAP/src/main/cpp/
JAVASRC=TRAP/src/main/java/

all:
	g++ -I $(JAVA_HOME)/include -I $(JAVA_HOME)/include/linux -shared $(CPPSRC)ZyppImpl.cc $(CPPSRC)trap.cc -o TRAP/src/main/lib/libZyppImpl.so -fPIC -lzypp -std=c++11 -fpermissive
