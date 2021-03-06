import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.Sql2oCategoryDao;
import dao.Sql2oTaskDao;
import models.Category;
import models.Task;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {

        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString,"","");

        //Instantiate Data Access Objects for task and category
        Sql2oCategoryDao categoryDao = new Sql2oCategoryDao(sql2o);
        Sql2oTaskDao taskDao = new Sql2oTaskDao(sql2o);



//        delete all tasks
        get("/tasks/:id/deleteAll", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int nukeThem = Integer.parseInt(req.params("id"));
            categoryDao.clearAllTasksByCategory(nukeThem);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

//        delete all categories
        get("/categories/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            categoryDao.clearAllCategories();
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());


        //get: show new task form
        get("/tasks/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Category> allCategories = categoryDao.getAllCategories();
            model.put("availableCategories", allCategories);
            return new ModelAndView(model, "task-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process new task form
        post("/tasks/new", (request, response) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            String description = request.queryParams("description");
            int thisCategory = Integer.parseInt(request.queryParams("thatCategory"));

            Task newTask = new Task(description, thisCategory);
            taskDao.add(newTask);
            model.put("task", newTask);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //If categories exist, display them
        post("/newCategory", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String newFocus = request.queryParams("pickles");
            categoryDao.add(new Category(newFocus));
            List<Category> allCategories = categoryDao.getAllCategories();
            model.put("availableCategories", allCategories);
            return new ModelAndView(model, "task-form.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show all tasks
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            List<Category> allCategories = categoryDao.getAllCategories();
//            List<Task> allTasks = taskDao.getAll();

            model.put("eachCategory", allCategories);
//            model.put("tasks", allTasks);

            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show an individual task
        get("/tasks/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToFind = Integer.parseInt(req.params("id")); //pull id - must match route segment
            Task foundTask = taskDao.findById(idOfTaskToFind);
//            Task foundTask = Task.findById(idOfTaskToFind); //use it to find task
            model.put("task", foundTask); //add it to model for template to display
            return new ModelAndView(model, "task-detail.hbs"); //individual task page.
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a task
        get("/tasks/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToEdit = Integer.parseInt(req.params("id"));
            Task editTask = taskDao.findById(idOfTaskToEdit);
//            Task editTask = Task.findById(idOfTaskToEdit);
            model.put("editTask", editTask);
            return new ModelAndView(model, "task-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process a form to update a task
        post("/tasks/:id/update", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            String newContent = req.queryParams("description");
            int idOfTaskToEdit = Integer.parseInt(req.params("id"));
            Task editTask = taskDao.findById(idOfTaskToEdit);
//            Task editTask = Task.findById(idOfTaskToEdit);
            taskDao.update(idOfTaskToEdit, newContent, editTask.getCategoryId() );
//            editTask.update(newContent);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete an individual task
        get("/tasks/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToDelete = Integer.parseInt(req.params("id")); //pull id - must match route segment
            taskDao.deleteById(idOfTaskToDelete);
//            Task deleteTask = Task.findById(idOfTaskToDelete); //use it to find task
//            deleteTask.deleteTask();
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //show all tasks of a certain category
        get("/categories/:id/tasks", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int categorical = Integer.parseInt(request.params("id") );
            List<Task> theseTasks = categoryDao.getAllTasksByCategory(categorical);
            model.put("categoryID", categorical);
            model.put("tasks", theseTasks);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

//


    }
}
