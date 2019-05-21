package com.janasef.auditor.controllers;


import play.mvc.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class ApplicationController extends Controller {
	

	ApplicationController(){	
	}
	
    public Result index() {
        return ok(com.janasef.auditor.views.html.index.render()); // TODO
    }

}
