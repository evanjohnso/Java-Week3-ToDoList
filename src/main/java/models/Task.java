package models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task {

    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private int id;
    private int categoryId;

    //Constructor
    public Task(String description, int categoryId){
        this.description = description;
        this.completed = false;
        this.categoryId = categoryId;
        this.createdAt = LocalDateTime.now();
    }

    //Setters
    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    //Getters
    public String getDescription() {
        return description;
    }

    public boolean getCompleted(){
        return this.completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }



    public void update(String content) {
        this.description = content;
    }

    //Overrides for DAO
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (completed != task.completed) return false;
        if (id != task.id) return false;
        if (categoryId != task.categoryId) return false;
        return description.equals(task.description);
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + (completed ? 1 : 0);
        result = 31 * result + id;
        result = 31 * result + categoryId;
        return result;
    }


}
