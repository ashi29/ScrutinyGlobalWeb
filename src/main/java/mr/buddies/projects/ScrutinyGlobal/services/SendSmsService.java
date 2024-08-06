package mr.buddies.projects.ScrutinyGlobal.services;

import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.http.HttpMethod;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SendSmsService {

	
	private final String ACCOUNT_SID="AC462f7907d2bb5579f7f1e2045bf5fc25";
	private final String AUTH_TOKEN="7449f1f8aeaa18372918eb31741e78d2";
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
