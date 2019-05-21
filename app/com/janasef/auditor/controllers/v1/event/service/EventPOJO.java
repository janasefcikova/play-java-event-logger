package com.janasef.auditor.controllers.v1.event.service;

import java.util.Date;

import com.janasef.auditor.controllers.v1.event.domain.Event;

/**
 * Plain Resource for the API.
 */
public class EventPOJO {
    private Long id;
    private Long userId;
    private Date timestamp;
    private String operation;
    private String userRole;
    private String longText;

    public EventPOJO() {
    }
    
    public Event convert() {
    	Event data =  new Event();
    	data.id = this.id;
    	data.userId = this.userId;
    	data.timestamp = this.timestamp;
    	data.operation = this.operation;
    	data.userRole = this.userRole;
    	data.longText = this.longText;
    	return data;
    }

    public EventPOJO(Event data) {

        this.id = data.id;
        this.userId = data.userId;
        this.timestamp = data.timestamp;
        this.operation = data.operation;
        this.userRole = data.userRole;
        this.longText = data.longText;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getLongText() {
		return longText;
	}

	public void setLongText(String longText) {
		this.longText = longText;
	}

	@Override
	public String toString() {
		return "EventPOJO [id=" + id + ", userId=" + userId + ", timestamp=" + timestamp + ", operation=" + operation
				+ ", userRole=" + userRole + ", longText=" + longText + "]";
	}
	
	
    
}
