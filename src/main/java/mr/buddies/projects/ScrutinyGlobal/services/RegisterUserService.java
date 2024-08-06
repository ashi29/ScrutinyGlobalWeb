package mr.buddies.projects.ScrutinyGlobal.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mr.buddies.projects.ScrutinyGlobal.dto.AddingRolesRequest;
import mr.buddies.projects.ScrutinyGlobal.dto.OtpVerifing;
import mr.buddies.projects.ScrutinyGlobal.dto.RegisterRequest;
import mr.buddies.projects.ScrutinyGlobal.dto.SettingRoleRquest;
import mr.buddies.projects.ScrutinyGlobal.dto.UserFilter;
import mr.buddies.projects.ScrutinyGlobal.exception.UserAlreadyExist;
import mr.buddies.projects.ScrutinyGlobal.model.ClientDetails;
import mr.buddies.projects.ScrutinyGlobal.model.RegisterUser;
import mr.buddies.projects.ScrutinyGlobal.model.VenderDetails;
import mr.buddies.projects.ScrutinyGlobal.repo.ClientRepository;
import mr.buddies.projects.ScrutinyGlobal.repo.RegisterUserRepository;
import mr.buddies.projects.ScrutinyGlobal.repo.VenderRepository;

@Service
public class RegisterUserService {
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private RegisterUserRepository registerUserRepository;
	
	@Autowired
	private VenderRepository venderRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	 public RegisterUserService(JavaMailSender mailSender) {
	        this.mailSender = mailSender;
	    }
	
		public boolean saveRegisterUser(RegisterUser registerUser) {
			
			try {					
				registerUserRepository.save(registerUser);
				return true;
				
			}catch(Exception e) {
				System.out.println("exception"+e);
				return false;
			}
			
		}
		
		   private void sendSimpleEmail(String toEmail,String subject,String body) {
			   System.out.println("in Mail Send...");
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("testingdummy892@gmail.com");
			message.setTo(toEmail);
			message.setText(body);
			message.setSubject(subject);
			mailSender.send(message);
			System.out.println("Mail Send...");			
			 
			}
		   
		   public boolean sendOtpOnMail(String email,HttpSession session) throws UserAlreadyExist {
			   
			   int count=registerUserRepository.findActiveUserByEmail(email);
			   System.out.println("Count is..."+count);
			   if(count>0) 
			   {
				   throw new UserAlreadyExist("This email already exist");
				   
			   }
			   
			   Random random = new Random();
				int newOTP=1000 + random.nextInt(9000);				
				sendSimpleEmail(email,"OTP for registration","your OTP is "+newOTP);
				session.setAttribute("userEmail", email);
				session.setAttribute("userOtp", newOTP);
				session.setAttribute("userOtpTime", new Date());
				
				
			   return true;
		   }
		   
		   public List<RegisterUser> findAllUserForAprovel(){
			   
			   return registerUserRepository.findAllUserForAprovel();
		   }
		   
		  public List<RegisterUser> getAllActiveUser(){
					   
					   return registerUserRepository.findAllActiveUser();
				   }
		  public List<RegisterUser> getUserList(UserFilter userFilter){
			  int aprove=0;
			  if(userFilter.isAprove())
				  aprove=1;
				  Boolean accountCheck=null;
				  Boolean countryCheck=null;
				  Boolean professionCheck=null;
				
				List<String> accountType=new ArrayList<String>();
				List<String> country=new ArrayList<String>();
				List<String> profession=new ArrayList<String>();
				
				if(userFilter.getAccountType()!=null) {
					accountCheck=true;
					accountType.addAll(userFilter.getAccountType());
				}
				if(userFilter.getCountry()!=null) {
					countryCheck=true;
					country.addAll(userFilter.getCountry());
				}
				if(userFilter.getProfession()!=null) {
					professionCheck=true;
					profession.addAll(userFilter.getProfession());
				}
			   
			   return registerUserRepository.findUserList(accountCheck,countryCheck,professionCheck,accountType,country,profession,aprove);
		   }


		   @Transactional
		   public boolean giveRoleToUser(SettingRoleRquest settingRoleRquest) {
			   
			   int check= registerUserRepository.giveRoleToUser(settingRoleRquest.getAccountType(), settingRoleRquest.getUserId());
			   
			   if(check==1) {
				   if((settingRoleRquest.getAccountType()).toUpperCase().equals("VENDER")) {
					   VenderDetails venderDetails=new VenderDetails();
					   venderDetails.setVenderId(settingRoleRquest.getUserId());
					   venderDetails.setSuccessURL(settingRoleRquest.getSuccessURL());
					   venderDetails.setTerminateURL(settingRoleRquest.getTerminateURL());
					   venderDetails.setQuotaFullURL(settingRoleRquest.getQuotaFullURL());
					   venderDetails.setSecurityTerminateURL(settingRoleRquest.getSecurityTerminateURL());
					   venderDetails.setAlternateNumber(settingRoleRquest.getAlternateNumber());
					   venderDetails.setRegisterationNumber(settingRoleRquest.getRegisterationNumber());
					   venderDetails.setPanNumber(settingRoleRquest.getPanNumber());
					   venderDetails.setBankBranchAddress(settingRoleRquest.getBankBranchAddress());
					   venderDetails.setAccountNumber(settingRoleRquest.getAccountNumber());
					   venderDetails.setIfscCode(settingRoleRquest.getIfscCode());
					   venderDetails.setBankAccountType(settingRoleRquest.getAccountType());
					   
					   
					   venderRepository.save(venderDetails);
				   }
				   if((settingRoleRquest.getAccountType()).toUpperCase().equals("CLIENT")) {
					   
					   ClientDetails clientDetails=new ClientDetails(settingRoleRquest.getUserId(),settingRoleRquest.getContactName(),settingRoleRquest.getContactEmail(),settingRoleRquest.getAlternateNumber(),settingRoleRquest.getWebsite(),settingRoleRquest.getIndustry());
					   clientRepository.save(clientDetails);
				   }
				   
			   }else
				   return false;
			   return true;
			   
			   
		   }
		   public String getRoleGivenByAdmin(Integer userId) {
			   
			   String roles=registerUserRepository.getRoleGivenByAdmin(userId);
			   
			   return roles;
			   
		   }
		   
		   @Transactional
		   public boolean giveRoleByAdmin(AddingRolesRequest addingRolesRequest) {
			   
			   String roles=getRoleGivenByAdmin(addingRolesRequest.getUserId());
			   
			   if(roles.toUpperCase().equals("ALL")||roles.isEmpty())
			   {
				   roles="";
			   }else
			   {
				   roles=roles+",";
			   }
			   if(!addingRolesRequest.getRoles().isEmpty()) {
				   for (String item : addingRolesRequest.getRoles()) {
					   roles=roles+item+",";
			        }
				   roles=roles.substring(0, roles.length() - 1);  
			   }else {
				   roles="";
			   } 				   
				System.out.println(roles);

			   int check= registerUserRepository.giveRoleByAdmin(addingRolesRequest.getAccountType(),addingRolesRequest.getUserId(),roles);
					   
					   if (check==1)
			   return true;
					   else
						   return false;
		   }
		   
		   public List<Map<String,Object>> getListAsAccountType(String accountType) throws Exception {
			   
			   List<Map<String,Object>> registerRequestList=new ArrayList<Map<String,Object>>();
			   
			   if(accountType.toUpperCase().equals("CLIENT")) 
				   registerRequestList =registerUserRepository.getListAsClient();
							   
			   if(accountType.toUpperCase().equals("VENDER")) 
				   registerRequestList= registerUserRepository.getListAsVender();   
			   
			   return registerRequestList;
			   
			   
			   
		   }
		   
		   
		   public boolean addVender(RegisterUser registerUser,VenderDetails venderDetails) {
			   
				try {					
					  registerUserRepository.save(registerUser);
					   venderRepository.save(venderDetails);
					return true;
					
				}catch(Exception e) {
					System.out.println("exception"+e);
					return false;
				}
		   }
		   
		   public Map<String,Object> getUserDetails(String userEmail) {
			   
			   
			   return  registerUserRepository.getUserDetails(userEmail);
					
			   }
		   
		   
}
