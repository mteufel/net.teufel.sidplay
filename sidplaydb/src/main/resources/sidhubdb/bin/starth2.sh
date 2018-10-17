#!/bin/sh

export SIDHUB_HOME=/home/marc/IdeaProjects/net.teufel.sidplay/sidplaydb/src/main/resources/sidhubdb/
export SIDHUB_DATA=/home/marc/sidhubdb

java -Dh2.socketConnectTimeout=100000 -cp $SIDHUB_HOME/lib/h2-1.4.197.jar org.h2.tools.Server -tcpAllowOthers -tcpPort 9101 -baseDir $SIDHUB_DATA