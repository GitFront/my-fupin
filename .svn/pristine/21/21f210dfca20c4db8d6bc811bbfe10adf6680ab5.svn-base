
package com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileMembers;
import java.util.List;
import com.aspire.bi.common.config.Global;
import com.aspire.birp.modules.antiPoverty.model.base.BaseData;


public class FileMembersData  extends BaseData{

    private List<Member> members;
    private List<Album> album;
    public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public List<Album> getAlbum() {
		return album;
	}

	public void setAlbum(List<Album> album) {
		this.album = album;
	}
	
	private String getMembersList(){
		StringBuffer sb =new StringBuffer("[");
	  	   for(int i = 0;i <members.size();i++)
	  	   {
	  		 Member member = members.get(i);
	  		 sb.append("{");
	  		 sb.append("\"order\":").append(i+1).append(",");
	  		 sb.append("\"name\":").append("\""+member.getName()+"\"").append(",");
	  		 sb.append("\"sex\":").append("\""+member.getSex()+"\"").append(",");
	  		 sb.append("\"credential_type\":").append("\""+member.getCredentialType()+"\"").append(",");
	  		 sb.append("\"age\":").append(member.getAge()).append(",");
	  		 sb.append("\"relationship\":").append("\""+member.getRelationship()+"\"").append(",");
	  		 sb.append("\"nationality\":").append("\""+member.getNationality()+"\"").append(",");
	  		 sb.append("\"political_affiliation\":").append("\""+member.getPoliticalAffiliation()+"\"").append(",");
	  		 sb.append("\"education\":").append("\""+member.getEducation()+"\"").append(",");
	  		 sb.append("\"at_school\":").append("\""+member.getAtSchool()+"\"").append(",");
	  		 sb.append("\"health\":").append("\""+member.getHealth()+"\"").append(",");
	  		 sb.append("\"labor_capacity\":").append("\""+member.getLaborCapacity()+"\"").append(",");
	  		 sb.append("\"work_status\":").append("\""+member.getWorkStatus()+"\"").append(",");
	  		 sb.append("\"work_time\":").append("\""+member.getWorkTime()+"\"").append(",");
	  		 sb.append("\"active_service\":").append("\""+member.getActiveService()+"\"").append(",");
	  		 sb.append("\"serious_illness_insurance\":").append("\""+member.getSeriousIllnessInsurance()+"\"").append(",");
	  		 sb.append("\"technical_school_willing\":").append("\""+member.getTechnicalSchoolWilling()+"\"").append(",");
	  		 sb.append("\"train_need\":").append("\""+member.getTrainNeed()+"\"").append(",");
	  		 sb.append("\"skill_status\":").append("\""+member.getSkillStatus()+"\"").append(",");
	  		 sb.append("\"employment_willing\":").append("\""+member.getEmploymentWilling()+"\"").append(",");
	  		 sb.append("\"employment_expectation\":").append("\""+member.getEmploymentExpectation()+"\"").append(",");
	  		 sb.append("\"staff_pension_insurance\":").append("\""+member.getStaffPensionInsurance()+"\"").append(",");
	  		 sb.append("\"resident_medical_insurance\":").append("\""+member.getResidentMedicalInsurance()+"\"").append(",");
	  		 sb.append("\"resident_pension_insurance\":").append("\""+member.getResidentPensionInsurance()+"\"").append(",");
	  		 sb.append("\"pension_level\":").append("\""+member.getPensionLevel()+"\"");
	  		 if((i+1) == members.size()){
	  			sb.append("}");
	  		 }else{
	  			sb.append("}").append(",");
	  		 }
	  		 
	  	   }
	  	 sb.append("]");
		return sb.toString();
	}
	
	private String getAlbumList(){
		String name = "";
		if(null!= album && album.size()>0){
			name = album.get(0).getHouseHolderName();
		}
		StringBuffer sb =new StringBuffer("{");
		sb.append("\"house_holder_name\":\""+name+"\"").append(",");
		sb.append("\"list\":[");
		for(int j=0;j<album.size();j++){
			Album ab = album.get(j);
			sb.append("{\"src\":");
			sb.append("\""+Global.getConfig("image.path")+ab.getSrc()+"\"");
			if((j+1) == album.size()){
	  			sb.append("}");
	  		 }else{
	  			sb.append("}").append(",");
	  		 }
		}
		sb.append("]}");
		return sb.toString();
	}
		

	
	@Override
	public String toString() {
		return "{\"member\":{\"table\":"+getMembersList()+"},\"album\":"+getAlbumList()+"}";
	}
	



}
