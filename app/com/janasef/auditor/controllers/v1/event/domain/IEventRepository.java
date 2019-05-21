package com.janasef.auditor.controllers.v1.event.domain;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

import com.google.inject.ImplementedBy;

@ImplementedBy(JPAEventRepository.class)
public interface IEventRepository {

    CompletionStage<Stream<Event>> list();

    CompletionStage<Event> create(Event eventData);

    CompletionStage<Optional<Event>> get(Long id);

}

