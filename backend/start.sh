#!/bin/bash

export $(cat .env | grep -v '#' | xargs)
./mvnw spring-boot:run