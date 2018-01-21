package com.ramit.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  
@Table(name= "JOBS")  
public class Jobs {
	
	public Jobs(){
	}
	
	@Id
	@Column(name="JOB_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String jobId;
	
	@Column(name="JOB_TITLE")
	private String jobTitle;
	
	@Column(name="MIN_SALARY")
	private Integer minSalary;
	
	@Column(name="MAX_SALARY")
	private Integer maxSalary;

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Integer getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(Integer minSalary) {
		this.minSalary = minSalary;
	}

	public Integer getMaxSalary() {
		return maxSalary;
	}

	public void setMaxSalary(Integer maxSalary) {
		this.maxSalary = maxSalary;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("jobId=");
		sb.append(jobId);
		sb.append(", jobTitle=");
		sb.append(jobTitle);
		sb.append(", minSalary=");
		sb.append(minSalary);
		sb.append(", maxSalary=");
		sb.append(maxSalary);
		return sb.toString();
	}
}
