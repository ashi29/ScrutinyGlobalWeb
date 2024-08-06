package mr.buddies.projects.ScrutinyGlobal.dto;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RegisterRequest {

	    private Integer userId;
	    @NotNull(message = "User name can't be blank")
	    private String name;
	    @Email(message = "Plz enter valid email Id")
	    private String email;
	    private String password;
	    
	    private String number;
	    private String countryCode;
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	    private Date dob;
	    private String country;
	    private String state;
		private String city;
	    private String zipcode;
	    private String profession;
	    private String accountType;
	    private String monthlySalary;
	    private String address;
	    private Integer otp;
	    
	    public RegisterRequest(Integer userId, String name, String email, String password, String number,
				String countryCode, Date dob, String country, String state, String city, String zipcode,
				String profession, String accountType, String monthlySalary, String address, Integer otp) {
			super();
			this.userId = userId;
			this.name = name;
			this.email = email;
			this.password = password;
			this.number = number;
			this.countryCode = countryCode;
			this.dob = dob;
			this.country = country;
			this.state = state;
			this.city = city;
			this.zipcode = zipcode;
			this.profession = profession;
			this.accountType = accountType;
			this.monthlySalary = monthlySalary;
			this.address = address;
			this.otp = otp;
		}
	   
	    
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}
		public String getCountryCode() {
			return countryCode;
		}
		public void setCountryCode(String countryCode) {
			this.countryCode = countryCode;
		}
		public Date getDob() {
			return dob;
		}
		public void setDob(Date dob) {
			this.dob = dob;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getZipcode() {
			return zipcode;
		}
		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}
		public String getProfession() {
			return profession;
		}
		public void setProfession(String profession) {
			this.profession = profession;
		}
		
		public String getMonthlySalary() {
			return monthlySalary;
		}
		public void setMonthlySalary(String monthlySalary) {
			this.monthlySalary = monthlySalary;
		}
		public Integer getOtp() {
			return otp;
		}
		public void setOtp(Integer otp) {
			this.otp = otp;
		}
		public String getAccountType() {
			return accountType;
		}
		public void setAccountType(String accountType) {
			this.accountType = accountType;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
	
	    
	    
}
