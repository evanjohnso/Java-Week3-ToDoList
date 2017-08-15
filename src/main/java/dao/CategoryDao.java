package dao;


import models.*;

import java.util.List;

public interface CategoryDao {

    //create
    void add (Category category);

    //read
    List<Category> getAllCategories();
    List<Task> getAllTasksByCategory (int categoryId);

    Category findById(int id);

    //update
    void update (int id, String name);

    //delete
    void deleteById(int id);
    void clearAllCategories();
    }
