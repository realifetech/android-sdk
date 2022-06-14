#!/usr/bin/env bash

./gradlew assembleDebug;
./gradlew bundleDebug --stacktrace;
./gradlew appDistributionUploadDebug --stacktrace;
