package com.lambdaschool.oktafoundation.models;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
@Embeddable
public class UserTeachersId implements Serializable
{
    private long teacher;
    private long program;
    public UserTeachersId()
    {
    }
    public UserTeachersId(long teacher, long program)
    {
        this.teacher = teacher;
        this.program = program;
    }
    public long getTeacher()
    {
        return teacher;
    }
    public void setTeacher(long teacher)
    {
        this.teacher = teacher;
    }
    public long getProgram()
    {
        return program;
    }
    public void setProgram(long program)
    {
        this.program = program;
    }
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTeachersId that = (UserTeachersId) o;
        return getTeacher() == that.getTeacher() && getProgram() == that.getProgram();
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(getTeacher(), getProgram());
    }
}