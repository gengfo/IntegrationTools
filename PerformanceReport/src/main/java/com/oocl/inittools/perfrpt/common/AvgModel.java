package com.oocl.inittools.perfrpt.common;

public class AvgModel {
	
	public AvgModel(){
		
	}
	
	public AvgModel(String requestName, String hitCount, String avgElapsedTime, String ttlElapsedTime){
		this.requestName = requestName;
		this.hitCount = hitCount;
		this.avgElapsedTime = avgElapsedTime;
		this.totalElapsedTime = ttlElapsedTime;
	}
	
	public String requestName;
	
	public String getRequestName() {
		return requestName;
	}

	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}

	public String getHitCount() {
		return hitCount;
	}

	public void setHitCount(String hitCount) {
		this.hitCount = hitCount;
	}

	public String getAvgElapsedTime() {
		return avgElapsedTime;
	}

	public void setAvgElapsedTime(String avgElapsedTime) {
		this.avgElapsedTime = avgElapsedTime;
	}

	public String getTotalElapsedTime() {
		return totalElapsedTime;
	}

	public void setTotalElapsedTime(String totalElapsedTime) {
		this.totalElapsedTime = totalElapsedTime;
	}

	public String hitCount;

	public String avgElapsedTime;

	public String totalElapsedTime;
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();

	
		sb.append("RequestName-> " + this.getRequestName());
		sb.append(" | ");
		sb.append("HitCount-> " + this.getHitCount());
		
		
		sb.append(" | ");
		sb.append("AvgElapsedTime-> " + this.getAvgElapsedTime());
		sb.append(" | ");
		sb.append("TotalElapsedTime-> " + this.getTotalElapsedTime());
		

		return sb.toString();

	}

	
	

}
