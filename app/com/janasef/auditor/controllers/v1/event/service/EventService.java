package com.janasef.auditor.controllers.v1.event.service;

import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http;
import play.mvc.Http.Request;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.janasef.auditor.controllers.v1.event.domain.Event;
import com.janasef.auditor.controllers.v1.event.domain.IEventRepository;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * @author Jana Sefcikova
 *
 */
@Singleton
public class EventService {

    private final IEventRepository repository;
    private final HttpExecutionContext ec;

    @Inject
    public EventService(IEventRepository repository, HttpExecutionContext ec) {
        this.repository = repository;
        this.ec = ec;
    }

    public CompletionStage<EventPOJO> create(Http.Request request, EventPOJO resource) {
        final Event data = resource.convert();
        return repository.create(data).thenApplyAsync(savedData -> {
            return new EventPOJO(savedData);
        }, ec.current());
    }

    public CompletionStage<Optional<EventPOJO>> lookup(Http.Request request,Long id) {
        return repository.get(id).thenApplyAsync(optionalData -> {
            return optionalData.map(data -> new EventPOJO(data));
        }, ec.current());
    }
    
    public CompletionStage<Stream<EventPOJO>> find(Http.Request request) {
        return repository.list().thenApplyAsync(eventDataStream -> {
            return eventDataStream.map(data -> new EventPOJO(data));
        }, ec.current());
    }

}
