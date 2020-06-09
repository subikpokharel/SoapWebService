package com.soap.webservices.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.soap.webservices.model.Course;

@Component
public class CourseDetailsService {

	private static List<Course> courses = new ArrayList<>();

	static {
		courses.add(new Course(1, "Spring", "Spring core"));
		courses.add(new Course(2, "Spring MVC", "Spring MVC for Full Stack Development"));
		courses.add(new Course(3, "Spring Boot", "Spring Boot application development"));
		courses.add(new Course(4, "RESTful", "Restful web services"));
		courses.add(new Course(5, "SOAP", "SOA web services"));
	}
	
	public enum Status{
		SUCCESS, FAILURE
	}

	public Course findById(int id) {
		for (Course course : courses) {
			if (course.getId() == id)
				return course;
		}
		return null;
	}

	public List<Course> findAll() {
		return courses;
	}

	public Status deleteById(int id) {
		Iterator<Course> iterator = courses.iterator();
		while (iterator.hasNext()) {
			Course course = iterator.next();
			if (course.getId() == id) {
				iterator.remove();
				return Status.SUCCESS;
			}
		}
		return Status.FAILURE;
	}
}
