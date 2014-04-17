#include <stdio.h>
#include "trap.h"
#include "JZypp.h"   //Fichier d'en-tête créé par javah en compilant la classe java
#include <string>

/*
    basic jni cpp function skeleton ;

        /*
        * Get strings

        const char *sysRoot = env->GetStringUTFChars(jSysRoot, 0);
        const char *repoName = env->GetStringUTFChars(jRepoName, 0);
        const char *alias = env->GetStringUTFChars(jAlias, 0);

        /*
        * Get environement
        Trap &trap = Trap::getInstance();
        trap.setPathName(sysRoot);
        /*
        * Do stuff


        /*
        * Release strings

        env->ReleaseStringUTFChars(jSysRoot, sysRoot);
        env->ReleaseStringUTFChars(jRepoName, repoName);
        env->ReleaseStringUTFChars(jAlias, alias);
*/

JNIEXPORT jboolean JNICALL Java_fr_univsavoie_serveurbeta_trap_JZypp_isAValidRepository (JNIEnv * env, jobject obj, jstring jUrl){

        /*
        *   Get Strings
        */
        const char *repoURL = env->GetStringUTFChars(jUrl, 0);

        /*
        * Get environment
        */
        Trap &trap = Trap::getInstance();

        /*
        *   Do stuff
        */
        bool check = trap.checkRepo(repoURL);

        /*
        *   Release Strings
        */
        env->ReleaseStringUTFChars(jUrl, repoURL);

        return check;
}

JNIEXPORT jstring JNICALL Java_fr_univsavoie_serveurbeta_trap_JZypp_getPackage (JNIEnv *env, jobject obj, jstring jSysRoot, jstring jPackageName){

        /*
        * Get strings
        */
        const char *sysRoot = env->GetStringUTFChars(jSysRoot, 0);
        const char *packageName = env->GetStringUTFChars(jPackageName, 0);

        /*
        * Get environment
        */
        Trap &trap = Trap::getInstance();
        trap.setPathName(sysRoot);

        /*
        * Do stuff
        */
        jstring result = env->NewStringUTF(trap.getPackagesFromName(packageName).c_str());

        /*
        * Release strings
        */
        env->ReleaseStringUTFChars(jSysRoot, sysRoot);
        env->ReleaseStringUTFChars(jPackageName, packageName);

        return result;
}

/*
 * Class:     fr_univsavoie_serveurbeta_trap_JZypp
 * Method:    addReposiory
 * Signature: (Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_fr_univsavoie_serveurbeta_trap_JZypp_addReposiory(JNIEnv * env, jobject, jstring jSysRoot, jstring jURL, jstring jAlias){

        const char *sysRoot = env->GetStringUTFChars(jSysRoot, 0);
        const char *url = env->GetStringUTFChars(jURL, 0);
        const char *alias = env->GetStringUTFChars(jAlias, 0);

        /*
        * Get environment
        */
        Trap &trap = Trap::getInstance();
        trap.setPathName(sysRoot);

        /*
        * Do stuff
        */
        trap.addRepo(std::string(alias), std::string(url));

        /*
        * Release strings
        */
        env->ReleaseStringUTFChars(jSysRoot, sysRoot);
        env->ReleaseStringUTFChars(jURL, url);
        env->ReleaseStringUTFChars(jAlias, alias);
  }

/*
 * Class:     fr_univsavoie_serveurbeta_trap_JZypp
 * Method:    addRepository
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_fr_univsavoie_serveurbeta_trap_JZypp_addRepository__Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2
(JNIEnv * env, jobject, jstring jSysRoot, jstring jRepoURL, jstring jGPGURL, jstring jAlias) {
        const char *sysRoot = env->GetStringUTFChars(jSysRoot, 0);
        const char *repoUrl = env->GetStringUTFChars(jRepoURL, 0);
        const char *gpgUrl = env->GetStringUTFChars(jGPGURL, 0);
        const char *alias = env->GetStringUTFChars(jAlias, 0);

        /*
        * Get environment
        */
        Trap &trap = Trap::getInstance();
        trap.setPathName(sysRoot);

        /*
        * Do stuff
        */
        trap.addRepo(std::string(alias), std::string(repoUrl), std::string(gpgUrl));

        /*
        * Release strings
        */
        env->ReleaseStringUTFChars(jSysRoot, sysRoot);
        env->ReleaseStringUTFChars(jRepoURL, repoUrl);
        env->ReleaseStringUTFChars(jGPGURL, gpgUrl);
        env->ReleaseStringUTFChars(jAlias, alias);
    }

/*
 * Class:     fr_univsavoie_serveurbeta_trap_JZypp
 * Method:    getPackagesFromName
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_fr_univsavoie_serveurbeta_trap_JZypp_getPackagesFromName(JNIEnv * env, jobject, jstring jSysRoot,jstring jPackageName, jstring jAlias){

        /*
        * Get strings
        */
        const char *sysRoot = env->GetStringUTFChars(jSysRoot, 0);
        const char *packageName = env->GetStringUTFChars(jPackageName, 0);
        const char *alias = env->GetStringUTFChars(jAlias, 0);

         /*
         * Get environment
         */
        Trap &trap = Trap::getInstance();
        trap.setPathName(sysRoot);

        /*
        * Do stuff
        */
        jstring packages = env->NewStringUTF(trap.getPackagesFromName(std::string(packageName), std::string(alias)).c_str());

        /*
        * Release strings
        */
        env->ReleaseStringUTFChars(jSysRoot, sysRoot);
        env->ReleaseStringUTFChars(jPackageName, packageName);
        env->ReleaseStringUTFChars(jAlias, alias);

        return packages;
 }

/*
 * Class:     fr_univsavoie_serveurbeta_trap_JZypp
 * Method:    getPackagesFromRepo
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_fr_univsavoie_serveurbeta_trap_JZypp_getPackagesFromRepo(JNIEnv * env, jobject, jstring jSysRoot, jstring jAlias){

        /*
        * Get strings
        */
        const char *sysRoot = env->GetStringUTFChars(jSysRoot, 0);
        const char *alias = env->GetStringUTFChars(jAlias, 0);

         /*
         * Get environment
         */
        Trap &trap = Trap::getInstance();
        trap.setPathName(sysRoot);

        /*
        * Do stuff
        */
        jstring result = env->NewStringUTF(trap.getPackagesFromName("", std::string(alias)).c_str());

        /*
        * Release strings
        */
        env->ReleaseStringUTFChars(jSysRoot, sysRoot);
        env->ReleaseStringUTFChars(jAlias, alias);

        return result;
        }

/*
 * Class:     fr_univsavoie_serveurbeta_trap_JZypp
 * Method:    refreshRepo
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_fr_univsavoie_serveurbeta_trap_JZypp_refreshRepo(JNIEnv * env, jobject, jstring jSysRoot, jstring jAlias){

        /*
        * Get strings
        */
        const char *sysRoot = env->GetStringUTFChars(jSysRoot, 0);
        const char *alias = env->GetStringUTFChars(jAlias, 0);

        /*
        * Get environment
        */
        Trap &trap = Trap::getInstance();
        trap.setPathName(sysRoot);

        /*
        * Do stuff
        */
        trap.refreshRepo(std::string(alias));

        /*
        * Release strings
        */
        env->ReleaseStringUTFChars(jSysRoot, sysRoot);
        env->ReleaseStringUTFChars(jAlias, alias);
 }

/*
 * Class:     fr_univsavoie_serveurbeta_trap_JZypp
 * Method:    setPathName
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_fr_univsavoie_serveurbeta_trap_JZypp_setPathName
    (JNIEnv * env, jobject, jstring jSysRoot){

 }