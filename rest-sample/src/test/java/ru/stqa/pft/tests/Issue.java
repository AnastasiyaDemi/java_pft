package ru.stqa.pft.tests;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Issue {

    private int id;
    private String subject;
    private String description;

    @SerializedName("state_name")
    private String status;

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public Issue withStatus(String status) {
        this.status = status;
        return this;
    }

    public Issue withId(int id) {
        this.id = id;
        return this;
    }

    public Issue withSubject(String subject) {
        this.subject = subject;
        return this;
    }


    public Issue withDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return id == issue.id &&
                Objects.equals(subject, issue.subject) &&
                Objects.equals(description, issue.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, description);
    }
}
