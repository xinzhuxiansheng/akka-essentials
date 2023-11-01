package org.akka.essentials.clientserver.sample;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.remote.RemoteScope;
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

    /**
     * 该方法演示了 通过actorSelection方式获取 actorRef 来访问远程的actor
     */
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
        actorRef.tell("Start-RemoteActorRef", ActorRef.noSender());
    }

    public void remoteActorCreationDemo2() {
        log.info("Creating a actor with remote deployment");

        // alternate way to create the address object that points to the remote
        // server
        Address addr = AddressFromURIString
                .parse("akka://ServerSys@127.0.0.1:2552");

        // creating the ServerActor on the specified remote server
        final ActorRef serverActor = actorSystem.actorOf(Props.create(ServerActor.class)
                .withDeploy(new Deploy(new RemoteScope(addr))));

        // create a local actor and pass the reference of the remote actor
        actorRef = actorSystem.actorOf(ClientActor.props(serverActor));
        // send a message to the local client actor
        actorRef.tell("Start-RemoteActorCreationDemo2", ActorRef.noSender());
    }

    public void remoteActorCreationDemo3() {
        log.info("Creating a actor with remote deployment");

        // creating the ServerActor on the specified remote server
        final ActorRef serverActor = actorSystem.actorOf(Props.create(ServerActor.class), "remoteServerActor");

        // create a local actor and pass the reference of the remote actor
        actorRef = actorSystem.actorOf(ClientActor.props(serverActor));
        // send a message to the local client actor
        actorRef.tell("Start-RemoteActorCreationDemo3", ActorRef.noSender());
    }


    public static void main(String[] args) {
        ClientActorSystem clientActorSystem = new ClientActorSystem();
        // clientActorSystem.remoteActorRefDemo();
        clientActorSystem.remoteActorCreationDemo2();
    }
}
