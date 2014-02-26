#include <iostream>

#include <zypp/ZYpp.h>
#include <zypp/ZYppFactory.h>
#include <zypp/PathInfo.h>
#include <zypp/Capability.h>
#include <zypp/sat/Solvable.h>
#include <zypp/sat/WhatProvides.h>

using namespace zypp;

int main(int argc, char **argv) {
       zypp::ZYpp::Ptr zyppPtr = zypp::ZYppFactory::instance().getZYpp();

	//Chemin vers le workspace jenkins
       zypp::Pathname sysRoot( "/" );

       zyppPtr->initializeTarget( sysRoot, false );
       zyppPtr->target()->load();

       std::cout << "Looking for packages which provide " << argv[1] << std::endl;
       zypp::Capability cap(argv[1]);
       zypp::sat::WhatProvides wp(cap);

       if (wp.empty()) {
               std::cout << "No providers of " << argv[1] << " found" << std::endl;
       } else {
               zypp::sat::Solvable package(*wp.begin());
               std::cout << "Provided by " << package.name() << " version " << package.edition().version()
                       << std::endl;
       }

       return EXIT_SUCCESS;
}

class ZyppCPPImpl{


  JNIEXPORT jstring JNICALL searchPackage(JNIEnv *env, jstring packageName){
    NSString* nativeString = JNFJavaToNSString(env, packageName);

    searchPackage(nativeString);

    (*env)->ReleaseStringUTFChars(env, javaString, nativeString);
    return javaString;
  }

  public String[] searchPackage(String packageName){

     //mouline avec libzypp et retourne ce qu'il faut

  }

/**
* main pour chercher un package : à appeler avec un exec pour l'incrément 1
*/
int main(int argc, char **argv) {

}

}