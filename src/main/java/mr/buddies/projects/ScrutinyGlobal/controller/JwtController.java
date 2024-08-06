package mr.buddies.projects.ScrutinyGlobal.controller;

import mr.buddies.projects.ScrutinyGlobal.exception.ErrorMsgException;
import mr.buddies.projects.ScrutinyGlobal.helper.JwtUtil;
import mr.buddies.projects.ScrutinyGlobal.helper.SessionStore;
import mr.buddies.projects.ScrutinyGlobal.model.JwtRequest;
import mr.buddies.projects.ScrutinyGlobal.model.JwtResponse;
import mr.buddies.projects.ScrutinyGlobal.services.CustomUserDetailsService;
import mr.buddies.projects.ScrutinyGlobal.services.RegisterUserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
	private RegisterUserService registerUserService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws ErrorMsgException,Exception  {

        System.out.println("Inside Controller");
        System.out.println(jwtRequest);
        try {

            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));


        }catch (BadCredentialsException e)
        {
            e.printStackTrace();
            throw new ErrorMsgException(e.getMessage());
        }
//        UserDetails userDetails=null;
        HttpSession session=SessionStore.getSession();
        System.out.println(session.getAttribute("userAprovel")+"-----userAprovel");
        if(session.getAttribute("userAprovel").equals(0))
        {
        	throw new ErrorMsgException("Waiting for aprovel");
        }
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
		
        //fine area..
      
        String token = this.jwtUtil.generateToken(userDetails);
        System.out.println("JWT " + token);
        new JwtResponse(token);
        Map<String,Object> userdetails=  registerUserService.getUserDetails(jwtRequest.getUsername());
//        Map<String,Object> getToken=new HashMap<String,Object>();
//        getToken.put("token", token);
        //{"token":"value"}
        Map<String,Object> result=new HashMap<String,Object>(); 
        result.put("userName",userdetails.get("name"));
        result.put("roles",userdetails.get("roles"));
        result.put("email",userdetails.get("email"));
        result.put("user_id",userdetails.get("user_id"));
        result.put("token", token);

        return ResponseEntity.ok(result);

    }
}
