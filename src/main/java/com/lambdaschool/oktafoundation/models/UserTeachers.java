package com.lambdaschool.oktafoundation.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "userteachers")
@IdClass(UserTeachersId.class)
public class UserTeachers extends Auditable implements Serializable
{
    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = {"adminPrograms", "studentPrograms", "teacherPrograms"}, allowSetters = true)
    private User teacher;
    @Id
    @ManyToOne
    @JoinColumn(name = "programid")
    @JsonIgnoreProperties(value = "users", allowSetters = true)
    private Program program;

    public UserTeachers() {
    }

    public UserTeachers(User teacher, Program program)
    {
        this.teacher = teacher;
        this.program = program;
    }

    public User getTeacher()
    {
        return teacher;
    }
    public void setTeacher(User teacher)
    {
        this.teacher = teacher;
    }
    public Program getProgram()
    {
        return program;
    }
    public void setProgram(Program program)
    {
        this.program = program;
    }
    @Override
    public int hashCode() {
        return 37;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserTeachers)) {
            return false;
        }
        UserTeachers that = (UserTeachers) o;
        return ((teacher == null) ? 0 : teacher.getUserid()) == ((that.teacher == null) ? 0 : that.teacher.getUserid()) &&
                ((program == null) ? 0 : program.getProgramId()) ==
                        ((that.program == null) ? 0 : that.program.getProgramId());
    }
}