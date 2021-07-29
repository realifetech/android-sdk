#!/usr/bin/env bash
if [[  "${CORE_CHANGED}" == "changed" || "${SDK_CHANGED}" == "changed"  || "${RELEASE_CREATED}" == "created" || $1 == "master" ]]; then
  if [[ $1 == "master" ]]; then
    tag_version=$(./gradlew -q echoSdkVersion)
    echo "$tag_version"
    git tag $tag_version
    git describe --abbrev=0 --tags
    git push origin --tags
    curl -v -i -X POST -H "Content-Type:application/json" \
      -H "Authorization: token ${GITHUB_TOKEN}" \
      https://api.github.com/repos/realifetech/android-sdk/releases \
      -d "{\"tag_name\":\"$tag_version\",\"target_commitish\": \"master\",\"name\": \"$tag_version\",\"body\": \"Description of the release\",\"draft\": false,\"prerelease\": false}"
  fi
  if [[ "${CORE_CHANGED}" == "changed" ]]; then
    ./gradlew core-sdk:publishLibraryPublicationToGitHubPackagesRepository
    ./gradlew -D sdk.prerelease="$VERSION_SUFFIX"
    ./gradlew sdk:increment"$VERSION_TARGET"
    ./gradlew sdk:assembleRelease
  fi
  ./gradlew sdk:publishLibraryPublicationToGitHubPackagesRepository
  ./.scripts/commitAndPush.sh
else
  echo "its just sample app change, nothing to publish mate, we are done here."
fi
