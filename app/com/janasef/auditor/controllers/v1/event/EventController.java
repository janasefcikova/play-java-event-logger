package com.janasef.auditor.controllers.v1.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.janasef.auditor.controllers.v1.event.service.EventPOJO;
import com.janasef.auditor.controllers.v1.event.service.EventService;

import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class EventController extends Controller {

    private HttpExecutionContext ec;
    private EventService eventService;

    @Inject
    public EventController(HttpExecutionContext ec, EventService eventService) {
        this.ec = ec;
        this.eventService = eventService;
    }
    
    public CompletionStage<Result> create(Http.Request request) {
        JsonNode json = request.body().asJson();
        final EventPOJO resource = Json.fromJson(json, EventPOJO.class);
        return eventService.create(request, resource).thenApplyAsync(savedResource -> {
            return created(Json.toJson(savedResource));
        }, ec.current());
    }

    public CompletionStage<Result> show(Http.Request request, Long id) {
        return eventService.lookup(request, id).thenApplyAsync(optionalResource -> {
            return optionalResource.map(resource ->
                ok(Json.toJson(resource))
            ).orElseGet(Results::notFound);
        }, ec.current());
    }
    
    public CompletionStage<Result> list(Http.Request request) {
        return eventService.find(request).thenApplyAsync(events -> {
            final List<EventPOJO> eventList = events.collect(Collectors.toList());
            return ok(Json.toJson(eventList));
        }, ec.current());
    }   
    
}
