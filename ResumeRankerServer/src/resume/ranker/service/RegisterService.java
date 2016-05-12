package resume.ranker.service;

import org.hibernate.Session;
import org.hibernate.Transaction;

import resume.ranker.hibernate.util.HibernateUtil;
import resume.ranker.model.User;

public class RegisterService {
	
	public boolean register(User user){
	     Session session = HibernateUtil.openSession();
	     //if(isUserExists(user)) return false;  
	     
	     Transaction tx = null;
	     try {
	         tx = session.getTransaction();
	         tx.begin();
	         session.save(user);       
	         tx.commit();
	     } catch (Exception e) {
	         if (tx != null) {
	             tx.rollback();
	         }
	         e.printStackTrace();
	     } finally {
	         session.close();
	     } 
	     return true;
	}
	
}
