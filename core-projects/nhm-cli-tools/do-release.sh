#!/bin/sh

THIS_VERSION=$1
NEXT_VERSION=$2

# release script for the CLI tools
# this doesn't use tag based versioning, because its version is kind of determined by the dependency.

sed -i -r -e "s:version\\s*=.+$:version = '${THIS_VERSION}':g" build.gradle
sed -i -r -e "s#compile \"uk\\.org\\.cse\\.nhm:bundle-impl:.+\"\$#compile \"uk.org.cse.nhm:bundle-impl:${THIS_VERSION}.+\"#g" build.gradle

./gradlew clean publish

git add build.gradle
git commit  --allow-empty -m "Releasing version ${THIS_VERSION}"

sed -i -r -e "s:version\\s*=.+$:version = '${NEXT_VERSION}-SNAPSHOT':g" build.gradle
sed -i -r -e "s#compile \"uk\\.org\\.cse\\.nhm:bundle-impl:.+\"\$#compile \"uk.org.cse.nhm:bundle-impl:${NEXT_VERSION}.+\"#g" build.gradle

git add build.gradle
git commit --allow-empty -m "Using version ${NEXT_VERSION} for dependencies"

git push --all
