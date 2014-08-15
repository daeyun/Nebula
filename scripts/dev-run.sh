#!/bin/sh

SCRIPT_DIR=`dirname $0`
source "$SCRIPT_DIR/config.sh"

cd $SERVICE_ROOT 
./sbt one-jar
cd -

MAIN_JAR=$SERVICE_ROOT"target/scala-2.10/nebula_2.10-*-one-jar.jar"
chmod 755 $MAIN_JAR

java -server -Xmx10240m -jar $MAIN_JAR \
    -admin.port=':9990' \
    -service.port='42001' \
    -graph.path='/resources/nebula/graph_files/' \
    -graph.prefix='graph-' \
    -log.level='INFO' \
    -log.output='/dev/stderr' \
    -service.name='NebulaService' \
    $@
