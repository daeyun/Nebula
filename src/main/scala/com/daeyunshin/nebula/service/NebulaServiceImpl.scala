package com.daeyunshin.nebula.service

import com.daeyunshin.nebula.graph.DirectedGraphOperation
import com.twitter.util.Future

/**
 * Created by daeyun on 8/14/14.
 */
class NebulaServiceImpl(graphOperation: DirectedGraphOperation) extends NebulaService.FutureIface {
  override def hasNode(nodeId: Int): Future[Boolean] = {
    Future.value(graphOperation.hasNodeById(nodeId))
  }

  override def getEdgeCount(): Future[Long] = {
    Future.value(graphOperation.getEdgeCount())
  }

  override def getShortestPath(sourceNodeId: Int, destinationNodeId: Int): Future[Seq[Int]] = {
    Future.value(graphOperation.unweightedShortestPath(sourceNodeId, destinationNodeId))
  }

  override def getOutBoundNeighbors(sourceNodeId: Int, depth: Int): Future[Seq[Seq[Int]]] = {
    Future.value(graphOperation.getOutBoundNeighbors(sourceNodeId, depth))
  }
}
