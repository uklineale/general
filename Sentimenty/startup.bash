#!/bin/bash

mysqld &
sleep 10
mysql -u root -e "CREATE DATABASE test_db;"
cd /home/sentimenty
gradle bootrun
