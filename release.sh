# you can't run this on its own; it's part of ./build.sh

do_commit () {
    git commit -am "$1" && git tag "$2"
}

set_documentation_version () {
    pushd nhm-documentation

    # we also have to edit the POM files, because a maven property cannot
    # be a function of the maven version. Who knew.

    xmlstarlet ed \
               -N m=http://maven.apache.org/POM/4.0.0 \
               -u /m:project/m:properties/m:nhm.version -v "$1"\
               -u /m:project/m:properties/m:stockimport.version -v "$1" \
               pom.xml > pom.xml.updated

    mv pom.xml.updated pom.xml

    # versions:set lets us do the other edit more easily
    # although obviously we have to start java and all that.
    maven versions:set -DnewVersion="$1"

    rm -f */pom.xml.versionsBackup pom.xml.versionsBackup

    popd
}

add_model_dependency_to_ide () {
    pushd nhm-ide/cse.nhm.models.feature/
    if ! xmlstarlet sel -t \
         -v '//import[@plugin="uk.org.cse.nhm.bundle.impl"]/@version' < \
         feature.xml | grep -q "$1"; then
        xmlstarlet ed \
                   -s /feature/requires \
                   -t elem -n NewImport \
                   -i //NewImport -t attr -n plugin -v uk.org.cse.nhm.bundle.impl \
                   -i //NewImport -t attr -n version -v "$1" \
                   -i //NewImport -t attr -n match -v equivalent \
                   -r //NewImport -v import\
                   feature.xml > feature.xml.updated
        mv feature.xml.updated feature.xml
    fi
    popd
}

green "Checking current version"
pushd core-projects
VER=$(gradle cV | grep -oP "(?<=Project version: ).+")
popd

green "Version from tags: $VER"
RE="(.+)-SNAPSHOT"
if [[ "$VER" =~ $RE ]]; then
    NOT_SNAPSHOT="${BASH_REMATCH[1]}"

    read -r -p "Version for release [${NOT_SNAPSHOT}]: " NEWVER
    NEWVER="${NEWVER:-${NOT_SNAPSHOT}}"
    MAJOR_VERSION=$(echo $NEWVER | cut -d. -f1,2)
    MINOR_VERSION=$(echo $NEWVER | cut -d. -f3)
    MINOR_VERSION=$(( MINOR_VERSION + 1 ))
    SUGGESTED_NEXTVER=${MAJOR_VERSION}.${MINOR_VERSION}

    read -r -p "Subsequent version (omit snapshot) [$SUGGESTED_NEXTVER]: " NEXTVER

    NEXTVER="${NEXTVER:-${SUGGESTED_NEXTVER}}"

    [[ -z $NEXTVER ]] && { red "You need to give a next version"; exit 1; }


    green "Updating to version ${NEWVER}"

    if ! grep -q "${NEWVER}" \
         "core-projects/nhm-language-documentation/src/main/resources/changelog.org"; then
        red "The version ${NEWVER} is not mentioned in the changelog!"
        red "  -> Update core-projects/nhm-language-documentation/src/main/resources/changelog.org";
        exit 1
    fi

    ask "Is the changelog up to date" || exit 1

    set_documentation_version "${NEWVER}"
    add_model_dependency_to_ide "${NEWVER}"

    green "Making commit for release"
    do_commit "Release preparation for ${VER} -> ${NEWVER}" "v-${NEWVER}"

    green "Going to next version"
    set_documentation_version "${NEXTVER}-SNAPSHOT"
    add_model_dependency_to_ide "${NEXTVER}"
    do_commit "Starting work on ${NEXTVER}" "v-${NEXTVER}-alpha"

    ask "Push these changes" || exit 1

    green "Pushing commits & tags"
    git push origin master
    git push --tags
else
    red "Version $VER is not a -SNAPSHOT version, so it should already be a release!"
    red "You may just need to ./build.sh --do package instead."
    exit 1
fi
