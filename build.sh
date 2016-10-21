#!/usr/bin/env bash
GRADLE="./gradlew"
CLEAN=1
BUILD_API=0
BUILD_DOCS=1
while [[ $# -gt 0 ]]; do
    case $1 in
        --no-clean)
            CLEAN=0
            ;;
        --skip-docs)
            BUILD_DOCS=0
            ;;
        --gradle-command)
            shift
            GRADLE="$1"
            ;;
        --build-api)
            BUILD_API=1
            ;;
        *)
            echo "usage: ./build.sh [--skip-docs] [--build-api] [--no-clean] [--gradle-command <cmd>]"
            exit 0
            ;;
    esac
    shift # use up this argument
done

RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

declare -a ERRORS

red () {
    printf "${RED}WARN${NC} $*\n"
    ERRORS+="$*"
}

green () {
    printf "${GREEN}INFO${NC} $*\n"
}

# use maven global settings from this folder
SETTINGS="$PWD/maven-settings.xml"
maven () {
    green "mvn $@ ($(basename $PWD))"
    mvn -gs "$SETTINGS" "$@"
    RES=$?
    if [ $RES -ne 0 ]; then
        red "ERROR: mvn $@ => $RES"
    fi
}

# my machine (tom) has some problem with gradlew binaries.
gradle () {
    green "gradle $@ ($(basename $PWD))"
    $GRADLE --no-daemon "$@"
    RES=$?
    if [ $RES -ne 0 ]; then
        red "ERROR: gradle $@ => $RES"
    fi
}

nc -zv localhost 8080
if [ $? -eq 0 ]; then
    red "Some other process is using port 8080"
    red "Use sudo netstat -n -p | head to find it"
    exit 1
fi

./start_p2.sh &
SERVER=$!
green "Starting P2 server [$SERVER]"

while ! nc -zv localhost 8080 ; do
    green "Waiting for port 8080..."
    sleep 5
done

green "Started P2 server [$SERVER]"

pushd core-projects

if [ $CLEAN == 1 ]; then
    gradle clean
else
    green "Skip clean"
fi

if [ $BUILD_API == 1 ]; then
    gradle :nhm-bundle-api:publish
else
    green "Skip API"
fi

gradle :nhm-impl-bundle:publish
gradle :nhm-cli-tools:publish

popd

pushd nhm-documentation
if [ $BUILD_DOCS == 1 ]; then
    maven install
    cd eclipse
    maven deploy
else
    green "Skip documentation"
fi
popd


pushd nhm-ide

green "Update signing keys"
git submodule init
git submodule update

maven clean package

popd

green "Stopping p2 server [$SERVER]..."
kill $SERVER

if [ ! -z $ERRORS ]; then
    red "$ERRORS"
else
    green "IDE built into nhm-ide/nhm-ide/cse.nhm.ide.build/target/products/"
fi
