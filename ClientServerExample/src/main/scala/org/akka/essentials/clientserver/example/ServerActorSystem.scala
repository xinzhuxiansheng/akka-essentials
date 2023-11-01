package org.akka.essentials.clientserver.example

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

object ServerActorSystem {
  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load().getConfig("ServerSys")
    val actorSystem = ActorSystem("ServerSys",config)
    val serverActor = actorSystem.actorOf(Props[ServerActor],name = "serverActor")
  }

}
