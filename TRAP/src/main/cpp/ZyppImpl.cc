#include <stdio.h>
#include "trap.h"
#include "JZypp.h"   //Fichier d'en-tête créé par javah en compilant la classe java

JNIEXPORT jstring JNICALL Java_JZypp_whatProvides (JNIEnv *env, jobject obj, jstring jPackageName){
	
	const char *packageName = env->GetStringUTFChars(jPackageName, 0);
 
    //Traitement
    
    Trap &trap = Trap::getInstance();

    //const char *packageFound = getPackagesFromName(packageName);
    
    
    //Libération du malloc fait par jni
    //env->ReleaseStringUTFChars(jPackageName, packageFound);
    
    return env->NewStringUTF(trap.getPackagesFromName(packageName).c_str());
}

