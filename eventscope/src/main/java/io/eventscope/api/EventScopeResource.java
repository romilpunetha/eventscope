package io.eventscope.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/api/v1/events")
@ApplicationScoped
public class EventScopeResource {

    @Inject
    EventScopeService service;

    @POST
    public Uni<Void> injectEvent(Event event) throws JsonProcessingException {
        return service.injectEvent(event);
    }

}
