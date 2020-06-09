package com.soap.webservices.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.soap.courses.CourseDetails;
import com.soap.courses.DeleteCourseDetailsRequest;
import com.soap.courses.DeleteCourseDetailsResponse;
import com.soap.courses.GetAllCourseDetailsRequest;
import com.soap.courses.GetAllCourseDetailsResponse;
import com.soap.courses.GetCourseDetailsRequest;
import com.soap.courses.GetCourseDetailsResponse;
import com.soap.webservices.exception.CourseNotFoundException;
import com.soap.webservices.model.Course;
import com.soap.webservices.service.CourseDetailsService;
import com.soap.webservices.service.CourseDetailsService.Status;

@Endpoint
public class CourseDetailsEndpoint {

	@Autowired
	CourseDetailsService courseDetailService;
	
	@PayloadRoot(namespace = "http://soap.com/courses", localPart = "GetCourseDetailsRequest")
	@ResponsePayload
	public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {
		
		Course course = courseDetailService.findById(request.getId());
		if(course == null)
			throw new CourseNotFoundException("Invalid Course Id " + request.getId());
		return mapCourseDetails(course);
	}
	
	@PayloadRoot(namespace = "http://soap.com/courses", localPart = "GetAllCourseDetailsRequest")
	@ResponsePayload
	public GetAllCourseDetailsResponse processAllCourseDetailsRequest(@RequestPayload GetAllCourseDetailsRequest request) {
		
		List<Course> courses = courseDetailService.findAll();
		return mapAllCourseDetails(courses);
	}
	
	@PayloadRoot(namespace = "http://soap.com/courses", localPart = "DeleteCourseDetailsRequest")
	@ResponsePayload
	public DeleteCourseDetailsResponse deleteCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request) {
		DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
		Status status = courseDetailService.deleteById(request.getId());
		response.setStatus(mapStatus(status));
		return response;
	}
	

	private com.soap.courses.Status mapStatus(Status status) {
		if (status == Status.FAILURE)
			return com.soap.courses.Status.FAILED;
		return com.soap.courses.Status.SUCCESS;
	}

	private GetCourseDetailsResponse mapCourseDetails(Course course) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();
		response.setCourseDetails(mapCourse(course));
		return response;
	}
	
	private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
		GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
		for (Course course : courses) {
			CourseDetails mapCourse = mapCourse(course);
			response.getCourseDetails().add(mapCourse);
		}
		return response;
	}
	
	private CourseDetails mapCourse(Course course) {
		CourseDetails courseDetails = new CourseDetails();
		courseDetails.setId(course.getId());
		courseDetails.setName(course.getName());
		courseDetails.setDescription(course.getDescription());
		return courseDetails;
	}
}




