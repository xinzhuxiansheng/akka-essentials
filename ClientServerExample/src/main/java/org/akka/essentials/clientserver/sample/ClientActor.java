package org.akka.essentials.clientserver.sample;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ClientActor extends AbstractActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(),this);
    private final ActorSelection actorSelection;

    public ClientActor(ActorSelection actorSelection) {
        this.actorSelection = actorSelection;
    }

    public static Props props(ActorSelection actorSelection) {
        return Props.create(ClientActor.class, () -> new ClientActor(actorSelection));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> {
                    if (message.startsWith("Start")) {
                        log.info("Sending message to server - message# Hi there");
                        actorSelection.tell("Hi there", getSelf());
                    } else {
                        log.info("Message received from Server -> " + message);
                    }
                })
                .build();
    }
}
