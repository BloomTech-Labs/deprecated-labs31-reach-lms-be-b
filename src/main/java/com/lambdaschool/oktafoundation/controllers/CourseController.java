package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Course;
import com.lambdaschool.oktafoundation.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "/courses")
@RestController
public class CourseController
{
    @Autowired
    private CourseService courseService;

    @GetMapping(value = "/course/{courseid}", produces = "application/json")
    public ResponseEntity<?> fetchSingleCourse(@PathVariable long courseid) throws Exception
    {
        Course course = courseService.fetchCourseById(courseid);

        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @GetMapping(value = "/courses", produces = "application/json")
    public ResponseEntity<?> fetchAllCourses()
    {
        List<Course> coursesList = courseService.getAllCourses();

        return new ResponseEntity<>(coursesList, HttpStatus.OK);
    }

    @GetMapping(value = "/course/{courseid}/modules", produces = "application/json")
    public ResponseEntity<?> fetchCourseModules(@PathVariable long courseid)
    {
        Course c = courseService.fetchCourseById(courseid);
        return new ResponseEntity<>(c.getModules(), HttpStatus.OK);
    }

    @PostMapping(value = "/course", consumes = "application/json")
    public ResponseEntity<?> postCourse(@RequestBody @Valid Course newCourse) throws Exception
    {
        newCourse.setCourseid(0);
        courseService.save(newCourse);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/course/{courseid}", consumes = "application/json")
    public ResponseEntity<?> putCourse(@PathVariable long courseid, @RequestBody @Valid Course updatedCourse) throws Exception
    {
        updatedCourse.setCourseid(courseid);
        courseService.save(updatedCourse);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/course/{courseid}", consumes = "application/json")
    public ResponseEntity<?> patchCourse(@PathVariable long courseid, @RequestBody Course partiallyEditedCourse) throws Exception
    {
        partiallyEditedCourse.setCourseid(courseid);
        courseService.edit(partiallyEditedCourse);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/course/{courseid}")
    public ResponseEntity<?> deleteSingleCourse(@PathVariable long courseid)
    {
        courseService.deleteCourseById(courseid);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
