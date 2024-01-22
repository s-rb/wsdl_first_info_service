#!/bin/bash

apt-get update && apt-get install -y wget && apt-get install -y unzip

wget http://archive.apache.org/dist/servicemix/servicemix-5/5.4.1/apache-servicemix-5.4.1.zip && \
unzip apache-servicemix-5.4.1.zip && \
cd apache-servicemix-5.4.1 && \
./bin/servicemix server

sleep 120

INSTALL_OUTPUT=$(./bin/client osgi:install file:/app.jar)
BUNDLE_ID=$(echo $INSTALL_OUTPUT | grep -o 'Bundle ID: [0-9]*' | cut -d' ' -f3)
./bin/client osgi:start $BUNDLE_ID