package com.ramit.controller;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.Api;

@Api(value = "cookie", description = "swaggerCookie") // Swagger annotation
@Controller
@RequestMapping("/cookie")
public class CookieController extends BaseController {
		
	// http://127.0.0.1:8090/WebService/rest/cookie/readSessionCookie/
	@RequestMapping(value = "/readSessionCookie/", method = RequestMethod.GET)
	@ResponseBody
	public String readSessionCookie(@CookieValue(value = "JSESSIONID", defaultValue = "defaultValue") String sessionId) {
		return sessionId;
	}
	
	//http://127.0.0.1:8090/WebService/rest/cookie/createCookie/
	@ResponseBody
    @RequestMapping(value="/createCookie")
    public String writeCookie(HttpServletResponse response) {
		Cookie createdCookie = new Cookie("createdCookie", "myValue"); //bake cookie
		//createdCookie.setMaxAge(1000); //set expire time to 1000 sec
		response.addCookie(createdCookie); //put cookie in response 
    	return "Cookie set in the response";
    }
	
	//http://127.0.0.1:8090/WebService/rest/cookie/readCookie/
	@RequestMapping(value = "/readCookie/", method = RequestMethod.GET)
	@ResponseBody
	public String readCookie(@CookieValue(value = "createdCookie", defaultValue = "defaultValue") String createdCookie) {
		return createdCookie;
	}
}
