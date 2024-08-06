package mr.buddies.projects.ScrutinyGlobal.services;

import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.http.HttpMethod;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SendSmsService {

	@Value("${twilio.account.sid}")
	private String ACCOUNT_SID;
	@Value("${twilio.auth.token}")
	private String AUTH_TOKEN;
	private final String SENDER="+17206276838";
	

	public void sendSms(String reciver,String email,HttpSession session) {
		
		try {
		 Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		 PhoneNumber to = new PhoneNumber(reciver);//to
		 PhoneNumber from = new PhoneNumber(SENDER); // from
		 Integer otp = generateOTP();
			String otpMessage = "Dear Customer , Your OTP is  " + otp + " for Scrutiny Global Account . Thank You.";
			Message message = Message
			        .creator(to, from,
			                otpMessage)
			        .create();

	        System.out.println(message);
	        System.out.println(message.getSid());
	        session.setAttribute("userEmail", email);
			session.setAttribute("userOtp", otp);
			session.setAttribute("userOtpTime", new Date());

		}catch(Exception e) {
			
			e.printStackTrace();
			
		}
		}
		
		private Integer generateOTP() {
			 Random random = new Random();
				int newOTP=1000 + random.nextInt(9000);		
		return newOTP;
		
		}
}
