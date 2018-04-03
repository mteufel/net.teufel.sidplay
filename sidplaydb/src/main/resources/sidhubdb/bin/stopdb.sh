#!/bin/sh

export SIDHUB_HOME=/Users/marcteufel/web-prj/net.teufel.sidhub.db/sidhubdb

java -jar $SIDHUB_HOME/lib/sqltool-2.4.0.jar --rcFile=$SIDHUB_HOME/bin/sidhubdb.rc --sql "SHUTDOWN;" sidhubdb