package mr.buddies.projects.ScrutinyGlobal.helper;

import java.io.Serializable;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class SixDigitIdGenerator implements IdentifierGenerator  {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Random random = new Random();
        return 100000 + random.nextInt(900000); 
	}

}
