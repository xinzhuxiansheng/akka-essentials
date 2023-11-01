package org.akka.essentials.clientserver.example

import akka.actor.Actor

class ServerActor extends Actor {
  override def receive: Receive = {
    case message: String =>
      sender() ! (message + " got something")
  }
}
