
package com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileMembers;

import org.apache.ibatis.type.Alias;

@Alias("Member")
public class Member {

	    private Integer order;
	    private String name="";
	    private String sex="";
	    private String credentialType="";
	    private Integer age=0;
	    private String relationship="";
	    private String nationality="";
	    private String politicalAffiliation="";
	    private String education="";
	    private String atSchool="";
	    private String health="";
	    private String laborCapacity="";
	    private String workStatus="";
	    private String workTime="";
	    private String activeService="";
	    private String seriousIllnessInsurance="";
	    private String technicalSchoolWilling="";
	    private String trainNeed="";
	    private String skillStatus="";
	    private String employmentWilling="";
	    private String employmentExpectation="";
	    private String staffPensionInsurance="";
	    private String residentMedicalInsurance="";
	    private String residentPensionInsurance="";
	    private String pensionLevel="";

	    public Integer getOrder() {
	        return order;
	    }

	    public void setOrder(Integer order) {
	        this.order = order;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getSex() {
	        return sex;
	    }

	    public void setSex(String sex) {
	        this.sex = sex;
	    }

	    public String getCredentialType() {
	        return credentialType;
	    }

	    public void setCredentialType(String credentialType) {
	        this.credentialType = credentialType;
	    }

	    public Integer getAge() {
	        return age;
	    }

	    public void setAge(Integer age) {
	        this.age = age;
	    }

	    public String getRelationship() {
	        return relationship;
	    }

	    public void setRelationship(String relationship) {
	        this.relationship = relationship;
	    }

	    public String getNationality() {
	        return nationality;
	    }

	    public void setNationality(String nationality) {
	        this.nationality = nationality;
	    }

	    public String getPoliticalAffiliation() {
	        return politicalAffiliation;
	    }

	    public void setPoliticalAffiliation(String politicalAffiliation) {
	        this.politicalAffiliation = politicalAffiliation;
	    }

	    public String getEducation() {
	        return education;
	    }

	    public void setEducation(String education) {
	        this.education = education;
	    }

	    public String getAtSchool() {
	        return atSchool;
	    }

	    public void setAtSchool(String atSchool) {
	        this.atSchool = atSchool;
	    }

	    public String getHealth() {
	        return health;
	    }

	    public void setHealth(String health) {
	        this.health = health;
	    }

	    public String getLaborCapacity() {
	        return laborCapacity;
	    }

	    public void setLaborCapacity(String laborCapacity) {
	        this.laborCapacity = laborCapacity;
	    }

	    public String getWorkStatus() {
	        return workStatus;
	    }

	    public void setWorkStatus(String workStatus) {
	        this.workStatus = workStatus;
	    }

	   

	    public String getWorkTime() {
			return workTime;
		}

		public void setWorkTime(String workTime) {
			this.workTime = workTime;
		}

		public String getActiveService() {
	        return activeService;
	    }

	    public void setActiveService(String activeService) {
	        this.activeService = activeService;
	    }

	    public String getSeriousIllnessInsurance() {
	        return seriousIllnessInsurance;
	    }

	    public void setSeriousIllnessInsurance(String seriousIllnessInsurance) {
	        this.seriousIllnessInsurance = seriousIllnessInsurance;
	    }

	    public String getTechnicalSchoolWilling() {
	        return technicalSchoolWilling;
	    }

	    public void setTechnicalSchoolWilling(String technicalSchoolWilling) {
	        this.technicalSchoolWilling = technicalSchoolWilling;
	    }

	    public String getTrainNeed() {
	        return trainNeed;
	    }

	    public void setTrainNeed(String trainNeed) {
	        this.trainNeed = trainNeed;
	    }

	    public String getSkillStatus() {
	        return skillStatus;
	    }

	    public void setSkillStatus(String skillStatus) {
	        this.skillStatus = skillStatus;
	    }

	    public String getEmploymentWilling() {
	        return employmentWilling;
	    }

	    public void setEmploymentWilling(String employmentWilling) {
	        this.employmentWilling = employmentWilling;
	    }

	    public String getEmploymentExpectation() {
	        return employmentExpectation;
	    }

	    public void setEmploymentExpectation(String employmentExpectation) {
	        this.employmentExpectation = employmentExpectation;
	    }

	    public String getStaffPensionInsurance() {
	        return staffPensionInsurance;
	    }

	    public void setStaffPensionInsurance(String staffPensionInsurance) {
	        this.staffPensionInsurance = staffPensionInsurance;
	    }

	    public String getResidentMedicalInsurance() {
	        return residentMedicalInsurance;
	    }

	    public void setResidentMedicalInsurance(String residentMedicalInsurance) {
	        this.residentMedicalInsurance = residentMedicalInsurance;
	    }

	    public String getResidentPensionInsurance() {
	        return residentPensionInsurance;
	    }

	    public void setResidentPensionInsurance(String residentPensionInsurance) {
	        this.residentPensionInsurance = residentPensionInsurance;
	    }

		public String getPensionLevel() {
			return pensionLevel;
		}

		public void setPensionLevel(String pensionLevel) {
			this.pensionLevel = pensionLevel;
		}


}
