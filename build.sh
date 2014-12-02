#!/usr/bin/env bash

set -eu

TIMESTAMP=$(git --no-pager log -1 --format=%at)

case $OSTYPE in
  linux-gnu)
    QUALIFIER=$(date -u -d @$TIMESTAMP +%Y%m%d-%H%M%S)
    ;;
  darwin*)
    QUALIFIER=$(date -u -r $TIMESTAMP +%Y%m%d-%H%M%S)
    ;;
  *)
    echo "Unsupported platform: $OSTYPE" >&2
    exit 1
    ;;
esac

echo "Using Eclipse qualifier '$QUALIFIER'."

mvn clean verify -DforceContextQualifier=$QUALIFIER
