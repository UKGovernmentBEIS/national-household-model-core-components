#!/usr/bin/env bash
GRADLE="./gradlew"
while [[ $# -gt 0 ]]; do
    case $1 in
        --no-documentation)
            SKIP_DOCUMENTATION=1
            ;;
        --stupid-gradle)
            GRADLE="gradle"
            ;;
        --include-api)
            BUILD_API=1
            ;;
        --help)
            echo "usage: ./build.sh [--no-documentation] [--stupid-gradle] [--include-api]"
            exit 0
            ;;
    esac
    shift # use up this argument
done


RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

ERRS=""

red () {
    printf "${RED}WARN${NC} $*\n"
    ERRS="$ERRS\n$*"
}

green () {
    printf "${GREEN}INFO${NC} $*\n"
}

# use maven global settings from this folder
SETTINGS="$PWD/maven-settings.xml"
maven () {
    mvn -gs "$SETTINGS" "$@"
    RES=$?
    if [ $RES -ne 0 ]; then
        red "ERROR: mvn $@ => $RES (in $PWD)"
    fi
}

# my machine (tom) has some problem with gradlew binaries.
gradle () {
    $GRADLE "$@"
    RES=$?
    if [ $RES -ne 0 ]; then
        red "ERROR: gradle $@ => $RES (in $PWD)"
    fi
}

green "Building and installing core projects to local maven repo"
pushd core-projects/
gradle clean install -x test
popd

./start_p2.sh &
SERVER=$!
green "Started P2 Server [$SERVER]"

if [ ! -z $BUILD_API ]; then
    green "Building and publising nhm-api-bundle"
    pushd core-projects/nhm-bundle-api
    gradle clean publish
    popd
else
    green "skipping API"
fi

green "Building and publishing nhm-impl-bundle"
pushd core-projects/nhm-impl-bundle
gradle clean publish
popd

#TODO Not sure why the ide doesn't pick this up from p2

if [ -z "$SKIP_DOCUMENTATION" ]; then
    green "Building and publishing documentation bundle"
    pushd nhm-documentation
    maven install
    cd eclipse
    maven deploy
    popd
else
    green "skipping documentation"
fi

pushd nhm-ide

green "Getting signing keys submodule"
git submodule init
git submodule update

green "Building IDE"
maven clean package

popd

green "Stopping p2 server [$SERVER]..."
kill $SERVER

if [ ! -z $ERRS ]; then
    red "$ERRS"
else
    green "IDE built into nhm-ide/nhm-ide/cse.nhm.ide.build/target/products/"
fi
