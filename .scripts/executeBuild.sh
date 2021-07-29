#!/usr/bin/env bash

if [[  "${CORE_CHANGED}" == "changed" || "${SDK_CHANGED}" == "changed"  || "${RELEASE_CREATED}" == "created" || $1 == "master" ]]; then
if [[ "${CORE_CHANGED}" == "changed" ]]; then
  ./gradlew -D core-sdk.prerelease="$VERSION_SUFFIX"
  ./gradlew core-sdk:increment"$VERSION_TARGET"
  ./gradlew core-sdk:assembleRelease
  else
  ./gradlew -D sdk.prerelease="$VERSION_SUFFIX"
  ./gradlew sdk:increment"$VERSION_TARGET"
  ./gradlew sdk:assembleRelease
  fi
else
  echo "its just sample app change, nothing to publish mate, we are done here."
fi