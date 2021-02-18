package com.lambdaschool.oktafoundation.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "modules")

public class Module extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long moduleid;

    @NotNull
    private String modulename;

    @NotNull
    private String moduledescription;

    @NotNull
    private String modulecontent;

    @ManyToOne
    @JoinColumn(name = "courseid")
    @JsonIgnoreProperties(value = {"program", "modules"}, allowSetters = true)
    public Course course;

    public Module() {
    }

    public Module(@NotNull String modulename, @NotNull String moduledescription, @NotNull String modulecontent) {
        this.modulename = modulename;
        this.moduledescription = moduledescription;
        this.modulecontent = modulecontent;
    }

    public long getModuleid() {
        return moduleid;
    }

    public void setModuleid(long moduleid) {
        this.moduleid = moduleid;
    }

    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

    public String getModuledescription() {
        return moduledescription;
    }

    public void setModuledescription(String moduledescription) {
        this.moduledescription = moduledescription;
    }

    public String getModulecontent() {
        return modulecontent;
    }

    public void setModulecontent(String modulecontent) {
        this.modulecontent = modulecontent;
    }

    public Course getCourse()
    {
        return course;
    }

    public void setCourse(Course course)
    {
        this.course = course;
    }
}
