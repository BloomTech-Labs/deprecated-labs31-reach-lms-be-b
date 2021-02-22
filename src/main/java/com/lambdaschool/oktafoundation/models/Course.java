package com.lambdaschool.oktafoundation.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@JsonIgnoreProperties(value = {"program", "modules"}, allowSetters = true)
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
    private Program program;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Module> modules = new HashSet<>();

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

    public Program getProgram()
    {
        return program;
    }

    public void setProgram(Program program)
    {
        this.program = program;
    }

    public Set<Module> getModules()
    {
        return modules;
    }

    public void setModules(Set<Module> modules)
    {
        this.modules = modules;
    }
}
