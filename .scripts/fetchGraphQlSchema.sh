#!/bin/bash
TOKEN=$(echo "$1")
./gradlew :sdk:downloadApolloSchema -Pcom.apollographql.apollo.endpoint='https://staging-graphql-eu.realifetech.com/' -Pcom.apollographql.apollo.schema='src/main/graphql/com/realifetech/schema.json' --header="Authorization: Bearer NGZjZjViZDE2YTk5MjU1YzJlMzk3MDYxYzhmZjc4MzBlMDQxYzg2MjYwMTNmYTAwY2IyMzI4NzlkNWI3YzEzNw"