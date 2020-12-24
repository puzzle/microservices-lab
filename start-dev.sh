#!/bin/bash

echo starting hugo-dev on http://localhost:8095
docker run --rm --interactive --name hugo-dev --publish 8095:8080 -v $(pwd):/opt/app/src -w /opt/app/src acend/hugo:0.76.5 hugo server -p 8080 --bind 0.0.0.0
