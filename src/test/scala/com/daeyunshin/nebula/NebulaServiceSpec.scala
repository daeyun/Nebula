package com.daeyunshin.nebula

import com.twitter.util.Await
import org.scalatest.{BeforeAndAfterAll, GivenWhenThen}

/**
 * Created by daeyun on 8/14/14.
 */
class NebulaServiceSpec extends NebulaServiceSpecBase with GivenWhenThen with BeforeAndAfterAll {

  "hasNode operation" should "return true" in {
    Given("an existing node id")
    val client = getThriftClient()
    assert(Await.result(client.hasNode(1)))
  }

  "Number of edges in adj_1.txt" should "be 16" in {
    val client = getThriftClient()
    assert(Await.result(client.getEdgeCount()) == 16)
  }

  "outbound neighbors" should "work starting from a source node" in {
    val client = getThriftClient()

    Given("depth 0")
    assert(Await.result(client.getOutBoundNeighbors(1, 0)).equals(Seq(Seq(1))))
    Given("depth 1")
    assert(Await.result(client.getOutBoundNeighbors(1, 1)).equals(Seq(Seq(1), Seq(2))))
    Given("depth 2")
    assert(Await.result(client.getOutBoundNeighbors(1, 2)).equals(Seq(Seq(1), Seq(2), Seq(3, 4, 5))))
    Given("depth 3")
    assert(Await.result(client.getOutBoundNeighbors(1, 3)).equals(Seq(Seq(1), Seq(2), Seq(3, 4, 5), Seq(6))))
    Given("depth 4")
    assert(Await.result(client.getOutBoundNeighbors(1, 4)).equals(Seq(Seq(1), Seq(2), Seq(3, 4, 5), Seq(6), Seq(7))))
    Given("depth 5")
    assert(Await.result(client.getOutBoundNeighbors(1, 5)).equals(Seq(Seq(1), Seq(2), Seq(3, 4, 5), Seq(6), Seq(7), Seq())))
  }

  override def beforeAll() = {
    startThriftServer()
  }

  override def afterAll() = {
    stopThriftServer()
  }
}
