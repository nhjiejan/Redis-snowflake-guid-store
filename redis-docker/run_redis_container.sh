#!/bin/bash

docker run -d --name redis-container -d \
-v $(pwd)/data/:/data/ \
-p 6379:6379 \
redis redis-server