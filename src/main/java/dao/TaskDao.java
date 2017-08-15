package dao;

import models.*;

import java.util.List;

public interface TaskDao {

                //CRUD
        //Create
        void add (Task task);
        //Read
        List<Task> getAll();
        //Update
        void update(int id, String newDescription, int categoryId);
        //DESTROY!!
        void deleteById(int id);

        //Additional Methods
        Task findById(int id);

        void clearAllTasks();

        List<Task> findByCompleted();
}
