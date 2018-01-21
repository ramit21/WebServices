package com.ramit.service;

public interface ServiceIntf<T, I> {
	T selectAll();
	T pagedResults(Integer pageSize,Integer pageNo);
	T whereResults(String x, String y);
}
