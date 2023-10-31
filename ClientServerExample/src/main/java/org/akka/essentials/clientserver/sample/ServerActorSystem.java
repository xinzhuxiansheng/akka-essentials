package org.akka.essentials.clientserver.sample;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ServerActorSystem {
    private LoggingAdapter log = null;
    private ActorSystem actorSystem;

    public ServerActorSystem() {
        Config config = ConfigFactory.load();
        Config serverSysConfig = config.getConfig("ServerSys");
        actorSystem = ActorSystem.create("ServerSys", serverSysConfig);
        log = Logging.getLogger(actorSystem, this);
        ActorRef actor = actorSystem.actorOf(Props.create(ServerActor.class), "serverActor");
    }

    public static void main(String[] args) {
        new ServerActorSystem();
    }
}
