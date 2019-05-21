package com.janasef.auditor.controllers.v1.event.domain;

import java.util.Date;

import javax.persistence.*;

@Entity
public class Event {

    public Event() {
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;
    
    public Long userId;
    
    public Date timestamp;
    
    public String operation;
    
    public String userRole;
    
    @Lob
    public String longText;
    
}
