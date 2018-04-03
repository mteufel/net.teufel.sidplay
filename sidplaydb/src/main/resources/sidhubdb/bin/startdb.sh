#!/bin/sh

export SIDHUB_HOME=/Users/marcteufel/web-prj/net.teufel.sidhub.db/sidhubdb
export SIDHUB_DATA=/Users/marcteufel/Desktop/testdb

cp $SIDHUB_HOME/bin/server.properties $SIDHUB_DATA
cd $SIDHUB_DATA
java -cp $SIDHUB_HOME/lib/hsqldb-2.4.0.jar org.hsqldb.server.Server &