#!/bin/bash
TOKEN=$(echo "$1")
./gradlew :sdk:downloadApolloSchema -Pcom.apollographql.apollo.endpoint='https://staging-graphql-eu.realifetech.com/ca/graphql' -Pcom.apollographql.apollo.schema='src/main/graphql/com/realifetechCa/schema.json' --header="Authorization: Bearer $TOKEN"