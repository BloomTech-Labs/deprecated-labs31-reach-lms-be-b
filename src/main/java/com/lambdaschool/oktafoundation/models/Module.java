package com.lambdaschool.oktafoundation.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "modules")
@JsonIgnoreProperties(value = {"course"}, allowSetters = true)
public class Module extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long moduleId;

    @NotNull
    private String moduleName;

    @NotNull
    private String moduleDescription;

    @NotNull
    private String moduleContent;

    @ManyToOne
    @JoinColumn(name = "courseid")
    public Course course;

    public Module() {
    }

    public Module(@NotNull String moduleName, @NotNull String moduleDescription, @NotNull String moduleContent) {
        this.moduleName = moduleName;
        this.moduleDescription = moduleDescription;
        this.moduleContent = moduleContent;
    }

    public long getModuleId() {
        return moduleId;
    }

    public void setModuleId(long moduleid) {
        this.moduleId = moduleid;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String modulename) {
        this.moduleName = modulename;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String moduledescription) {
        this.moduleDescription = moduledescription;
    }

    public String getModuleContent() {
        return moduleContent;
    }

    public void setModuleContent(String modulecontent) {
        this.moduleContent = modulecontent;
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
