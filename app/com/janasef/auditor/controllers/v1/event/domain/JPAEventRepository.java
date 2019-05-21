package com.janasef.auditor.controllers.v1.event.domain;

import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.Failsafe;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;


@Singleton
public class JPAEventRepository implements IEventRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext ec;
    private final CircuitBreaker circuitBreaker = new CircuitBreaker().withFailureThreshold(1).withSuccessThreshold(3);

    @Inject
    public JPAEventRepository(JPAApi api, DatabaseExecutionContext ec) {
        this.jpaApi = api;
        this.ec = ec;
    }

    @Override
    public CompletionStage<Event> create(Event eventData) {
        return supplyAsync(() -> wrap(em -> insert(em, eventData)), ec);
    }

    @Override
    public CompletionStage<Optional<Event>> get(Long id) {
       return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(() -> find(em, id))), ec);
    }
    
    @Override
    public CompletionStage<Stream<Event>> list() {
        return supplyAsync(() -> wrap(em -> select(em, "SELECT e FROM Event e")), ec);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Optional<Event> find(EntityManager em, Long id) throws SQLException {
        return Optional.ofNullable(em.find(Event.class, id));
    }

    private Stream<Event> select(EntityManager em, String queryStr) {
        TypedQuery<Event> query = em.createQuery(queryStr, Event.class);
        return query.getResultList().stream();
    }

    private Event insert(EntityManager em, Event eventData) {
        return em.merge(eventData);
    }
}
