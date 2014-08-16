package com.daeyunshin.nebula

import com.twitter.util.Await
import org.scalatest.{BeforeAndAfterAll, GivenWhenThen}

/**
 * Created by daeyun on 8/14/14.
 */
class NebulaServiceSpec extends NebulaServiceSpecBase with GivenWhenThen with BeforeAndAfterAll {

  "hasNode operation" should "return true" in {
    Given("an existing node id")
    assert(Await.result(client.hasNode(1)))
  }

  "Number of edges in adj_1.txt" should "be 16" in {
    assert(Await.result(client.getEdgeCount()) == 16)
  }

  "outbound neighbors" should "work starting from a source node" in {
    val neighbors = Seq(Seq(1), Seq(2), Seq(3, 4, 5), Seq(6), Seq(7), Seq(), Seq())
    (0 to 6).foreach { level =>
      Given("depth %d" format level)
      assert(Await.result(client.getOutBoundNeighbors(1, level)).equals(neighbors.take(level + 1)))
    }
  }

  override def beforeAll() = {
    startThriftServer()
  }

  override def afterAll() = {
    stopThriftServer()
  }
}
