package com.daeyunshin.nebula

import java.net.InetSocketAddress
import java.util.concurrent.Executors

import com.daeyunshin.nebula.graph.DirectedGraphOperation
import com.daeyunshin.nebula.service.{NebulaService, NebulaServiceImpl}
import com.daeyunshin.nebula.utils.PortFinder
import com.twitter.cassovary.util.io.AdjacencyListGraphReader
import com.twitter.finagle.Thrift
import com.twitter.finagle.builder.{Server, ServerBuilder}
import com.twitter.finagle.thrift.ThriftServerFramedCodec
import org.apache.thrift.protocol.TBinaryProtocol
import org.scalatest.FlatSpec

import scala.util.control.Breaks._

/**
 * Created by daeyun on 8/14/14.
 */
class NebulaServiceSpecBase extends FlatSpec {
  val SampleGraphPath = "src/test/resources"
  val SampleGraphFilePrefix = "adj_1"
  val ThriftServiceName = "NebulaServiceSpec"

  var server: Server = null
  var port: Int = 0

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

    breakable {
      for (i <- 1 to 5) {
        port = PortFinder.findFreePort()

        try {
          server = ServerBuilder()
            .bindTo(new InetSocketAddress(port))
            .codec(ThriftServerFramedCodec())
            .name(ThriftServiceName)
            .build(service)

          info("Successfully started a thrift server at %s" format server.localAddress.toString())
          break
        } catch {
          case e: Exception =>
            alert("Address %s is not available. Retrying.." format server.localAddress.toString())
            e.printStackTrace()
        }
      }
    }
  }

  def stopThriftServer(): Unit = {
    try {
      server.close()
    } catch {
      case _: Exception =>
    }
  }

  def getThriftClient() = {
    Thrift.newIface[NebulaService.FutureIface]("localhost:%d" format port)
  }
}
