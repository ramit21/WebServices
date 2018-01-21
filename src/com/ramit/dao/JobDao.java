package com.ramit.dao;

import java.util.List;

import com.ramit.persistence.model.Jobs;

public interface JobDao {
	
	List<Jobs> getAllJobs();
	List<Jobs> getPagedJobs(Integer pageSize, Integer pageNo);
	List<Jobs> getSelectedJobs(String x, String y);
}
