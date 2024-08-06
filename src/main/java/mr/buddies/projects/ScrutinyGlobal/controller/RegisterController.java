package mr.buddies.projects.ScrutinyGlobal.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import mr.buddies.projects.ScrutinyGlobal.dto.AddingRolesRequest;
import mr.buddies.projects.ScrutinyGlobal.dto.OtpVerifing;
import mr.buddies.projects.ScrutinyGlobal.dto.RegisterRequest;
import mr.buddies.projects.ScrutinyGlobal.dto.SettingRoleRquest;
import mr.buddies.projects.ScrutinyGlobal.dto.UserFilter;
import mr.buddies.projects.ScrutinyGlobal.dto.VenderRequest;
import mr.buddies.projects.ScrutinyGlobal.exception.ErrorMsgException;
import mr.buddies.projects.ScrutinyGlobal.exception.UserAlreadyExist;
import mr.buddies.projects.ScrutinyGlobal.helper.MD5Util;
import mr.buddies.projects.ScrutinyGlobal.helper.SessionStore;
import mr.buddies.projects.ScrutinyGlobal.model.CountryData;
import mr.buddies.projects.ScrutinyGlobal.model.DocumentDetails;
import mr.buddies.projects.ScrutinyGlobal.model.JwtResponse;
import mr.buddies.projects.ScrutinyGlobal.model.RegisterUser;
import mr.buddies.projects.ScrutinyGlobal.model.VenderDetails;
import mr.buddies.projects.ScrutinyGlobal.services.CountryService;
import mr.buddies.projects.ScrutinyGlobal.services.RegisterUserService;
import mr.buddies.projects.ScrutinyGlobal.services.SendSmsService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ScrutinyGlobal")
public class RegisterController {
	
	@Autowired
	private RegisterUserService registerUserService;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private SendSmsService sendSmsService;
	
	@PostMapping("/saveRegisterUser")
	public ResponseEntity<?> saveRegisterUser(@RequestBody @Valid RegisterRequest registerRequest) throws ErrorMsgException, UserAlreadyExist {
		
		HttpSession session=SessionStore.getSession();
		RegisterUser registerUser =new RegisterUser();

		if(registerRequest.getEmail().equals(session.getAttribute("userEmail"))) {
			if(registerRequest.getOtp().equals(session.getAttribute("userOtp"))) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				registerUser.setName(registerRequest.getName());
				registerUser.setEmail(registerRequest.getEmail());
				registerUser.setPassword(MD5Util.hash(registerRequest.getPassword()));
				registerUser.setNumber(registerRequest.getNumber());
				registerUser.setCountryCode(registerRequest.getCountryCode());
				registerUser.setDob(registerRequest.getDob());
				registerUser.setCountry(registerRequest.getCountry());
				registerUser.setState(registerRequest.getState());
				registerUser.setCity(registerRequest.getCity());
				registerUser.setZipcode(registerRequest.getZipcode());
				registerUser.setProfession(registerRequest.getProfession());
				registerUser.setAccountType(registerRequest.getAccountType());
				registerUser.setAddress(registerRequest.getAddress());
				registerUser.setMonthlySalary(registerRequest.getMonthlySalary());
				registerUser.setOtp(registerRequest.getOtp());
				registerUser.setAprove(0);
				registerUser.setCreateTime(new Date());
				registerUser.setUpdateTime(new Date());
				
			}
			else {
				throw new ErrorMsgException("Wrong Otp");
			}
		}else {
			
			throw new UserAlreadyExist("User not match");
		} 
			 registerUserService.saveRegisterUser(registerUser);
			 return ResponseEntity.ok(registerRequest);
	}
	
	@PostMapping("/saveCountry")
	public boolean saveCountry(@RequestBody CountryData countryData) throws Exception {
		
		 try {
			 countryService.saveCountry(countryData);		
	          

	        } catch (UsernameNotFoundException e) {
	            e.printStackTrace();
	            throw new Exception("Bad Credentials");
	        }catch (BadCredentialsException e)
	        {
	            e.printStackTrace();
	            throw new Exception("Bad Credentials");
	        }
		 return true;
	}
	
	@GetMapping("/getAllUserForAprovel")
	public ResponseEntity<?> getAllUserForAprovel() throws Exception {
		
		List<RegisterUser> registerRequestList=new ArrayList<RegisterUser>();
		 try {
			 registerRequestList=registerUserService.findAllUserForAprovel();		
	          

	        } catch (Exception e) {
//	            e.printStackTrace();
	            throw new ErrorMsgException(e.getMessage());
	            
	        }
		 return ResponseEntity.ok(registerRequestList);
		 
	}
	@GetMapping("/getAllActiveUserList")
	public ResponseEntity<?> getAllActiveUserList() throws Exception {
		
		List<RegisterUser> registerRequestList=new ArrayList<RegisterUser>();
		 try {
			 registerRequestList=registerUserService.getAllActiveUser();		
	          

	        } catch (Exception e) {
//	            e.printStackTrace();
	            throw new ErrorMsgException(e.getMessage());
	            
	        }
		 return ResponseEntity.ok(registerRequestList);
		 
	}
	@PostMapping("/getUserList")
	public ResponseEntity<?> getUserList(@RequestBody UserFilter userFilter) throws Exception {
		
		System.out.println("Inside Controller");
        System.out.println(userFilter);
        
        
		List<RegisterUser> registerRequestList=new ArrayList<RegisterUser>();
		 try {
			 registerRequestList=registerUserService.getUserList(userFilter);		
	          

	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println(e);
	            throw new ErrorMsgException(e.getMessage());
	            
	        }
		 return ResponseEntity.ok(registerRequestList);
		 
	}
	
	@PostMapping("/otpsend")
	public boolean otpSendOnMail(@RequestBody OtpVerifing otpVerifing) throws UserAlreadyExist  {
		
		HttpSession session=SessionStore.getSession();
		 	if(otpVerifing.getNumber().length()!=0) {
		 		sendSmsService.sendSms(otpVerifing.getNumber(),otpVerifing.getEmail(),session);
		 		

		 	}
		 	else {
		 		 registerUserService.sendOtpOnMail(otpVerifing.getEmail(),session);
		 	}
//			 registerUserService.sendOtpOnMail(otpVerifing.getEmail(),session);
//			 System.out.println(session.getAttribute("userEmail")+"-----email");
				//				System.out.println(session.getAttribute("userOtpTime")+"-----userOtpTime");

	       
		 return true;
	}
	
	@PutMapping("/setroletouser")
	public boolean giveRoleToUser(@RequestBody SettingRoleRquest settingRoleRquest) {
		
		return registerUserService.giveRoleToUser(settingRoleRquest);
//		return true;
		
	}

	
	@GetMapping("/getListAsAccountType")
	public ResponseEntity<?> getListAsAccountType(@RequestParam(name = "accountType") String accountType) throws Exception {
		
		List<Map<String,Object>> registerRequestList=new ArrayList<Map<String,Object>>();
		
		registerRequestList=registerUserService.getListAsAccountType(accountType);
		
		 return ResponseEntity.ok(registerRequestList);
		 
	}
	@PostMapping("/addVender")
	public ResponseEntity<?> addVender(@RequestBody VenderRequest venderRequest) throws UserAlreadyExist  {
		
		RegisterUser registerUser =new RegisterUser();
		VenderDetails venderDetails =new VenderDetails();
		
		registerUser.setName(venderRequest.getVenderName());
		registerUser.setNumber(venderRequest.getNumber());
		registerUser.setEmail(venderRequest.getEmail());
		registerUser.setAddress(venderRequest.getAddress());
		registerUser.setCity(venderRequest.getCity());
		registerUser.setZipcode(venderRequest.getPincode());
		registerUser.setCountry(venderRequest.getCountry());
		venderDetails.setAlternateNumber(venderRequest.getAlternateNumber());
		venderDetails.setSuccessURL(venderRequest.getSuccessURL());
		venderDetails.setTerminateURL(venderRequest.getTerminateURL());
		venderDetails.setQuotaFullURL(venderRequest.getQuotaFullURL());	
		venderDetails.setRegisterationNumber(venderRequest.getRegisterationNumber());	
		venderDetails.setPanNumber(venderRequest.getPanNumber());	
		venderDetails.setBankBranchAddress(venderRequest.getBankBranchAddress());	
		venderDetails.setAccountNumber(venderRequest.getAccountNumber());	
		venderDetails.setIfscCode(venderRequest.getIfscCode());	
		venderDetails.setBankAccountType(venderRequest.getAccountType());	
		boolean result=registerUserService.addVender(registerUser, venderDetails);
		
		return ResponseEntity.ok(result);
		
		
	}
	
	@GetMapping("/getRoleGivenByAdmin")
	public ResponseEntity<?> getRoleGivenByAdmin(@RequestParam(name = "userid") Integer userid) throws Exception {
		
		Map<String,String> grtRoles=new HashMap<>();
		String roles=registerUserService.getRoleGivenByAdmin(userid);
		grtRoles.put("roles", roles);
		 return ResponseEntity.ok(grtRoles);
		 
	}
	
	@PostMapping("/giveRoleByAdmin")
	public ResponseEntity<?> giveRoleByAdmin(@RequestBody AddingRolesRequest addingRolesRequest) throws Exception {
		boolean roles=registerUserService.giveRoleByAdmin(addingRolesRequest);
	
		 return ResponseEntity.ok(roles);
		 
	}
	

}
