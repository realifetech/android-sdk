#!/usr/bin/env bash
 if [[ $1 == "develop" ]]; then
    echo "export VERSION_TARGET=Patch" >> "$BASH_ENV"
    echo "export VERSION_SUFFIX=alpha" >> "$BASH_ENV"
elif [[ $1 == "master" ]]; then
    echo "export VERSION_TARGET=Major" >> "$BASH_ENV"
    echo "export VERSION_SUFFIX=" >> "$BASH_ENV"
elif [[ $1 =~ ^release\/.+$ || $1 =~ /^hotfix\/.+$/ ]]; then
    echo "export VERSION_TARGET=Minor " >> "$BASH_ENV"
    echo "export VERSION_SUFFIX=beta" >> "$BASH_ENV"
  fi
echo "export SDK_CHANGED=$(git diff --quiet HEAD "$( git rev-parse --short @~)" -- sdk || echo changed)" >> "$BASH_ENV";
echo "export CORE_CHANGED=$(git diff --quiet HEAD "$( git rev-parse --short @~)" -- core-sdk || echo changed)" >> "$BASH_ENV";
if [[ $1 =~ ^release\/.+$ ]]; then
  echo "export RELEASE_CREATED=$([ -z "$( git cherry -v develop )" ] && echo "created")" >> "$BASH_ENV";
  fi
source $BASH_ENV
echo "$CORE_CHANGED"
echo "$SDK_CHANGED"
echo "$VERSION_TARGET"
echo "$VERSION_SUFFIX"
echo "$1"