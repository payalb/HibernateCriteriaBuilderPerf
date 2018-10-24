package com.java.main;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;

import com.java.dto.Phone;
import com.java.dto.Student;
import com.java.dto.Type;

public class PerformanceDemo {
	static SessionFactory sf;
	static {
		Configuration cfg = new Configuration().addPackage("com.java.dto");
		cfg.configure("hibernate.cfg.xml");
		sf = cfg.buildSessionFactory();
	}

	public static void main(String args[]) {
		PerformanceDemo obj = new PerformanceDemo();
		try {
		//	obj.insertRecords();
			obj.joinFetchDemo();
			//obj.batchDemo();
			/*obj.updateData();
		//	obj.fetchAllData();
			obj.fetchDataById();
			obj.fetchDataById();
			
			obj.deleteData();
			obj.pagination(20, 20);*/
		} finally {
			sf.close();
		}

	}
	
	public void joinFetchDemo() {
	Session s= sf.openSession();
	Query<Student> query=s.createNamedQuery("joinFetch", Student.class);
	query.setParameter("id", 1);
	Student student=query.uniqueResultOptional().orElse(new Student());
	s.close();
	System.out.println(student);
	}
	
	
	//BATCH update
	public void perf2() {
		//for all students >90 : status : inactive
		
	}
	
	
	
	//Batch insert 
	public void perf1() {
		Session s= sf.openSession();
		//insert 100000 record into ur student table?
		
		for(int i=1; i<=100000; i++) {
			
			Student st= new Student();//java obj
			s.save(st);
			if(i%1000==0) {
				s.flush();
				s.clear();
			}
		
		}
		s.getTransaction().commit();
	}
	
	
	
	
	
	
	
	
	
	public void batchDemo() {
		Session s= sf.openSession();
		List<Student> list=s.createQuery("from st").list();
		
		for(Student st: list) {
			System.out.println(st.getPhones());
		}
		s.close();
	}
	public void demoOfVersioning() {
		
	}

/*	private void fetchDataById() {
		int id= 1;
		Session s = sf.openSession();
		
		Criteria c=s.createCriteria(Phone.class);
		//c.setFirstResult(firstResult)
		Criterion cr1= Restrictions.idEq(76_372l);
		c.add(cr1);
		Criterion cr1= Restrictions.idEq(id);
		Criterion cr2= Restrictions.eq("name", "payal");
		Criterion cr3=Restrictions.or(cr1, cr2);
		c.add(cr3);
		Projection p1=Projections.property("student.id");
		Projection p2=Projections.property("student.name");
		ProjectionList list=Projections.projectionList();
		list.add(p1).add(p2);
		c.setProjection(list);
		c.setProjection(Projections.max("id"));
		long result=(long) c.uniqueResult();
		System.out.println(result);
	//	System.out.println(list);
		Query<Student> query=s.createQuery("from st where id = "+ id);
		Optional<Student> st=query.uniqueResultOptional();
		//Optional: prevent null pointer exception
		//get(): give u the data
		//orElse(): u can set the data to be returned if there is no data
		st.orElse(new Student());
		System.out.println(st.get());
		
		NativeQuery<Student> q1=s.createNativeQuery("select * from student_details where id =" + id);
		Optional<Student> st1=q1.uniqueResultOptional();
		st1.orElse(new Student());
		System.out.println(st1.get());
		s.close();
		
	}*/

	public void insertRecords() {
		
		/*"from student": 1-> many: lazy*/
		Student st1 = new Student("payal");
		Student st2 = new Student("ritu");
		Student st3 = new Student("Shreya");
		Student st4 = new Student("Komal");
		Student st5 = new Student("Kapil");
		Phone p1 = new Phone(76_372l, Type.LANDLINE, st1);
		Phone p2 = new Phone(43644l, Type.MOBILE, st2);
		Phone p3 = new Phone(73647l, Type.MOBILE, st2);
		Phone p4 = new Phone(3264524l, Type.MOBILE, st2);
		Phone p5 = new Phone(656434l, Type.MOBILE, st3);
		Phone p6 = new Phone(734675346l, Type.MOBILE, st4);
		Phone p7 = new Phone(6354635l, Type.MOBILE, st5);
		Phone p8 = new Phone(3476765l, Type.MOBILE, st5);
		
		st1.setPhones(Arrays.asList(p1));
		st2.setPhones(Arrays.asList(p2,p3,p4));
		st3.setPhones(Arrays.asList(p5));
		st4.setPhones(Arrays.asList(p6));
		st5.setPhones(Arrays.asList(p7,p8));
		Session s = sf.openSession();
		s.beginTransaction();
		
		s.persist(st1);
		s.persist(st2);
		s.persist(st3);
		s.persist(st4);
		s.persist(st5);
		s.getTransaction().commit();
		s.close();
	}

/*	public void fetchAllData() {
		Session s = sf.openSession();
		Query<Student> query=s.createQuery("from st");
		List<Student> list=query.list();
		System.out.println(list);
	
		Criteria c= s.createCriteria(Student.class);
		List<Student> list= c.list();
		System.out.println(list);
		s.close();
	}
	
	public void updateData() {
		Session s = sf.openSession();
		Query<Student> query=s.createQuery("update st set name= 'ritu' where id = 1 ");
		Transaction t=s.beginTransaction();
		int noOfRowsUpdated=query.executeUpdate();
		Transaction t=s.beginTransaction();
		Student st=s.get(Student.class, 1); //1 payal 
		---- changes are happening in background
		st.setName("kanika");
		t.commit();
		s.close();
	}
	
	
	public void deleteData() {
		Session s= sf.openSession();
		Query<Student> query= s.createQuery("delete from st where id = 1");
		Transaction t=s.beginTransaction();
		int noOfRowsUpdated=query.executeUpdate();
		t.commit();
		System.out.println(noOfRowsUpdated);
		s.close();
		
	}
	1>> 2: 20 | 20 :  2>> 40, 20
	// ?start=index&limit=maxNumber
	public void pagination(int index, int maxNumber) {
		Session s = sf.openSession();
		Query<Student> query=s.createQuery("from st");
		query.setFirstResult(index); //starting from which index
		query.setMaxResults(maxNumber);//no of records to fetch
		List<Student> list=query.list();
		System.out.println(list);
		s.close();
		
	}
	//join queries 
	//subquery using hql
	//params
	public void filterData() {
		
	}*/
}
