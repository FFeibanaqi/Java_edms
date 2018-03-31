package process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;






import org.hibernate.Session;
import org.hibernate.Transaction;

import utils.HibernateSessionFactory;

import com.entities.Document;
import com.entities.Uoperate;
import com.entities.User;

public class documentprocess {
	/**
	 * load all original documemts by user's id
	 * @param uid
	 * @return List<Document>
	 */
	public List<Document> loaddocument(int uid){
		List<Document> list=new ArrayList<Document>();
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			String hql="FROM Document doc where doc.no in (select uop.no from Uoperate uop where uop.id=:userid)";
			list=session.createQuery(hql).setParameter("userid", uid).list();
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}
		return list;
	}
	
	/**
	 * record user's uploading file action
	 * @param uid
	 * @param name
	 * @param type
	 * @param size
	 * @return null
	 */
	public void useruploadfile(int uid,String name,String type,Double size){
		
		Document dm=new Document();
		dm.setName(name);
		dm.setType(type);
		dm.setSize(size);
		
		Uoperate up=new Uoperate();
		up.setId(uid);
		up.setUtype(5);//5 upload
		
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			session.save(dm);
			String hql="select d.no from Document d where d.name=:dname and d.type=:dtype";
			int n=(int)session.createQuery(hql).setParameter("dname",name).setParameter("dtype",type).uniqueResult();
			up.setNo(n);
			session.save(up);
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}
	}
	
	/**
	 * get the complete document's name by document's no
	 * @param no
	 * @return String
	 */
	public String getfilename(int no){
		String filename=null;
		String fname=null;
		String fsuffix=null;
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			String hqla="select d.name from Document d where d.no=:dno";
			fname=(String)session.createQuery(hqla).setParameter("dno",no).uniqueResult();
			String hqlb="select d.type from Document d where d.no=:dno";
			fsuffix=(String)session.createQuery(hqlb).setParameter("dno",no).uniqueResult();
			filename=fname+fsuffix;
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}
		return filename;
	}
	
	/**
	 * get document's suffix name by document's no
	 * @param docno
	 * @return String
	 */
	public String getsuffix(int docno){
		String suffix="";
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			String hqlb="select d.type from Document d where d.no=:dno";
			suffix=(String)session.createQuery(hqlb).setParameter("dno",docno).uniqueResult();
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}
		return suffix;
	}
	
	//record user's deleting file action
	public void userdeletefile(int uid,int docno){
		List<Document> list=new ArrayList<Document>();
		Document dm=new Document();
		Uoperate up=new Uoperate();
		up.setId(uid);
		up.setUtype(2);//2 delete
		up.setNo(docno);
			
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			session.save(up);
			String hql="from Document d where d.no=:dno";
			list=session.createQuery(hql).setParameter("dno",docno).list();
			dm=list.get(0);
			session.delete(dm);
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}
	}
	
	/**
	 * user searches original document by document's name or suffix(partial)
	 * @param uid
	 * @param dname
	 * @return	List<Document>
	 */
	public List<Document> searchdocname(int uid,String dname){
		List<Document> docs=new ArrayList<Document>();
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			String hql="from Document d where d.name like '%"+dname+"%' and d.no in (select uop.no from Uoperate uop where uop.id=:userid)";
			docs=session.createQuery(hql).setParameter("userid", uid).list();
			String hql2="from Document d where d.type like '%"+dname+"%' and d.no in (select uop.no from Uoperate uop where uop.id=:userid)";
			List<Document> temp=session.createQuery(hql2).setParameter("userid", uid).list();
			for(Document d:temp){
				docs.add(d);
			}
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return docs;
	}
	
	/**
	 * user gets unempowered document by user's id
	 * @param uid
	 * @return List<Document>
	 */
	public List<Document> loadunempower(int uid){
		List<Document> list=new ArrayList<Document>();
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			String hql="FROM Document doc where doc.dstate=0 and doc.no in (select uop.no from Uoperate uop where uop.id=:userid)";
			list=session.createQuery(hql).setParameter("userid", uid).list();
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}
		return list;
	}
	
	/**
	 * user gets empowered document by user's id
	 * @param uid
	 * @return List<Document>
	 */
	public List<Document> loadempower(int uid){
		List<Document> list=new ArrayList<Document>();
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			String hql="FROM Document doc where doc.dstate=1 and doc.no in (select uop.no from Uoperate uop where uop.id=:userid)";
			list=session.createQuery(hql).setParameter("userid", uid).list();
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}
		return list;
	}
	
	/**
	 * user search unempowered document by document's name(partial)
	 * @param uid
	 * @param dname
	 * @return List<Document>
	 */
	public List<Document> searchunempower(int uid,String dname){
		List<Document> list=new ArrayList<Document>();
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			String hql="FROM Document doc where doc.dstate=0 and doc.name like '%"+dname+"%' and doc.no in (select uop.no from Uoperate uop where uop.id=:userid)";
			list=session.createQuery(hql).setParameter("userid", uid).list();
			String hql2="FROM Document doc where doc.dstate=0 and doc.type like '%"+dname+"%' and doc.no in (select uop.no from Uoperate uop where uop.id=:userid)";
			List<Document> temp=session.createQuery(hql2).setParameter("userid", uid).list();
			for(Document document:temp){
				list.add(document);
			}
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}
		return list;
	}	
	
	/**
	 * user search empowered document by document's name(partial)
	 * @param uid
	 * @param dname
	 * @return List<Document>
	 */
	public List<Document> searchempower(int uid,String dname){
		List<Document> list=new ArrayList<Document>();
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			String hql="FROM Document doc where doc.dstate=1 and doc.name like '%"+dname+"%' and doc.no in (select uop.no from Uoperate uop where uop.id=:userid)";
			list=session.createQuery(hql).setParameter("userid", uid).list();
			String hql2="FROM Document doc where doc.dstate=1 and doc.type like '%"+dname+"%' and doc.no in (select uop.no from Uoperate uop where uop.id=:userid)";
			List<Document> temp=session.createQuery(hql2).setParameter("userid", uid).list();
			for(Document document:temp){
				list.add(document);
			}
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}
		return list;
	}	
	
	/**
	 * get document by document's no
	 * @param docno
	 * @return Document
	 */
	public Document getdocument(int docno){
		List<Document> list=new ArrayList<Document>();
		Document document=new Document();
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			String hql="FROM Document doc where doc.no=:docno";
			list=session.createQuery(hql).setParameter("docno", docno).list();
			document=list.get(0);
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}
		return document;
	}
	
	/**
	 * user update document's empower information
	 * @param uid
	 * @param document
	 * @param utype
	 * @return null
	 */
	public void updatedocument(int uid,Document document,int utype){
		Uoperate uoperate=new Uoperate();
		uoperate.setId(uid);
		uoperate.setUtype(utype);
		uoperate.setNo(document.getNo());
		
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			session.update(document);
			session.save(uoperate);
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}
	}
	
	/**
	 * user cancel document's empower and record this action
	 * @param uid
	 * @param document
	 * @return null
	 */
	public void cancelempower(int uid,Document document){
		documentprocess dp=new documentprocess();
		document.setDstate(0);//0 means unempower
		
		Uoperate uoperate=new Uoperate();
		uoperate.setId(uid);
		uoperate.setNo(document.getNo());
		uoperate.setUtype(7);//7 means cancel document's empower
		
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			session.update(document);
			session.save(uoperate);
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}
	}
	
	/**
	 * judge if document is empowered and return its empower rank(in order to get old empower rank,60+)
	 * @param docno
	 * @return int
	 */
	public int getempowerrank(int docno){
		int result=60;
		int dstate=0;
		
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			String hql="select d.dstate from Document d where d.no=:docno";
			dstate=(int) session.createQuery(hql).setParameter("docno", docno).uniqueResult();
			if(dstate==1){
				String hql2="select uo.utype from Uoperate uo where uo.uno=(select max(uo.uno) from uo where uo.utype>10 and uo.no=:docno)";
				result=(int)session.createQuery(hql2).setParameter("docno",docno).uniqueResult();
			}
			
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}		
		return result;
	}
	
	/**
	 * get all possible document from department by user's id
	 * @param uid
	 * @return List<Document>
	 */
	public List<Document> getdepartmentdocument(int uid){
		List<Document> documents=new ArrayList<Document>();
		userprocess up=new userprocess();
		User user=up.getUserbyid(uid);
		int rank=user.getRank()+60;
		int did=user.getDid();
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			String hql="from Document doc where doc.dstate=1 and doc.no in (select uo.no from Uoperate uo where "
					+ "uo.utype>=:rank and uo.utype>10 and uo.id in (select user.id from User user where user.did=:did))";
			documents=session.createQuery(hql).setParameter("rank", rank).setParameter("did", did).list();
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}
		return documents;
	}
	
	/**
	 * search public document from department by user's id and document's name(partial)
	 * @param uid
	 * @param dname
	 * @return List<Document>
	 */
	public List<Document> searchdepartmentdocument(int uid,String dname){
		List<Document> documents=new ArrayList<Document>();
		userprocess up=new userprocess();
		User user=up.getUserbyid(uid);
		int rank=user.getRank()+60;
		int did=user.getDid();
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			String hql="from Document doc where doc.dstate=1 and "
					+ "doc.name like '%"+dname+"%' and doc.no in (select uo.no from Uoperate uo where "
					+ "uo.utype>=:rank and uo.utype>10 and uo.id in (select user.id from User user where user.did=:did))";
			documents=session.createQuery(hql).setParameter("rank", rank).setParameter("did", did).list();
			String hql2="from Document doc where doc.dstate=1 and "
					+ "doc.type like '%"+dname+"%' and doc.no in (select uo.no from Uoperate uo where "
					+ "uo.utype>=:rank and uo.utype>10 and uo.id in (select user.id from User user where user.did=:did))";
			List<Document> temp=session.createQuery(hql2).setParameter("rank", rank).setParameter("did", did).list();
			for(Document document:temp){
				documents.add(document);
			}
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}
		return documents;
	}	
	
	/**
	 * get user by document's no
	 * @param docno
	 * @return User
	 */
	public User getuserbydocno(int docno){
		User user=new User();
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			String hql="from User u where u.id=(select uo.id from Uoperate uo where uo.uno=("
					+ "select max(uo.uno) from uo where uo.utype=5 and uo.no=:docno))";
			user= (User) session.createQuery(hql).setParameter("docno", docno).uniqueResult();
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}
		return user;
	}
	
	/**
	 * user open public document,but do not record this action
	 * @param docno
	 * @return null
	 */
	public void useropenpublicfile(int docno){
		documentprocess dp=new documentprocess();
		Document dm=dp.getdocument(docno);
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			int newfrequency=dm.getFrequency()-1;//frequency-1
			dm.setFrequency(newfrequency);
			session.update(dm);
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}
	}	
	
	/**
	 * user edit public document and record this action
	 * @param uid
	 * @param docno
	 * @return null
	 */
	public void usereditpublicfile(int uid,int docno){
		documentprocess dp=new documentprocess();
		Document dm=dp.getdocument(docno);
		
		Uoperate uoperate=new Uoperate();
		uoperate.setId(uid);
		uoperate.setNo(docno);
		uoperate.setUtype(1);//1 means edit document
		
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			int newfrequency=dm.getFrequency()-1;//frequency-1
			dm.setFrequency(newfrequency);
			session.update(dm);
			session.save(uoperate);
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}
	}	
	
	/**
	 * user download public document and record this action
	 * @param uid
	 * @param docno
	 * @return null
	 */
	public void userdownloadpublicfile(int uid,int docno){
		
		Uoperate uoperate=new Uoperate();
		uoperate.setId(uid);
		uoperate.setNo(docno);
		uoperate.setUtype(4);//4 means download document
		
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			session.save(uoperate);
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}
	}	
	
	
	/**
	 * either time or frequency runs out,document is deleted automatically and record this action
	 * @param uid
	 * @param docno
	 * @return null
	 */
	public void timerunsout(int uid,int docno){
		documentprocess dp=new documentprocess();
		Document dm=dp.getdocument(docno);
		
		Uoperate uoperate=new Uoperate();
		uoperate.setId(uid);
		uoperate.setNo(docno);
		uoperate.setUtype(8);//8 means time runs out
		
		Transaction tx=null;
		try{
			Session session=HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			dm.setDstate(0);//document is deleted,in other words,it's unempowered
			session.update(dm);
			session.save(uoperate);
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}finally{
			HibernateSessionFactory.closeSession();
			}
	}	
	
	
	
}
