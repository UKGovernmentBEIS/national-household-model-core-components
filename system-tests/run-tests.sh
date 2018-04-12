#!/usr/bin/env bash

# horrible script to run all the tests.
# requires the P2 repo to be running, to be able to get hold of a model release
# TODO it could instead trigger the build of the jar, or be supplied with that jar?

RED='\033[0;31m'
GREEN='\033[0;32m'
CYAN='\033[0;36m'
NC='\033[0m'
PREFIX="main"

function err {
    printf "${RED}[ERROR]${NC}\t${CYAN}${PREFIX}${NC}\t%s\n" "$@" 1>&2
}

function inf {
    printf "${GREEN}[INFO]${NC}\t${CYAN}${PREFIX}${NC}\t%s\n" "$@"
}

function newest {
    find "$@" | sort -n | tail -n 1
}

function download_version {
    inf "downloading $1..."
    GET=$1
    if [[ $GET == LATEST ]]; then
        GET="*"
    fi
    shopt -s extglob
    pth=$(newest ../p2/inputs/plugins/nhm-impl-bundle$GET.jar ../binaries/uk/org/cse/nhm/nhm-impl-bundle/*/nhm-impl-bundle$GET.jar)
    shopt -u extglob
    [[ -f $pth ]] || (err "Could not download $1" ; exit 1)
    inf "$pth => $2"
    cp $pth "$2"
    return 0
}

function get_version {
    VERSION_FILE="$1/version"
    VERSION="LATEST"
    if [ -f "${VERSION_FILE}" ]
    then
        VERSION=$(cat "${VERSION_FILE}")
    fi

    if [ "${VERSION}" != "LATEST" ] && [ ! -f ".versions/nhm-${VERSION}.jar" ]
    then
        download_version "${VERSION}" ".versions/nhm-${VERSION}.jar"
    fi

    echo ".versions/nhm-${VERSION}.jar"
}

function run_test {
    TESTNAME=$1
    HERE=$(pwd)
    OLDPREFIX="${PREFIX}"
    PREFIX="${TESTNAME}"
    TESTSCENARIO="${TESTNAME}/main.nhm"
    TESTSCRIPT="${TESTNAME}/tests.R"
    OUTPUT="${TESTNAME}/output.zip"
    SIMLOG="${TESTNAME}/sim-log.txt"
    TESTLOG="${TESTNAME}/test-log.txt"

    DELETE_OUTPUT=1
    if [ -f "${TESTSCENARIO}" ]
    then
        JAR=$(get_version "${TESTNAME}")

        inf "running scenario with version $JAR $(java -jar $JAR version)"
        rm -f "${SIMLOG}" "${TESTLOG}" "${OUTPUT}"
        #java -agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=y -jar "${JAR}" run "${TESTSCENARIO}" "${OUTPUT}" >> "${SIMLOG}" 2>&1
        java -jar "${JAR}" run "${TESTSCENARIO}" "${OUTPUT}" >> "${SIMLOG}" 2>&1
        if [ ! -f ${OUTPUT} ]
        then
            err "produced no output"
            echo -e "${TESTNAME}\tNo output.zip produced" >> failed-tests
        else
            zipinfo "${OUTPUT}" errors.txt &> /dev/null
            if [ $? == 0 ]
            then
                ERROR=$(unzip -p ${OUTPUT} errors.txt | head -n 1)
                err "${ERROR}"
                unzip -p ${OUTPUT} errors.txt >> "${SIMLOG}"
                echo -e "${TESTNAME}\toutput has errors.txt" >> failed-tests
                DELETE_OUTPUT=0
            elif [ -f "${TESTSCRIPT}" ]
            then
                inf "running test script"
                {
                    cat "prefix.R" ;
                    echo "setwd(\"${TESTNAME}\")" ;
                    cat "${TESTSCRIPT}" ;
                    echo "die.if.failed()" ;
                } | R --silent --slave --no-save >> "${TESTLOG}" 2>&1
                if [ $? != 0 ]
                then
                    err "the test script failed: $(grep -e '^\[1\] \"ERROR:' ${TESTLOG} | tail -n 1)"
                    echo -e "${TESTNAME}\thas failing tests" >> failed-tests
                    DELETE_OUTPUT=0
                fi
            fi
        fi
    else
        err "main.nhm is missing"
    fi

    [[ $DELETE_OUTPUT == 1 ]] && rm -f "${OUTPUT}"

    PREFIX="${OLDPREFIX}"
}

rm -f failed-tests

download_version "LATEST" ".versions/nhm-LATEST.jar"

if [ "$#" -gt 0 ]; then
    for file in "$@"; do
        if [ -d "$file" ]
        then
            run_test "$file"
        fi
    done
else
    find . -type f -name main.nhm -exec dirname '{}' ';' |
    while read file ; do
        if [ -d "$file" ]
        then
            run_test "$file"
        fi
    done
fi

if [ -f failed-tests ]
then
    echo "Some tests failed"
    exit 1
fi
