#!/bin/sh

export SIDHUB_HOME=/Users/vzteufem/git/net.teufel.sidplay/sidplaydb/src/main/resources/sidhubdb/
export SIDHUB_DATA=/Users/vzteufem/Desktop/testdb

java -Dh2.socketConnectTimeout=100000 -cp $SIDHUB_HOME/lib/h2-1.4.197.jar org.h2.tools.Server -tcpAllowOthers -tcpPort 9101 -baseDir $SIDHUB_DATA