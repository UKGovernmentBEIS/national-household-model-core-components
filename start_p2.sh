#!/bin/bash
#echo "Stating package-drone p2 server"
cd org.eclipse.packagedrone.server-0.12.2
java -jar plugins/org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar &
SERVER=$!
cd ..
