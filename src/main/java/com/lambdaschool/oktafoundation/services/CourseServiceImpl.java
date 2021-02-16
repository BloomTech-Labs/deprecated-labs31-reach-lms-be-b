package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Course;
import com.lambdaschool.oktafoundation.repository.CourseRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "courseService")
public class CourseServiceImpl implements CourseService
{
    @Autowired
    CourseRespository courseRespository;

    @Override
    public Course fetchCourseById(long courseid)
    {
        return courseRespository.findById(courseid)
                .orElseThrow(() -> new EntityNotFoundException("Course " + courseid + " not found!"));
    }

    @Override
    public Course save(Course course)
    {
        Course newCourse = new Course();

        if (course.getCourseid() != 0)
        {
            courseRespository.findById(course.getCourseid())
                    .orElseThrow(() -> new EntityNotFoundException("Course " + course.getCourseid() + " not Found!"));
            newCourse.setCourseid(course.getCourseid());
        }

        newCourse.setCoursename(course.getCoursename());
        newCourse.setCoursecode(course.getCoursecode());
        newCourse.setCoursedescription(course.getCoursedescription());

        //handling of future relationships right here



        return courseRespository.save(course);
    }

    @Override
    public void deleteCourseById(long courseid)
    {
        courseRespository.deleteById(courseid);
    }

    @Override
    public List<Course> getAllCourses()
    {
        List<Course> rtnList = new ArrayList<>();
        courseRespository.findAll().iterator().forEachRemaining(rtnList::add);

        return rtnList;
    }

    @Override
    public Course edit(Course partiallyEditedCourse)
    {
        Course editedCourse = new Course();

        return editedCourse;
    }
}
