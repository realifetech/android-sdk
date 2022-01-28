#!/bin/bash
TOKEN=$(echo "$1")
./gradlew :sdk:downloadApolloSchema -Pcom.apollographql.apollo.endpoint='https://staging-graphql-eu.realifetech.com/' -Pcom.apollographql.apollo.schema='src/main/graphql/com/realifetech/core/schema.json' --header="Authorization: Bearer $TOKEN"