/*package resume.ranker.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import resume.ranker.hibernate.util.HibernateUtil;
import resume.ranker.model.JobProfileConfiguration;

public class GetConfigurationService {
	
	public boolean getConfiguration(String jobId) {
        JobProfileConfiguration jobProfileConfiguration = getConfigurationByJobId(jobId);         
        if(user!=null && user.getEmailId().equals(emailId) && user.getPassword().equals(password)){
            return true;
        }else{
            return false;
        }
    }
 
    public JobProfileConfiguration getConfigurationByJobId(String jobId) {
        Session session = HibernateUtil.openSession();
        Transaction tx = null;
        JobProfileConfiguration jobProfileConfiguration = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            String sqlQuery = "from JobProfileConfiguration WHERE jobId='" + jobId + "'";
            Query query = session.createQuery(sqlQuery);
            jobProfileConfiguration = (JobProfileConfiguration)query.uniqueResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return jobProfileConfiguration;
    }

}
*/