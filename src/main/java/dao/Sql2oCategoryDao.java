package dao;


import models.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.*;

public class Sql2oCategoryDao implements CategoryDao {
    private final Sql2o sql2o;

    public Sql2oCategoryDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    public void add(Category newCategory) {
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery("INSERT INTO categories (focus) VALUES (:focus) ")
                    .bind(newCategory)
                    .executeUpdate()
                    .getKey();
            newCategory.setId(id);
        } catch (Sql2oException e) {
            e.printStackTrace();
        }
    }

    public List<Category> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM categories")
                    .executeAndFetch(Category.class);
        }
    }

    public List<Task> getAllTasksByCategory(int categoryId) {
        String sql = "SELECT * FROM tasks WHERE categoryid = :thisId";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("categoryid", categoryId)
                    .executeAndFetch(Task.class);
        }
    }

    public Category findById(int newId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM categories WHERE id = :id")
                    .addParameter("id", newId)
                    .executeAndFetchFirst(Category.class);
        }
    }

    public void update(int findersId, String updatedName) {
        String sql = "UPDATE categories SET focus = :focus WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("focus", updatedName)
                    .addParameter("id", findersId)
                    .executeUpdate();
            } catch (Sql2oException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteById(int nukeIt) {
        try (Connection con = sql2o.open()) {
            con.createQuery("DELETE FROM categories WHERE id = :id")
                    .addParameter("id", nukeIt)
                    .executeUpdate();
        } catch (Sql2oException e) {
            e.printStackTrace();
        }
    }
}
