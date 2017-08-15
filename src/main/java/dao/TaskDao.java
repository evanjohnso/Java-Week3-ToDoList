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
        void update (int id, String newDescription);
        //DESTROY!!
        void deleteById(int id);

        //Additional Methods
        Task findById(int id);

        void clearAllTasks();
}
