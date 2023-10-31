package org.akka.essentials.clientserver.sample;

import akka.actor.AbstractActor;
import akka.actor.PoisonPill;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ServerActor extends AbstractActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(),this);
    private static int instanceCounter = 0;


    @Override
    public void preStart() throws Exception, Exception {
        instanceCounter ++;
        log.info("Starting ServerActor instance #" + instanceCounter
                + ", hashcode #" + this.hashCode());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> {
                    getSender().tell(message + " got something", getSelf());
                })
                .matchEquals(PoisonPill.getInstance(), poisonPill -> {
                    getContext().getSystem().terminate();
                })
                .build();
    }

    @Override
    public void postStop() {
        log.info("Stoping ServerActor instance #" + instanceCounter
                + ", hashcode #" + this.hashCode());
        instanceCounter--;
    }
}
