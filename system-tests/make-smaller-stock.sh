#!/bin/bash

# Stop on first error.
set -e;

### A script to make a smaller stock by passing in aacodes
test -n $1 || exit 1;

AA_CODES="$@";
AA_CODES="\"${AA_CODES// /\", \"}\"";
QUERY="select(.basicAttributes.aacode == (${AA_CODES}))"
echo "Making stock reduced.stock.gz using query ${QUERY}";

zcat EHS2012.json.gz | jq "${QUERY}" | tr '\n' ' ' > reduced.stock;
gzip reduced.stock -f -k;
