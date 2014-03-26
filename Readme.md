TRAP Research Any Package
=========================

    * Compiler les class java
javac *.java
    * Générer le fichier header cpp. Il faut être dans TRAP/main/java pour que la commande se passe bien
javah JZypp
    * Compiler les sources c++
g++ -I $JAVA_HOME/include -I $JAVA_HOME/include/linux -shared trap.cc ZyppImpl.cc -o resource/libZyppImpl.so -std=c++11 -lzypp -fPIC
    * Créer un jar avec ça