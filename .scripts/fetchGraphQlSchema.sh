#!/bin/bash
TOKEN=$(echo "$1")
./gradlew :sdk:downloadApolloSchema -Pcom.apollographql.apollo.endpoint='https://staging-graphql-eu.realifetech.com/' -Pcom.apollographql.apollo.schema='src/main/graphql/com/realifetech/schema.json' --header="Authorization: Bearer ZDkxYmY1MjgxYjM3MWQ0ZDY4YjNkMTI5NTRiY2YwYTU5M2RmZmQ4ZGU2OWZjMjIzNjNhMjU5MzA0MGExZmQ2Zg"