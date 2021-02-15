package com.lambdaschool.oktafoundation.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Program extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long programId;

    @NotNull
    @Column(unique = true)
    private String programName;

    @NotNull
    private String getProgramType;

    private String programDescription;

    public Program() {
    }

    public Program(@NotNull String programName, @NotNull String getProgramType, String programDescription) {
        this.programName = programName;
        this.getProgramType = getProgramType;
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

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getGetProgramType() {
        return getProgramType;
    }

    public void setGetProgramType(String getProgramType) {
        this.getProgramType = getProgramType;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }
}
