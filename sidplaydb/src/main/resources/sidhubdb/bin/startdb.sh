#!/bin/sh

export SIDHUB_HOME=/Users/vzteufem/git/net.teufel.sidplay/sidplaydb/src/main/resources/sidhubdb/
export SIDHUB_DATA=/Users/vzteufem/Desktop/testdb

cp $SIDHUB_HOME/bin/server.properties $SIDHUB_DATA
cd $SIDHUB_DATA
java -cp $SIDHUB_HOME/lib/hsqldb-2.4.0.jar org.hsqldb.server.Server &