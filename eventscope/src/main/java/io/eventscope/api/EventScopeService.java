package io.eventscope.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import java.time.Instant;

public class EventScopeService {

    @Inject
    @Channel("events")
    Emitter<String> eventEmitter;

    @Inject
    ObjectMapper objectMapper;

    public Uni<Void> injectEvent(Event event) throws JsonProcessingException {

        event.setCreatedAt(Instant.now());
        String eventString = objectMapper.writeValueAsString(event);
        Log.debug("Event String : " + eventString);
        return Uni.createFrom().completionStage(() -> eventEmitter.send(eventString));
    }
}
