package mr.buddies.projects.ScrutinyGlobal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "RegisterUser")
public class RegisterUser {

	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sixDigitIdGenerator")
	    @GenericGenerator(name = "sixDigitIdGenerator", strategy = "mr.buddies.projects.ScrutinyGlobal.helper.SixDigitIdGenerator")
	    private Integer userId;
	    private String name;
	    private String email;
	    private String password;
	    private String number;
	    private String countryCode;
	    private Date dob;
	    private String country;
	    private String state;
	    private String city;
	    private String zipcode;
	    private String profession;
	    private String accountType;
	    private String address;
	    private String monthlySalary;
	    @Column(nullable = false, columnDefinition = "varchar(255) default 'ALL'")
	    private String roles;
	    private Integer otp;
	    private Integer aprove;
	    private Date createTime;
	    private Date updateTime;
	    
	
		public Integer getUserId() {
			return userId;
		}
		public void setUserId(Integer userId) {
			this.userId = userId;
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
		public Integer getAprove() {
			return aprove;
		}
		public void setAprove(Integer aprove) {
			this.aprove = aprove;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
	
		public Date getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
		
		public String getRoles() {
			return roles;
		}
		public void setRoles(String roles) {
			this.roles = roles;
		}
		public RegisterUser() {
			super();
			// TODO Auto-generated constructor stub
		}
		public RegisterUser(Integer userId, String name, String email, String password, String number,
				String countryCode, Date dob, String country, String state, String city, String zipcode,
				String profession, String accountType, String address, String monthlySalary, String roles, Integer otp,
				Integer aprove, Date createTime, Date updateTime) {
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
			this.address = address;
			this.monthlySalary = monthlySalary;
			this.roles = roles;
			this.otp = otp;
			this.aprove = aprove;
			this.createTime = createTime;
			this.updateTime = updateTime;
		}
		
		
	    
	    
	
}
