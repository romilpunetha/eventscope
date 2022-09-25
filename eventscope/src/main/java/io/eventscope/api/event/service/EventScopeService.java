package io.eventscope.api.event.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eventscope.api.event.model.Event;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Instant;

@ApplicationScoped
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventScopeService {

    @Inject
    @Channel("events")
    Emitter<String> eventEmitter;

    @Inject
    ObjectMapper objectMapper;

    public Uni<Void> injectEvent(Event event) throws JsonProcessingException {

        event.setCreatedAt(Instant.now());
        if (ObjectUtils.isEmpty(event.getTimestamp()))
            event.setTimestamp(Instant.now());
        String eventString = objectMapper.writeValueAsString(event);
        Log.debug("Event String : " + eventString);
        return Uni.createFrom().completionStage(() -> eventEmitter.send(eventString));
    }
}
