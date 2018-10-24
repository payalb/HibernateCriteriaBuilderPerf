package com.java.main;

import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.java.dto.Phone;
import com.java.dto.Student;
import com.java.dto.Student_;
import com.java.dto.Type;

public class CachingDemo {
	static SessionFactory sf;
	static {
		Configuration cfg = new Configuration().addPackage("com.java.dto");
		cfg.configure("hibernate.cfg.xml");
		sf = cfg.buildSessionFactory();
	}

	public static void main(String args[]) {
		
		try {
			immutableDemo();
		//	whereDemo();
		//	insertRecords();
		//	softDeleteDemo();
		//	dynamicUpdateDemo();
			//secondLevelCacheDemo();
			//queryCacheDemo();
			//updateData();
			//criteriaUpdateDemo();
		} finally {
			sf.close();
		}
	}

	private static void queryCacheDemo() {
		System.out.println("query cache demo..");
		Session s=sf.openSession();
		Query<Student> q=s.createQuery("from st where id= 1");
		q.setCacheable(true);
		Student st=q.uniqueResult();
		System.out.println(st);
		Query<Student> q1=s.createQuery("from st where id= 1");
		q1.setCacheable(true);
		Student st1=q1.uniqueResult();
		System.out.println(st1);
		s.close();
	}

	private static void dynamicUpdateDemo() {
		Session s=sf.openSession();
		Student st=s.get(Student.class, 1);
		s.beginTransaction();
		st.setMarks(65.4f);
		s.getTransaction().commit();
	}
	private static void firstLevelCacheDemo() {
		Session s=sf.openSession();
		Student s1=s.get(Student.class, 1);//student & phone 
		Student s2=s.get(Student.class, 1);
		System.out.println(s1);System.out.println(s2);
		s.close();
		//1 query fired
	}
	public static void updateData() {
		Session s = sf.openSession();
		Query<Student> query=s.createQuery("update st set name= 'hdsg' where id = 1 ");
		Transaction t=s.beginTransaction();
		query.executeUpdate();
		t.commit();
		s.close();
	}
	private static void secondLevelCacheDemo() {
		//2 queries fired
		Session s=sf.openSession();
		Phone s1=s.get(Phone.class, 76_372l);
		System.out.println(s1);
		s.close();
		Session s3 = sf.openSession();
		Query<Student> query=s3.createQuery("update phone set type= 'mobile' where id = 76372l ");
		Transaction t=s3.beginTransaction();
		query.executeUpdate();
		t.commit();
		s3.close();
	/*	s= sf.openSession();
		Student s2=s.get(Student.class, 1);
		System.out.println(s2);
		s.close();
	*/}
	
	private static void criteriaUpdateDemo() {
		Session s=sf.openSession();
		CriteriaBuilder cb=s.getCriteriaBuilder();
		CriteriaUpdate<Student> cu=cb.createCriteriaUpdate(Student.class);
		Root<Student> root=cu.from(Student.class);
		System.out.println(root.get("id"));
		cu.set(Student_.NAME, "MODIFIED"+root.get("id"));
		cu.where(cb.equal(root.get(Student_.ID), 1));
		s.beginTransaction();
		s.createQuery(cu).executeUpdate();
		s.getTransaction().commit();
		s.close();
	}
	
	public static void insertRecords() {
		Student st1 = new Student("payal");
		Student st2 = new Student("ritu");
		Phone p1 = new Phone(76_372l, Type.LANDLINE, st1);
		Phone p2 = new Phone(7_38_47_476l, Type.MOBILE, st2);
		st1.setPhones(Arrays.asList(p1));
		st1.setPhones(Arrays.asList(p2));
		Session s = sf.openSession();
		s.beginTransaction();
		s.persist(st1);
		s.persist(st2);
		
		s.getTransaction().commit();
		System.out.println(s.get(Student.class, 1));
		s.close();
	}
	
	public static void softDeleteDemo() {
		Session s = sf.openSession();
		s.beginTransaction();
		s.delete(s.load(Student.class, 1));
		s.getTransaction().commit();
		s.close();
	}

	public static void whereDemo() {
		Session s = sf.openSession();
		List<Student> list=s.createQuery("From st").list();
		System.out.println(list);
		s.close();
	}
	
	public static void immutableDemo() {
		Session s = sf.openSession();
		Student st=s.get(Student.class, 2);
		s.beginTransaction();
		st.setMarks(76.5f);
		s.getTransaction().commit();
		System.out.println(s.get(Student.class, 2));
		s.close();
		Session s1 = sf.openSession();
		System.out.println(s1.get(Student.class, 2));
		s1.close();
	}
}
