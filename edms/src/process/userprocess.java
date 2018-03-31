package process;

import utils.HibernateSessionFactory;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

import com.entities.User;

import ct.publicfilecont;

public class userprocess {
	/**
	 * judge uid and password,return a flag:0 nobody,1 admin,2 user
	 * @param uid
	 * @param password
	 * return int
	 */
	public int loginjudge(int uid,String password){
		int flag=0;
		List<Integer> rank=new ArrayList<Integer>();
		List<Integer> aid=new ArrayList<Integer>();
		Transaction tx=null;
		try{
		Session session=HibernateSessionFactory.getSession();
		tx=session.beginTransaction();
		String hql="select u.rank from User u where u.id=:uid and u.password=:password";
		rank= session.createQuery(hql).setParameter("uid", uid).setParameter("password", password).list();
		if(rank.size()!=0){
			flag=2;
		}else{
			String hql2="select a.id from Admin a where a.aid=:aid and a.password=:password";
			aid= session.createQuery(hql2).setParameter("aid", uid).setParameter("password", password).list();
			if(aid.size()!=0){
				flag=1;
			}
		}
		tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}finally{
		HibernateSessionFactory.closeSession();
		}
		return flag;
	}
	
	/**
	 * get all users
	 * @return
	 */
	public List<User> loaduser(){
		List<User> users = null;
		Transaction tx=null;
		try{
		Session session=HibernateSessionFactory.getSession();
		tx=session.beginTransaction();
		String hql="FROM User";
		users=session.createQuery(hql).list();
		tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}finally{
		HibernateSessionFactory.closeSession();
		}
		return users;
	}
	
	/**
	 * return department name by did(departmentid)
	 * @param did
	 * @return String
	 */
	public String getDepartmentName(int did){
		String deptname="common";
		Transaction tx=null;
		try{
		Session session=HibernateSessionFactory.getSession();
		tx=session.beginTransaction();
		String hql="select d.dname from Department d where d.did=:id";
		deptname=(String)session.createQuery(hql).setParameter("id", did).uniqueResult();
		tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return deptname;
	}
	
	/**
	 * return all did(departmentid) 
	 * @param did
	 * @return List<Integer>
	 */
	public List<Integer> getalldid(){
		List<Integer> didlist=new ArrayList<Integer>();
		Transaction tx=null;
		try{
		Session session=HibernateSessionFactory.getSession();
		tx=session.beginTransaction();
		String hql="select dep.did from Department dep";
		didlist=session.createQuery(hql).list();
		tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return didlist;
	}	
	
	
	/**
	 * return rank name by rank
	 * @param rank
	 * @return String
	 */
	public String getRankName(int rank){
		String rankna="null";
		if(rank==1)
			rankna="A";
		else if(rank==2)
			rankna="B";
		else if(rank==3)
			rankna="C";
		else if(rank==4)
			rankna="内部";
		else if(rank==5)
			rankna="普通";
		return rankna;
	}
	
	/**
	 * judge user if exists by id
	 * @param id
	 * @return boolean
	 */
	public boolean judgeUserbyid(int id){
		boolean judge=true;
		Transaction tx=null;
		try{
		Session session=HibernateSessionFactory.getSession();
		tx=session.beginTransaction();
		String hql="select u.name FROM User u where u.id=:uid";
		String uname=(String)session.createQuery(hql).setParameter("uid", id).uniqueResult();
		if(uname!=null)
			judge=false;
		else {
			judge=true;
		}
		tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}finally{
		HibernateSessionFactory.closeSession();
		}
		return judge;
	}
	
	/**
	 * search user by user's id(only one)
	 * @param id
	 * @return	User
	 */
	public User getUserbyid(int id){
		User u=new User();
		Transaction tx=null;
		try{
		Session session=HibernateSessionFactory.getSession();
		tx=session.beginTransaction();
		String hql="FROM User u where u.id=:uid";
		List<User> users=session.createQuery(hql).setParameter("uid", id).list();
		u=users.get(0);
		tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}finally{
		HibernateSessionFactory.closeSession();
		}
		return u;
	}
	
	/**
	 * delete user by user's id(0 success,1 fail)
	 * @param id
	 * @return int
	 */
	public int deleteuser(int id){
		int result=0;
		Transaction tx=null;
		try{
		Session session=HibernateSessionFactory.getSession();
		tx=session.beginTransaction();
        User userd=(User)session.load(User.class,id);
        session.delete(userd);
        tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			result=1;
			tx.rollback();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return result;
	}
	
	/**
	 * add user (already check user's id)
	 * @param nu
	 * @return boolean
	 */
	public boolean adduser(User nu){
		boolean aresult=true;
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			session.save(nu);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			aresult=false;
			tx.rollback();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return aresult;
	}
	
	/**
	 * search user by user's id(partial)
	 * @param id
	 * @return
	 */
	public List<User> searchid(int id){
		List<User> users=new ArrayList<User>();
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			String hql="from User u where u.id like '%"+id+"%'";
			users=session.createQuery(hql).list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return users;
	}
	
	/**
	 * search user by user's name(partial)
	 * @param uname
	 * @return List<User>
	 */
	public List<User> searchname(String uname){
		List<User> users=new ArrayList<User>();
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			String hql="from User u where u.name like '%"+uname+"%'";
			users=session.createQuery(hql).list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return users;
	}	
	
	/**
	 * update user's information
	 * @param userg
	 * @return boolean
	 */
	public boolean updateuser(User userg){
		Transaction tx=null;
		boolean a=true;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			session.update(userg);
			tx.commit();
			return a;
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
			a=false;
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return a;
	}	
	
}
