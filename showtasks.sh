#!/usr/bin/env bash

if ./runcrud.sh; then
    open http://localhost:8080/crud/v/v1/task/getTasks
else
    echo "Website could't be open."
fi