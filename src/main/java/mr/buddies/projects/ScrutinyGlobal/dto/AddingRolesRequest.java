package mr.buddies.projects.ScrutinyGlobal.dto;

import java.util.List;

public class AddingRolesRequest {

	private Integer userId;
	private String accountType;
	private List<String> roles;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public AddingRolesRequest(Integer userId, String accountType, List<String> roles) {
		super();
		this.userId = userId;
		this.accountType = accountType;
		this.roles = roles;
	}
	
	
}
