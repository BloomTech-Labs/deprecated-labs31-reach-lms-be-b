package com.lambdaschool.oktafoundation.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "courses")
public class Course
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long courseid;

    private String coursename;
    private String coursecode;
    private String coursedescription;

    @ManyToOne
    @JoinColumn(name="programId")
//    this needs to not be camel case otherwise Spring gets confused
    @JsonIgnoreProperties(value = "courses", allowSetters = true)
    private Program program;

    //relationship to program


    public Course()
    {
    }

    public Course(String coursename, String coursecode, String coursedescription)
    {
        this.coursename = coursename;
        this.coursecode = coursecode;
        this.coursedescription = coursedescription;
    }

    public long getCourseid()
    {
        return courseid;
    }

    public void setCourseid(long courseid)
    {
        this.courseid = courseid;
    }

    public String getCoursename()
    {
        return coursename;
    }

    public void setCoursename(String coursename)
    {
        this.coursename = coursename;
    }

    public String getCoursecode()
    {
        return coursecode;
    }

    public void setCoursecode(String coursecode)
    {
        this.coursecode = coursecode;
    }

    public String getCoursedescription()
    {
        return coursedescription;
    }

    public void setCoursedescription(String coursedescription)
    {
        this.coursedescription = coursedescription;
    }
}
