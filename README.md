Nebula [![Build Status](https://travis-ci.org/daeyun/Nebula.svg?branch=master)](https://travis-ci.org/daeyun/Nebula)
======

Nebula is a "big graph" querying service based on [Cassovary](https://github.com/twitter/cassovary/) and [Finagle](https://github.com/twitter/finagle) with a [Thrift](https://thrift.apache.org/) endpoint.

## Operations Supported

```
service NebulaService {

    list<list<i32>> getOutBoundNeighbors(1: i32 sourceNodeId, 2: i32 depth)

    list<i32> getShortestPath(1: i32 sourceNodeId, 2: i32 destinationNodeId)

    i64 getEdgeCount()

    bool hasNode(1: i32 nodeId)

}
```
