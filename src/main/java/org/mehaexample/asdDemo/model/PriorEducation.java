package org.mehaexample.asdDemo.model;

public class PriorEducation {
	private int id;
	private String nuid;
	private String InstitutionName;
	private String DegreeLevel;
	private String Major;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	} 
	
	public String getNuid() {
		return nuid;
	}

	public void setNuid(String nuid) {
		this.nuid = nuid;
	}
	
	public String getInstitutionName() {
		return InstitutionName;
	}
	
	public void setInstitutionName(String institutionName) {
		InstitutionName = institutionName;
	}
	
	public String getDegreeLevel() {
		return DegreeLevel;
	}
	
	public void setDegreeLevel(String degreeLevel) {
		DegreeLevel = degreeLevel;
	}
	
	public String getMajor() {
		return Major;
	}
	
	public void setMajor(String major) {
		Major = major;
	}
}
