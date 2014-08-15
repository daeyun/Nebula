#!/bin/sh

SCRIPT_DIR=`dirname $0`
source "$SCRIPT_DIR/config.sh"

cd $SERVICE_ROOT 
./sbt one-jar
cd -

chmod 755 $MAIN_JAR
MAIN_JAR=$SERVICE_ROOT"target/scala-2.10/nebula_2.10-*-one-jar.jar"

GC_OPTS="-XX:+UseParallelOldGC -XX:+UseAdaptiveSizePolicy -XX:MaxGCPauseMillis=1000 -XX:GCTimeRatio=99"
JAVA_OPTS="-XX:+DisableExplicitGC -XX:+UseNUMA $GC_OPTS"

java -server -Xmx10240m $JAVA_OPTS -jar $MAIN_JAR \
    -admin.port=':9990' \
    -service.port='42001' \
    -graph.path='/resources/nebula/graph_files/' \
    -graph.prefix='graph-' \
    -log.level='WARN' \
    -log.output='/var/log/nebula/log' \
    -service.name='NebulaService' \
    $@
