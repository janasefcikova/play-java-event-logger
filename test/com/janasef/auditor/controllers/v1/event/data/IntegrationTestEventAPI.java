package com.janasef.auditor.controllers.v1.event.data;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.janasef.auditor.controllers.v1.event.domain.Event;
import com.janasef.auditor.controllers.v1.event.domain.IEventRepository;
import com.janasef.auditor.controllers.v1.event.service.EventPOJO;

import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static play.test.Helpers.*;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Integration test for event API specified in event.routes.
 * 
 * @author Jana Sefcikova
 *
 */
public class IntegrationTestEventAPI extends WithApplication {
	IEventRepository repository;
	
    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }
    
    private static Event createEventEntity() {
    	Event e = new Event();
    	e.userId = 787L;
    	e.timestamp = new Date(java.lang.System.currentTimeMillis());
    	e.operation = "HTTP GET";
    	e.userRole = "Admin";
    	e.longText = "Long text"; 
		return e;
    }
    
    @Before
    public void setup() {
       repository = app.injector().instanceOf(IEventRepository.class);
    }
    
    /**
     * Test POST /v1/event creates new event.
     */
    @Test
    public void testCreateEvent() throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
        Event data = createEventEntity();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(data, JsonNode.class);
        
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/v1/event")
                .bodyJson(node);
        
        Result result = route(app, request);
        final String body = contentAsString(result);
        ObjectMapper objectMapper = new ObjectMapper();
        EventPOJO pojo = objectMapper.readValue(body, EventPOJO.class);
        assertNotNull(pojo.getId());
        assertThat(pojo.getUserId(), is(data.userId));
        assertThat(pojo.getTimestamp(), is(data.timestamp));
        assertThat(pojo.getOperation(), is(data.operation));
        assertThat(pojo.getLongText(), is(data.longText));
    }
    
    /**
     * Test GET /v1/event/:id for already created event
     */
    @Test
    public void testGetEvent() throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
        Event data = createEventEntity();
        data = repository.create(data).toCompletableFuture().get();

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/v1/event/" + data.id); 

        Result result = route(app, request);
        final String body = contentAsString(result);
        ObjectMapper objectMapper = new ObjectMapper();
        EventPOJO pojo = objectMapper.readValue(body, EventPOJO.class);
        assertThat(pojo.getId(), is(data.id));
        assertThat(pojo.getUserId(), is(data.userId));
        assertThat(pojo.getTimestamp(), is(data.timestamp));
        assertThat(pojo.getOperation(), is(data.operation));
        assertThat(pojo.getLongText(), is(data.longText));
    }
    
    /**
     * Test  GET /v1/even for no data. 
     */
    @Test
    public void testList_should_return_empty_array() throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/v1/event"); 

        Result result = route(app, request);
        final String body = contentAsString(result);
        ObjectMapper objectMapper = new ObjectMapper();
        EventPOJO[] pojos = objectMapper.readValue(body, EventPOJO[].class);
        assertThat("Should return empty array in json.", pojos.length, is(0));
    }
    

    /**
     * Test GET /v1/event listing two created events.
     */
    @Test
    public void testList_should_return_two_events_in_json() throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
        IEventRepository repository = app.injector().instanceOf(IEventRepository.class);
        Event event1 = createEventEntity();
        event1 = repository.create(event1).toCompletableFuture().get();
        Event event2 = createEventEntity();
        event2 = repository.create(event2).toCompletableFuture().get();
        
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/v1/event"); 

        Result result = route(app, request);
        final String body = contentAsString(result);
        System.out.println(body);
        ObjectMapper objectMapper = new ObjectMapper();
        EventPOJO[] pojos = objectMapper.readValue(body, EventPOJO[].class);
        
        assertThat("Two events in json", pojos.length, is(2));
        
        assertThat(pojos[0].getId(), is(event1.id));
        assertThat(pojos[0].getUserId(), is(event1.userId));
        assertThat(pojos[0].getTimestamp(), is(event1.timestamp));
        assertThat(pojos[0].getOperation(), is(event1.operation));
        assertThat(pojos[0].getLongText(), is(event1.longText));
        
        assertThat(pojos[1].getId(), is(event2.id));
        assertThat(pojos[1].getUserId(), is(event2.userId));
        assertThat(pojos[1].getTimestamp(), is(event2.timestamp));
        assertThat(pojos[1].getOperation(), is(event2.operation));
        assertThat(pojos[1].getLongText(), is(event2.longText));
    }


}
