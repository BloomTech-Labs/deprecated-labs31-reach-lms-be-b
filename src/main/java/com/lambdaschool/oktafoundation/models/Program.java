package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "programs")
@JsonIgnoreProperties(value = {"courses", "students", "teachers", "admin"}, allowSetters = true)
public class Program extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long programId;

    @NotNull
    @Column(unique = true)
    private String programName;

    @NotNull
    private String programType;

    private String programDescription;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserStudents> students = new HashSet<>();

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserTeachers> teachers = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "userid")
    private User admin;



    public Program() {
    }

    public Program(@NotNull String programName, @NotNull String programType, String programDescription) {
        this.programName = programName;
        this.programType = programType;
        this.programDescription = programDescription;
    }

    public long getProgramId() {
        return programId;
    }

    public void setProgramId(long programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }


    public String getProgramDescription() {
        return programDescription;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    public Set<UserStudents> getStudents() {
        return students;
    }
    public void setStudents(Set<UserStudents> students) {
        this.students = students;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public Set<UserTeachers> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<UserTeachers> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return "Program{" +
                "programId=" + programId +
                ", programName='" + programName + '\'' +
                ", programType='" + programType + '\'' +
                ", programDescription='" + programDescription + '\'' +
                ", courses=" + courses +
                ", students=" + students +
                ", teachers=" + teachers +
                ", admin=" + admin +
                '}';
    }
}
