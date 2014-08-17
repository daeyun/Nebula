package com.daeyunshin.nebula.graph

import com.twitter.cassovary.graph.DirectedGraph

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
 * Created by daeyun on 8/14/14.
 */
class DirectedGraphOperation(graph: DirectedGraph) {
  def getEdgeCount() = {
    graph.edgeCount: Long
  }

  def hasNodeById(nodeId: Int) = {
    graph.getNodeById(nodeId) match {
      case Some(_) => true
      case None => false
    }
  }

  def unweightedShortestPath(sourceNodeId: Int, destNodeId: Int): Seq[Int] = {
    val queue = new mutable.Queue[Int]
    val parentMap = new mutable.HashMap[Int, Int]
    queue += sourceNodeId
    parentMap += (sourceNodeId -> -1)

    while (queue.nonEmpty) {
      val nodeId = queue.dequeue()

      if (nodeId == destNodeId) {
        val path = new ListBuffer[Int]
        var currNode = nodeId
        while (currNode != -1) {
          path += currNode
          currNode = parentMap.get(currNode).get
        }
        return path.reverse
      }

      val childrenIds = graph.getNodeById(nodeId).get.outboundNodes().filter { id => !parentMap.contains(id)}
      childrenIds.foreach { id => parentMap += (id -> nodeId) }
      queue ++= childrenIds
    }

    Seq[Int]()
  }

  def getOutBoundNeighbors(nodeId: Int, depth: Int) = {
    val levelNodeIds = ArrayBuffer[Seq[Int]](List(nodeId))
    levelNodeIds.sizeHint(depth)
    val visitedNodeIds = mutable.HashSet[Int](nodeId)

    for (level <- 1 to depth) {
      val currentLevelNodeIds = new ListBuffer[Int]
      for (previousLevelNodeId <- levelNodeIds(level - 1)) {
        currentLevelNodeIds ++= graph.getNodeById(previousLevelNodeId).get.outboundNodes().filter { id =>
          if (visitedNodeIds.contains(id)) {
            false
          } else {
            visitedNodeIds += id
            true
          }
        }
      }
      levelNodeIds.append(currentLevelNodeIds)
    }

    levelNodeIds
  }

}
