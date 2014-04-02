#include <stdio.h>
#include "trap.h"
#include "JZypp.h"   //Fichier d'en-tête créé par javah en compilant la classe java

JNIEXPORT jstring JNICALL Java_fr_univsavoie_serveurbeta_trap_JZypp_searchPackage (JNIEnv *env, jobject obj, jstring jPackageName){
	
	const char *packageName = env->GetStringUTFChars(jPackageName, 0);
 
    //Traitement
    
    Trap &trap = Trap::getInstance();

    //const char *packageFound = getPackagesFromName(packageName);
    
    
    //Libération du malloc fait par jni
    //env->ReleaseStringUTFChars(jPackageName, packageFound);
    
    return env->NewStringUTF(trap.getPackagesFromName(packageName).c_str());
}

JNIEXPORT jboolean JNICALL Java_fr_univsavoie_serveurbeta_trap_JZypp_isAValidRepository (JNIEnv * env, jobject obj, jstring url, jstring alias){
    
    const char *packageName = env->GetStringUTFChars(url, 0);
 
    //Traitement
    
    Trap &trap = Trap::getInstance();

    return trap.checkRepo(packageName);
    
}
