TRAP Research Any Package
=========================



# About this projet

TRAP is a java module designed to research packages from software repositories. The meaning of the projet was to be used as a component inside a java web service to create linux distribution. The idea is to provide a java API to search and list packages.

# JNI, native lib and OpenSuse

As mentionned the projet is based on linux, with strong link to OpenSuse. The first implementation we made used the Suse's libzypp library which force this java librarie to be compiled and executed on Suse based OS. Infact we implemented a Java Native Interface (JNI) in order to use libzypp. Libzypp is provided as a compiled librarie (.so) in suse default repository. The information below allow you to compile the project using the native interface in command line style. In a wish to run and compile our librairie on every OS we also made a second version 100% written in Java. In this case we don't support every kind of repository. So the vanilla version is runnable on any environement if you use the full java implementation. When you instantiate the object, just add a false boolean in the constructor's signature.

# Command line compilation

Here are usefull information to compile the project :

* Compil Java classes

        javac *.java

* Generate native (cpp) headers. They need to be in TRAP/main/java

        javah JZypp

* Compil c++ sources

        g++ -I $JAVA_HOME/include -I $JAVA_HOME/include/linux -shared trap.cc ZyppImpl.cc -o resource/libZyppImpl.so -std=c++11 -lzypp -fPIC

* Now you can create a .jar

# Maven

This project can also be compiled and packaged using maven (which is way easier)
