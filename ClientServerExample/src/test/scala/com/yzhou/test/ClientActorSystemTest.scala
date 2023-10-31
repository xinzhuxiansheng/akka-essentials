package com.yzhou.test

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import org.akka.essentials.clientserver.sample.ClientActorSystem
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class ClientActorSystemTest extends TestKit(ActorSystem("TestSys"))
  with ImplicitSender
  with AnyWordSpecLike
  with Matchers
  with BeforeAndAfterAll {

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "A ClientActorSystem" should {
    "create a remote actor reference and send a message" in {
      val clientActorSystem = new ClientActorSystem()
      clientActorSystem.remoteActorRefDemo()

      // Here, you can add assertions based on your expectations.
      // For example, if you expect a message back from the actor, you can use:
      // expectMsg("ExpectedMessage")
    }
  }
}
