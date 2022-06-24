#!/usr/bin/env bash

if [[ "${SDK_CHANGED}" == "changed"  || "${RELEASE_CREATED}" == "created" || $1 == "master" ]]; then
  ./gradlew -D sdk.prerelease="$VERSION_SUFFIX"
  ./gradlew sdk:increment"$VERSION_TARGET"
  ./gradlew sdk:assembleRelease
else
  echo "its just sample app change, nothing to publish mate, we are done here."
fi