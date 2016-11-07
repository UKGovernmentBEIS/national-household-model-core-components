#!/usr/bin/env bash
GRADLE="./gradlew"

declare -A steps
declare -A doc

steps["clean"]=1
steps["api"]=0
steps["docs"]=1
steps["tests"]=1
steps["ide"]=1
steps["release"]=0

doc["clean"]="clean before build"
doc["api"]="attempt to publish the API bundle"
doc["docs"]="build the manual (slow!)"
doc["tests"]="run the whole system tests"
doc["ide"]="build the IDE"
doc["release"]="get the IDE and CLI tools and put them in a zip file"

while [[ $# -gt 0 ]]; do
    case $1 in
        --do)
            shift
            steps[$1]=1
            ;;
        --skip)
            shift
            steps[$1]=0
            ;;
        --gradle-command)
            shift
            GRADLE="$1"
            ;;
        *)
            echo "usage: ./build.sh [--[skip|do] <clean|api|docs|tests|ide>] [--gradle-command <cmd>]"
            echo "with current arguments:"
            for i in "${!steps[@]}"
            do
                if [ ${steps[$i]} == 1 ]; then
                    echo "    --do $i : ${doc[$i]}"
                else
                    echo "    --skip $i : do not ${doc[$i]}"
                fi
            done
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
    green "$(basename $PWD) => mvn $@"
    mvn -gs "$SETTINGS" "$@"
    RES=$?
    if [ $RES -ne 0 ]; then
        red "ERROR: mvn $@ => $RES"
    fi
}

# my machine (tom) has some problem with gradlew binaries.
gradle () {
    green "$(basename $PWD) => gradle $@ "
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

if [ ${steps["clean"]} == 1 ]; then
    gradle clean
else
    green "Skip clean"
fi

if [ ${steps["api"]} == 1 ]; then
    gradle :nhm-bundle-api:publish
else
    green "Skip API"
fi

gradle :nhm-cli-tools:publish :nhm-impl-bundle:publish

popd


if [ ${steps["docs"]} == 1 ]; then
    pushd core-projects
    gradle :nhm-language-documentation:publish :nhm-stock-documentation:publish
    popd
    pushd nhm-documentation
    # TODO: we may want to build the PDF or web manual here
    maven deploy -pl eclipse -am
    popd
else
    green "Skip documentation"
fi


if [ ${steps["tests"]} == 1 ]; then
    green "Running system tests"
    pushd system-tests
    ./run-tests.sh
    popd
fi

if [ ${steps["ide"]} == 1 ]; then
    pushd nhm-ide

    green "Update signing keys"
    git submodule init
    git submodule update

    maven clean package

    popd
fi

if [ ${steps["release"]} == 1 ]; then
    # make a release of it all
    release=$(date -Idate)
    green "Release: $release"
    rdir="releases/$release"
    rm -f "${rdir}.zip"
    rm -rf "$rdir"
    mkdir -p "$rdir"
    cp nhm-ide/cse.nhm.ide.build/target/products/*.zip "$rdir"
    versions=($())

    get() {
        maven 'org.apache.maven.plugins:maven-dependency-plugin:2.8:get' \
              '-U' \
              '-DremoteRepositories=http://localhost:8080/maven/7b9c5ef4-16a1-4b8f-ae4e-83bb87337fdb/'\
              "-Dartifact=uk.org.cse.nhm:nhm-cli-tools:$1" \
              "-Ddest=tools.jar" 2>/dev/null >/dev/null
        if [ ! -f tools.jar ]; then
            red "unable to find CLI tools $1"
            return 1
        else
            V=$(java -jar tools.jar version)
            green "found CLI tools $1: $V"
            mv tools.jar "$rdir/$V.jar"
        fi
    }

    # attempt to download CLI jars for the required versions
    xmlstarlet sel -t -v '//import[@plugin="uk.org.cse.nhm.bundle.impl"]/@version' < nhm-ide/cse.nhm.models.feature/feature.xml |
        while read -r i || [[ -n "$i" ]]
        do
            get $i || get $i-SNAPSHOT || true
        done

    zip -r "${rdir}.zip" "$rdir"
fi

green "Stopping p2 server [$SERVER]..."

kill $SERVER

if [ ! -z "$ERRORS" ]; then
    red "various errors:"
    for e in "$ERRORS"
    do
        red "   $e"
    done
else
    green "IDE built into nhm-ide/nhm-ide/cse.nhm.ide.build/target/products/"
fi
