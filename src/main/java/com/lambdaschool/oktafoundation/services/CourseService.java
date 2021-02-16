package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Course;

import java.util.List;

public interface CourseService
{
    Course fetchCourseById(long courseid) throws Exception;

    Course save(Course course);

    void deleteCourseById(long courseid);

    List<Course> getAllCourses();

    Course edit(Course partiallyEditedCourse);
}
