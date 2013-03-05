package com.oocl.inittools.perfrpt.common;

public class AvgOutputBean {
	
	private String processName;
	private AvgModel model1;
	private AvgModel model2;
	
	public AvgModel getModel1() {
		return model1;
	}
	public void setModel1(AvgModel model1) {
		this.model1 = model1;
	}
	public AvgModel getModel2() {
		return model2;
	}
	public void setModel2(AvgModel model2) {
		this.model2 = model2;
	}
	
	
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}

}
