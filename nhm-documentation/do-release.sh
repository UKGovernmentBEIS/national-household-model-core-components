#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail

OPTS=`getopt -o v: --long version:: -n 'do-release.sh' -- "$@"`

if [ $? != 0 ] ; then echo "Failed parsing options." >&2 ; exit 1 ; fi

eval set -- "$OPTS"

while true; do
    case "$1" in
        -v | --version)
            NEXT_VERSION="$2"
            shift 2
            ;;
        --)
            shift;
            break
            ;;
        *)
            echo "unexpected options $@"
            exit 1
    esac
done

THIS_VERSION=$(sed -n -e 's:<nhm\.version>\(.\+\)-SNAPSHOT</nhm\.version>:\1:p' pom.xml | tr -d ' ')

setversion () {
    V=$1
    sed -i -r -e "s:<nhm\\.version>.+</nhm\\.version>:<nhm.version>$V</nhm.version>:g" \
        -e "s:<stockimport\\.version>.+</stockimport\\.version>:<stockimport.version>$V</stockimport.version>:g" pom.xml
    mvn -U versions:set -DnewVersion="$V"
    git clean -f
}

echo "release $THIS_VERSION => $NEXT_VERSION"
sleep 4

setversion "${THIS_VERSION}"
git clean -f
mvn -U clean deploy
git commit -am "Release ${THIS_VERSION}"
git tag "v-${THIS_VERSION}"
setversion "${NEXT_VERSION}-SNAPSHOT"
git commit -am "Start work on ${NEXT_VERSION}"
git push
git push --tags
