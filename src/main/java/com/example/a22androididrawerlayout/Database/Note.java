package com.example.a22androididrawerlayout.Database;
// 1

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note
{
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String nameStudent;
    private String yearBirth;
    private String homeTown;
    private int priority;

    public Note()
    {

    }
    @Ignore
    public Note(String nameStudent, String yearBirth, String homeTown, int priority) {
        this.nameStudent = nameStudent;
        this.yearBirth = yearBirth;
        this.homeTown = homeTown;
        this.priority = priority;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public String getYearBirth() {
        return yearBirth;
    }

    public void setYearBirth(String yearBirth) {
        this.yearBirth = yearBirth;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
