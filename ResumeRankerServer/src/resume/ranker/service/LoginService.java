package resume.ranker.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import resume.ranker.hibernate.util.HibernateUtil;
import resume.ranker.model.User;

public class LoginService {

	public boolean authenticateUser(String emailId, String password) {
		User user = getUserByUserId(emailId);
		if (user != null && user.getEmailId().equals(emailId) && user.getPassword().equals(password)) {
			return true;
		} else {
			return false;
		}
	}

	public User getUserByUserId(String emailId) {
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		User user = null;
		try {
			tx = session.getTransaction();
			tx.begin();
			String sqlQuery = "from User WHERE emailId='" + emailId + "'";
			Query query = session.createQuery(sqlQuery);
			user = (User) query.uniqueResult();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;
	}

}
