#!/usr/bin/env bash

if [[  "${CORE_CHANGED}" == "changed" || "${SDK_CHANGED}" == "changed"  ]]; then
if [[ "${CORE_CHANGED}" == "changed" ]]; then
  ./gradlew -D core-sdk.prerelease="$VERSION_SUFFIX"
  ./gradlew core-sdk:increment"$VERSION_TARGET"
  ./gradlew core-sdk:assembleRelease
  fi
  ./gradlew -D sdk.prerelease="$VERSION_SUFFIX"
  ./gradlew sdk:increment"$VERSION_TARGET"
  ./gradlew sdk:assembleRelease
else
  echo "its just sample app change, nothing to publish mate, we are done here."
fi