#!/usr/bin/env bash
GRADLE="./gradlew"

# check dependencies

check_path () {
    which "$1" >/dev/null || { echo "missing $1 on PATH"; exit 1; }
}

check_path xmlstarlet
check_path git
check_path mvn
check_path java
check_path webfsd

declare -A steps
declare -A doc

steps["clean"]=1
steps["api"]=0
steps["docs"]=1
steps["tests"]=1
steps["ide"]=1
steps["package"]=0
steps["release"]=0

doc["clean"]="clean before build"
doc["api"]="attempt to publish the API bundle"
doc["docs"]="build the manual (slow!)"
doc["tests"]="run the whole system tests"
doc["ide"]="build the IDE"
doc["package"]="get the IDE and CLI tools and put them in a zip file"
doc["release"]="do the steps required for a release (tagging, setting versions, etc.)"

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
            echo "usage: ./build.sh [--[skip|do] <clean|api|docs|tests|ide|package|release>] [--gradle-command <cmd>]"
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

ask () {
    read -r -p "$1? [y/N]" response
    [[ "$response" =~ ^y|Y ]]
}

if [ ${steps["release"]} == 1 ] ; then
    source ./release.sh
fi

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

gradle :nhm-impl-bundle:publish

popd


if [ ${steps["docs"]} == 1 ]; then
    pushd core-projects
    #these could be install rather than publish to avoid p2 serving older versions
    gradle :nhm-language-documentation:publish :nhm-stock-documentation:publish
    popd
    pushd nhm-documentation
    # TODO: we may want to build the PDF or web manual here
    maven deploy -pl eclipse -am -U
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
    green "Copy jars to p2 inputs directory"
    find binaries -iname \*.jar -exec cp '{}' p2/inputs/plugins/ ';'
    pushd p2
    mvn tycho-p2-extras:publish-features-and-bundles
    cd target/repository
    webfsd -F -p 8000 &
    WEB=$!
    popd

    pushd nhm-ide

    green "Update signing keys"
    git submodule init
    git submodule update

    maven clean package

    green "product is in nhm-ide/cse.nhm.ide.build/target/products/*.zip"

    popd

    kill $WEB
fi

if [ ${steps["package"]} == 1 ]; then
    # make a release of it all
    release=$(date -Idate)
    green "Package: $release"
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
              "-Dartifact=uk.org.cse.nhm:nhm-impl-bundle:$1" \
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

if [ ! -z "$ERRORS" ]; then
    red "various errors:\n"
    for e in "$ERRORS"
    do
        red "   $e\n"
    done
else
    green "IDE built into nhm-ide/nhm-ide/cse.nhm.ide.build/target/products/"
fi
