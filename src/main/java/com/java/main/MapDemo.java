/*package com.java.main;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.java.dto.Standard;
import com.java.dto.Student;

https://stackoverflow.com/questions/7578231/howto-persist-a-mapentity-integer-with-jpa
 * https://stackoverflow.com/questions/4849554/persist-a-mapinteger-float-with-jpa
 * http://docs.jboss.org/hibernate/core/3.6/reference/en-US/html/collections.html#collections-ofvalues

public class MapDemo {
	static SessionFactory sf;
	static {
		Configuration cfg = new Configuration().addPackage("com.java.dto");
		cfg.configure("hibernate.cfg.xml");
		sf = cfg.buildSessionFactory();
	}

	public static void main(String[] args) {
		Standard standard= new Standard();
		Student st= new Student("Payal");
		Student st1= new Student("Ritu");
		Map<Student , Integer> map= new HashMap<>();
		
		Session s= sf.openSession();
		s.save(st);
		s.save(st1);
		s.flush();
		s.getTransaction().commit();
		map.put(st, 22);
		map.put(st1, 28);
		standard.setStudents(map);
		s.save(standard);
		s.close();
	}
}
*/