#!/usr/bin/env bash
git config user.email "$GITHUB_EMAIL"
git config user.name "$GITHUB_NAME"
echo -e $(git 'status')
git commit --allow-empty -am "Automatic commit from CircleCI [skip ci]"
git push origin $(git rev-parse --abbrev-ref HEAD)