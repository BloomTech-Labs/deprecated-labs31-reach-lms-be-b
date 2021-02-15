package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Course;
import com.lambdaschool.oktafoundation.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CourseController
{
    @Autowired
    private CourseService courseService;

    //temporary endpoint for testing.
    @GetMapping(value = "/courses/{courseid}", produces = "application/json")
    public ResponseEntity<?> fetchSingleCourse(@PathVariable long courseid) throws Exception
    {
        Course course = courseService.fetchCourseById(courseid);

        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PostMapping(value = "/courses", consumes = "application/json")
    public ResponseEntity<?> postCourse(@RequestBody @Valid Course newCourse)
    {
        newCourse.setCourseid(0);
        courseService.save(newCourse);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/courses/{courseid}", consumes = "application/json")
    public ResponseEntity<?> putCourse(@PathVariable long courseid, @RequestBody @Valid Course updatedCourse)
    {
        updatedCourse.setCourseid(courseid);
        courseService.save(updatedCourse);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/courses/{courseid}")
    public ResponseEntity<?> deleteSingleCourse(@PathVariable long courseid)
    {
        courseService.deleteCourseById(courseid);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
