#!/bin/sh

THIS_VERSION=$1
NEXT_VERSION=$2

# release script for the implementation bundle
# this doesn't use tag based versioning, because its version is kind of determined by the dependency.

git pull

sed -i -r -e "s:ext\\.nhmversion\\s*=.+$:ext.nhmversion = '${THIS_VERSION}':g" build.gradle
sed -i -r -e "s:ext\\.nhmstockversion\\s*=.+$:ext.nhmstockversion = '${THIS_VERSION}':g" build.gradle

./gradlew clean publish -PmvnUser=deploy -PmvnPassword=10013848146b42a0b9b09eb55aae208a830ff37c41e9498a6d7c169c7f017bb1 --refresh-dependencies

git add build.gradle
git commit  --allow-empty -m "Releasing version ${THIS_VERSION}"

sed -i -r -e "s:ext\\.nhmversion\\s*=.+$:ext.nhmversion = '${NEXT_VERSION}-SNAPSHOT':g" build.gradle
sed -i -r -e "s:ext\\.nhmstockversion\\s*=.+$:ext.nhmstockversion = '${NEXT_VERSION}-SNAPSHOT':g" build.gradle

git add build.gradle
git commit --allow-empty -m "Using version ${NEXT_VERSION} for dependencies"

git push --all
