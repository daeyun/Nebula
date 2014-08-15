package com.daeyunshin.nebula

import java.net.InetSocketAddress
import java.util.concurrent.Executors

import com.daeyunshin.nebula.graph.DirectedGraphOperation
import com.daeyunshin.nebula.service.{NebulaService, NebulaServiceImpl}
import com.twitter.cassovary.util.io.AdjacencyListGraphReader
import com.twitter.finagle.Thrift
import com.twitter.finagle.builder.{Server, ServerBuilder}
import com.twitter.finagle.thrift.ThriftServerFramedCodec
import org.apache.thrift.protocol.TBinaryProtocol
import org.scalatest.FlatSpec

/**
 * Created by daeyun on 8/14/14.
 */
class NebulaServiceSpecBase extends FlatSpec {
  val SampleGraphPath = "src/test/resources"
  val SampleGraphFilePrefix = "adj_1"
  val ThriftPort = 42101
  val ThriftServiceName = "NebulaServiceSpec"

  var server: Server = null

  def startThriftServer() {
    val graph = AdjacencyListGraphReader.forIntIds(
      SampleGraphPath,
      SampleGraphFilePrefix,
      Executors.newFixedThreadPool(1)
    ).toArrayBasedDirectedGraph()

    val graphOperation = new DirectedGraphOperation(graph)

    // Finagled Thrift service
    val service = new NebulaService.FinagledService(
      new NebulaServiceImpl(graphOperation),
      new TBinaryProtocol.Factory()
    )

    server = ServerBuilder()
      .bindTo(new InetSocketAddress(ThriftPort))
      .codec(ThriftServerFramedCodec())
      .name(ThriftServiceName)
      .build(service)
  }

  def stopThriftServer(): Unit = {
    try {
      server.close()
    } catch {
      case _ =>
    }
  }

  def getThriftClient() = {
    Thrift.newIface[NebulaService.FutureIface]("localhost:%d" format ThriftPort)
  }
}
