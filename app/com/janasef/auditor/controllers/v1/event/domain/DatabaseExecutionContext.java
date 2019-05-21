package com.janasef.auditor.controllers.v1.event.domain;

import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;

import akka.actor.ActorSystem;

/**
 * Custom execution context wired to "database.repository" thread pool.
 * See settings in application.conf database.repository configuration.
 */
public class DatabaseExecutionContext extends CustomExecutionContext {
    private static final String NAME = "database.repository";

    @Inject
    public DatabaseExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, NAME);
    }
}
