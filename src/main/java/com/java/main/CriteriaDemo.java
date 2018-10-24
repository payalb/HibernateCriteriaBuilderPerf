package com.java.main;

import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.java.dto.Phone;
import com.java.dto.Student;
import com.java.dto.Student_;
import com.java.dto.Type;

public class CriteriaDemo {
	static SessionFactory sf;
	static {
		Configuration cfg = new Configuration().addPackage("com.java.dto");
		cfg.configure("hibernate.cfg.xml");
		sf = cfg.buildSessionFactory();
	}

	
	public static void getAllStudents() {
		Session s= sf.openSession();
		CriteriaBuilder cb=s.getCriteriaBuilder();
		
		CriteriaUpdate<Student> cu=cb.createCriteriaUpdate(Student.class);
		Root<Student> root=cu.from(Student.class);
		//cu.set(root.get(Student_.NAME), "Modified");
		//Return type
	
		cu.where(cb.equal(root.get(Student_.ID), 2));
		s.createQuery(cu).executeUpdate();
		s.close();
	}
	//phones of a student where id =2
	public static void main(String args[]) {
		
		try {
		/*	insertRecords();
			getStudents();
			getStudentById();
			getStudentByPhoneId();
			getStudentIdByPhoneIdUsingCriteria();
		*/
			getAllStudents();
		} finally {
			sf.close();
		}
	}

	
	public static void getStudents() {
		Session s = sf.openSession();
		CriteriaBuilder cb=s.getCriteriaBuilder();
		//To specify result type
		CriteriaQuery<Student> cq=cb.createQuery(Student.class);
		Root<Student> root=cq.from(Student.class);
		cq.select(root);
		List<Student> list=s.createQuery(cq).getResultList();
		System.out.println(list);
		s.close();
	}
	
	public static void getStudentById() {
		Session s = sf.openSession();
		CriteriaBuilder cb=s.getCriteriaBuilder();
		//To specify result type
		CriteriaQuery<Student> cq=cb.createQuery(Student.class);
		Root<Student> root=cq.from(Student.class);
		cq.select(root);
		cq.where(cb.equal(root.get(Student_.id), 1));
		List<Student> list=s.createQuery(cq).getResultList();
		System.out.println(list);
		s.close();
	}
	
	public static void getStudentByPhoneId() {
		Session s = sf.openSession();
		CriteriaBuilder cb=s.getCriteriaBuilder();
		//To specify result type
		CriteriaQuery<Phone> cq=cb.createQuery(Phone.class);
		Root<Student> root=cq.from(Student.class);
		cq.select(root.get(Student_.PHONES));
		cq.where(cb.equal(root.get(Student_.ID), 1));
		List<Phone> phones=s.createQuery(cq).getResultList();
		System.out.println(phones);
		s.close();
	}
	
	public static void getStudentIdByPhoneIdUsingCriteria() {
		Session s = sf.openSession();
		Criteria c=s.createCriteria(Phone.class);
		c.setProjection(Projections.property("student.id"));
		//Phone table only stores student id
		//c.setProjection(Projections.property("student.stName"));
		c.add(Restrictions.idEq(76_372l));
		List list=c.list();
		System.out.println(list);
		s.close();
	}
	

	
	public static void insertRecords() {
		Student st1 = new Student("payal");
		Student st2 = new Student("ritu");
		Phone p1 = new Phone(76_372l, Type.LANDLINE, st1);
		Phone p2 = new Phone(7_38_47_476l, Type.MOBILE, st1);
		Phone p3 = new Phone(65434l, Type.MOBILE, st2);
		Session s = sf.openSession();
		s.beginTransaction();
		st1.setPhones(Arrays.asList(p1, p2));
		st2.setPhones(Arrays.asList(p3));
		s.persist(st1);
		s.persist(st2);
		
		s.getTransaction().commit();
		s.close();
	}

}
