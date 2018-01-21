package com.ramit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ramit.dao.JobDao;
import com.ramit.persistence.model.Jobs;

@Service("daoService")
public class DaoService implements ServiceIntf<List<Jobs>, String> {

	@Autowired
	@Qualifier("jobDao")
	private JobDao jobDao;

	@Override
	public List<Jobs> selectAll() {
		return jobDao.getAllJobs();
	}

	@Override
	public List<Jobs> pagedResults(Integer pageSize, Integer pageNo) {
		return jobDao.getPagedJobs(pageSize, pageNo);
	}

	@Override
	public List<Jobs> whereResults(String x, String y) {
		return jobDao.getSelectedJobs(x, y);
	}

}
