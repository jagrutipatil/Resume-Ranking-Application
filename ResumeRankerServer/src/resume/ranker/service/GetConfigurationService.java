package resume.ranker.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import resume.ranker.hibernate.util.HibernateUtil;
import resume.ranker.model.JobProfileConfiguration;

public class GetConfigurationService {

	public List<JobProfileConfiguration> getConfigurationDetails(String emailId) {
		List<JobProfileConfiguration> jobProfileConfigurationList = getConfigurationByemailId(emailId);
		if (jobProfileConfigurationList != null) {
			return jobProfileConfigurationList;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<JobProfileConfiguration> getConfigurationByemailId(String emailId) {
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		List<JobProfileConfiguration> jobProfileConfigurationList = null;
		try {
			tx = session.getTransaction();
			tx.begin();
			String sqlQuery = "from JobProfileConfiguration WHERE emailId='" + emailId + "'";
			Query query = session.createQuery(sqlQuery);
			jobProfileConfigurationList = query.list();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		System.out.println("Size of congiguration: " + jobProfileConfigurationList.size());
		return jobProfileConfigurationList;
	}

}
