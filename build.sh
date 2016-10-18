#!/bin/bash
echo "Building and installing core projects to local maven repo"
cd core-projects/
./gradlew clean install -x test

echo "Stating package-drone p2 server"
cd ../org.eclipse.packagedrone.server-0.12.2
java -jar plugins/org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar &
SERVER=$!

cd ..

echo "Building and publising nhm-api-bundle"
cd core-projects/nhm-bundle-api
./gradlew clean publish
cd ../..

echo "Building and publishing nhm-impl-bundle"
cd core-projects/nhm-impl-bundle
./gradlew clean publish
cd ../..

#TODO Not sure why the ide doesn't pick this up from p2
echo "Building and publishing documentation bundle"
cd nhm-documentation
mvn install
cd eclipse
mvn deploy
cd ../..

echo "Building the ide"
cd nhm-ide
mvn clean package

echo "Stopping p2 server...I'm lying not sure how todo this yet sorry ;-)"
#TODO: How to stop the drone server? Something with grep on process list to get process id to kill?
kill $!
