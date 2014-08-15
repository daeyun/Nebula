package com.daeyunshin.nebula

import java.net.InetSocketAddress
import java.util.concurrent.Executors

import com.daeyunshin.nebula.graph.DirectedGraphOperation
import com.daeyunshin.nebula.service.{NebulaService, NebulaServiceImpl}
import com.twitter.cassovary.util.io.AdjacencyListGraphReader
import com.twitter.finagle.builder.{Server, ServerBuilder}
import com.twitter.finagle.thrift.ThriftServerFramedCodec
import com.twitter.server.TwitterServer
import com.twitter.util.Await
import org.apache.thrift.protocol.TBinaryProtocol

/**
 * Created by daeyun on 8/14/14.
 */
object Main extends TwitterServer {
  // Flags
  val thriftPort = flag("service.port", DefaultThriftPort, "Thrift service port")
  val serviceName = flag("service.name", "NebulaService", "Service name")
  val graphPath = flag("graph.path", "/nebula/graph_files/", "Path to the graph files directory")
  val graphFilePrefix = flag("graph.prefix", "graph-", "Graph file prefix")

  // Constants
  val DefaultThriftPort = 42001
  val GraphReaderThreadCount = 4

  def main() {
    val graph = AdjacencyListGraphReader.forIntIds(
      graphPath(),
      graphFilePrefix(),
      Executors.newFixedThreadPool(GraphReaderThreadCount)
    ).toArrayBasedDirectedGraph()

    val graphOperation = new DirectedGraphOperation(graph)

    // Finagled Thrift service
    val service = new NebulaService.FinagledService(
      new NebulaServiceImpl(graphOperation),
      new TBinaryProtocol.Factory()
    )

    val thriftServer: Server = ServerBuilder()
      .bindTo(new InetSocketAddress("127.0.0.1", thriftPort()))
      .codec(ThriftServerFramedCodec())
      .name(serviceName())
      .build(service)

    Await.ready(adminHttpServer)

    log.info("Admin server is running at %s" format adminHttpServer.boundAddress.toString)
    log.info("Thrift server is running at %s" format thriftServer.localAddress.toString)
  }
}
