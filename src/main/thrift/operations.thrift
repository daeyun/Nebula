namespace java com.daeyunshin.nebula.service
#@namespace scala com.daeyunshin.nebula.service

service NebulaService {
    list<i32> getShortestPath(1: i32 sourceNodeId, 2: i32 destinationNodeId)

    list<list<i32>> getOutBoundNeighbors(1: i32 sourceNodeId, 2: i32 depth)

    i64 getEdgeCount()

    bool hasNode(1: i32 nodeId)
}