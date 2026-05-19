package com.vinsguru.candidate.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class Candidate {

    @Id
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String location;
    private Integer experienceInYears;
    private List<String> skills;

    @OneToMany
    @JoinColumn(name = "candidateId")
    private List<WorkExperience> workExperiences;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getExperienceInYears() {
        return experienceInYears;
    }

    public void setExperienceInYears(Integer experienceInYears) {
        this.experienceInYears = experienceInYears;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<WorkExperience> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(List<WorkExperience> workExperiences) {
        this.workExperiences = workExperiences;
    }
}
