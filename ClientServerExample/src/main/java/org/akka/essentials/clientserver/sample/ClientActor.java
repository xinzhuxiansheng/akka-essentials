package org.akka.essentials.clientserver.sample;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ClientActor extends AbstractActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(),this);
    private ActorSelection actorSelection;
    private ActorRef remote;

    public ClientActor(ActorSelection actorSelection) {
        this.actorSelection = actorSelection;
    }
    public ClientActor(ActorRef actor) {
        this.remote = actor;
    }

    public static Props props(ActorSelection actorSelection) {
        return Props.create(ClientActor.class, () -> new ClientActor(actorSelection));
    }

    public static Props props(ActorRef actor) {
        return Props.create(ClientActor.class, () -> new ClientActor(actor));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> {
                    if (message.startsWith("Start")) {
                        log.info("Sending message to server - message# Hi there");
                        if(actorSelection!=null){
                            actorSelection.tell("Hi there", getSelf());
                        }
                        if(remote!=null){
                            remote.tell("Hi there", getSelf());
                        }
                    } else {
                        log.info("Message received from Server -> " + message);
                    }
                })
                .build();
    }
}
