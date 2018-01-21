package com.ramit.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ramit.domain.Coffee;
import com.wordnik.swagger.annotations.Api;

@Api(value = "coffee", description = "swaggerCoffee") // Swagger annotation
@Controller
@RequestMapping("/coffee")
public class CoffeeController extends BaseController {
	
	@RequestMapping(value = "/{name}/{type}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Coffee> getCoffeeInXML(@PathVariable String name,@PathVariable String type) {
		Coffee coffee = new Coffee(name, type, 100);
		ResponseEntity<Coffee> response = new ResponseEntity<Coffee>(coffee, HttpStatus.OK);
		return response;
	}
	
	@ResponseBody
    @RequestMapping(value="/submit", method=RequestMethod.POST)
    public String saveCoffee(@RequestBody Coffee coffee,ModelMap model) {
    	System.out.println("Saved Coffee: " + coffee.getName() + " , " + coffee.getQuanlity());
    	model.addAttribute("success", "data saved successfully");
    	return "Simple post executed";
    }
	
	@ResponseBody
    @RequestMapping(value="/exception", method=RequestMethod.GET)
    public ResponseEntity<String> testRuntimeException() {
    	int a = 4/0;
    	return new ResponseEntity<String>("No Exception", HttpStatus.OK);
    }
	
}
