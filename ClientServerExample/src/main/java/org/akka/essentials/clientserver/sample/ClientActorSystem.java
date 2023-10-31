package org.akka.essentials.clientserver.sample;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ClientActorSystem {
    private LoggingAdapter log = null;
    private ActorSystem actorSystem;
    private ActorRef actorRef;
    private ActorRef remoteActorRef;

    public ClientActorSystem() {
        Config config = ConfigFactory.load();
        Config clientSysConfig = config.getConfig("ClientSys");
        actorSystem = ActorSystem.create("ClientSys", clientSysConfig);
        log = Logging.getLogger(actorSystem, this);
    }

    public void remoteActorRefDemo() {
        log.info("Creating a reference to remote actor");
        /*
            通过已知 path，使用actorSelection获取 remoteActorRef
         */
        ActorSelection actorSelection = actorSystem.actorSelection("akka://ServerSys@127.0.0.1:2552/user/serverActor");

        log.info("ServerActor with hashcode #" + actorSelection.hashCode());

        /*
            创建 local actor，并将 remoteActorRef 传递过去
         */
        actorRef = actorSystem.actorOf(ClientActor.props(actorSelection));

        // 给 local actor 发送一条消息
        actorRef.tell("Start-RemoteActorRef",ActorRef.noSender());
    }

    public static void main(String[] args) {
        ClientActorSystem clientActorSystem = new ClientActorSystem();
        clientActorSystem.remoteActorRefDemo();
    }
}
