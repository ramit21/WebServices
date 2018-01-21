package com.ramit.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ramit.dao.JobDao;
import com.ramit.persistence.model.Jobs;

public class JobDaoImpl implements JobDao{
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Jobs> getAllJobs(){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Jobs> criteria = builder.createQuery(Jobs.class);
		Root<Jobs> jobRoot = criteria.from(Jobs.class);
		criteria.select(jobRoot);
		List<Jobs> jobs = em.createQuery(criteria).getResultList();
		return jobs;
	}

	@Override
	public List<Jobs> getPagedJobs(Integer pageSize, Integer pageNo) {
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		
		CriteriaQuery<Long> cq = builder.createQuery(Long.class);
		cq.select(builder.count(cq.from(Jobs.class)));
		Long totalRecords =  em.createQuery(cq).getSingleResult();
		System.out.println("getPagedJobs(): Total records in DB = " + totalRecords);
		
		CriteriaQuery<Jobs> criteria = builder.createQuery(Jobs.class);
		Root<Jobs> jobRoot = criteria.from(Jobs.class);
		criteria.select(jobRoot);
		criteria.orderBy(builder.asc(jobRoot.get("maxSalary")));   //order by salary
		
		TypedQuery<Jobs> query = em.createQuery(criteria);
		
		query.setFirstResult(pageSize*(pageNo-1));
		query.setMaxResults(pageSize);
		return query.getResultList();	//verify results: select * from Jobs order by max_salary
	}
	
	@Override
	public List<Jobs> getSelectedJobs(String x, String y) {
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		
		CriteriaQuery<Long> cq = builder.createQuery(Long.class);
		cq.select(builder.count(cq.from(Jobs.class)));
		Long totalRecords =  em.createQuery(cq).getSingleResult();
		System.out.println("getPagedJobs(): Total records in DB = " + totalRecords);
		
		CriteriaQuery<Jobs> criteria = builder.createQuery(Jobs.class);
		Root<Jobs> jobRoot = criteria.from(Jobs.class);
		criteria.select(jobRoot);
		criteria.where(jobRoot.get("jobTitle").in(x,y));    // where clause, note that Restrictions is specific to hibernate
		
		TypedQuery<Jobs> query = em.createQuery(criteria);
		return query.getResultList();
	}
	
	private Pageable constructPageSpecification(int pageSize, int pageIndex) {
		Pageable pageSpecification = new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.DESC, "maxSalary"));
		return pageSpecification;
	}
 
}
