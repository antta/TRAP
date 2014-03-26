CPPSRC=TRAP/src/main/cpp
JAVASRC=TRAP/src/main/java
RESOURCESFOLDER=TRAP/src/main/resources
JAVA_HOME=/usr/java/latest

all:
	g++ -I $(JAVA_HOME)/include -I $(JAVA_HOME)/include/linux -shared $(CPPSRC)/ZyppImpl.cc $(CPPSRC)/trap.cc -o $(RESOURCESFOLDER)/libZyppImpl.so -fPIC -lzypp -std=c++11 -fpermissive
