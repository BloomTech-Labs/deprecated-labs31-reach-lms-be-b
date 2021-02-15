package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Course;

public interface CourseService
{
    Course fetchCourseById(long courseid) throws Exception;

    Course save(Course course);

    void deleteCourseById(long courseid);
}
