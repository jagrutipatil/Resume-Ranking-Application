package resume.ranker.service;

import org.hibernate.Session;
import org.hibernate.Transaction;

import resume.ranker.hibernate.util.HibernateUtil;
import resume.ranker.model.JobProfileConfiguration;

public class SaveConfigurationService {

	public boolean save(JobProfileConfiguration configurationDetails) {

		Session session = HibernateUtil.openSession();
		// if(isUserExists(user)) return false;

		Transaction tx = null;
		try {
			tx = session.getTransaction();
			tx.begin();

			// String sqlQuery = "INSERT INTO JOB_PROFILE_CONFIGURATION (jobId,
			// emailId, jobTitle, keywords) VALUES ('1','2','3','a,b,c')";
			// Query query = session.createQuery(sqlQuery);
			// query.executeUpdate();
			session.save(configurationDetails);
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
