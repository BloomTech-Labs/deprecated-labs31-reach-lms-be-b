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
    @JsonIgnoreProperties(value = "modules", allowSetters = true)
    public Course course;

    public Module() {
    }

    public Module(@NotNull String modulename, @NotNull String moduledescription, @NotNull String modulecontent) {
        this.modulename = modulename;
        this.moduledescription = moduledescription;
        this.modulecontent = modulecontent;
    }

    public long getModuleId() {
        return moduleid;
    }

    public void setModuleId(long moduleid) {
        this.moduleid = moduleid;
    }

    public String getModuleName() {
        return modulename;
    }

    public void setModuleName(String modulename) {
        this.modulename = modulename;
    }

    public String getModuleDescription() {
        return moduledescription;
    }

    public void setModuleDescription(String moduledescription) {
        this.moduledescription = moduledescription;
    }

    public String getModuleContent() {
        return modulecontent;
    }

    public void setModuleContent(String modulecontent) {
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
