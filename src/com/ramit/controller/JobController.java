package com.ramit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ramit.persistence.model.Jobs;
import com.ramit.service.ServiceIntf;
import com.wordnik.swagger.annotations.Api;

@Api(value = "jobs", description = "query job table with jpa") // Swagger annotation
@Controller
@RequestMapping("/jobs")
public class JobController extends BaseController {
	
	@Autowired
	@Qualifier("daoService")
	private ServiceIntf<List<Jobs>,String> service;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Jobs>> getAllJobs() {
		return new ResponseEntity<List<Jobs>>(service.selectAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/title", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Jobs>> getJobsWhere(@RequestParam("title1") String x,@RequestParam("title2") String y) {
		List<Jobs> results = service.whereResults(x, y);
		if(results.isEmpty()){
			return new ResponseEntity<List<Jobs>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Jobs>>(results, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Jobs>> getPagedJobs(@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNo") Integer pageNo) {
		//Use @RequestParam for ?pageSize=, and @PathVariable for /var
		List<Jobs> results = service.pagedResults(pageSize, pageNo);
		if(results.isEmpty()){
			return new ResponseEntity<List<Jobs>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Jobs>>(results, HttpStatus.OK);
	}
}
