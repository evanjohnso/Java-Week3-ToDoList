package dao;

import models.Task;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Sql2oTaskDaoTest {

    private Sql2oTaskDao taskDao; //ignore me for now
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        taskDao = new Sql2oTaskDao(sql2o); //ignore me for now

        //keep connection open through entire test so it does not get erased.
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingCourseSetsId() throws Exception {
        Task task = helper();
        int originalTaskId = task.getId();
        taskDao.add(task);
        assertNotEquals(originalTaskId, task.getId()); //how does this work?
    }

    @Test
    public void existingTasksCanBeFoundById() throws Exception {
        Task task = helper();
        taskDao.add(task); //add to dao (takes care of saving)
        Task foundTask = taskDao.findById(task.getId()); //retrieve
        assertEquals(task, foundTask); //should be the same
    }

    @Test
    public void addedTasksAreReturnedFromgetAll() throws Exception {
        Task task = helper();
        taskDao.add(task);
        assertEquals(1, taskDao.getAll().size());
    }

    @Test
    public void noTasksReturnsEmptyList() throws Exception {
        assertEquals(0, taskDao.getAll().size());
    }

    @Test
    public void updateChangesTaskContent() throws Exception {
        String description = "mow the lawn";
        Task task = new Task (description);
        taskDao.add(task);
        taskDao.update(task.getId(), "brush the cat", 0);
        Task updatedTask = taskDao.findById(task.getId());
        assertNotEquals(description, updatedTask.getDescription());
    }

    @Test
    public void deleteByIdDeletesCorrectTask() throws Exception {
        Task task = helper();
        taskDao.add(task);
        taskDao.deleteById(task.getId());
        assertEquals(0, taskDao.getAll().size());
    }

    @Test
    public void clearAllClearsAll() throws Exception {
        Task task = helper();
        Task otherTask = new Task("brush the cat");
        taskDao.add(otherTask);
        taskDao.add(task);

        taskDao.clearAllTasks();
        assertEquals(0, taskDao.getAll().size());
    }

    //Helper
    public Task helper() {
        return new Task("mow the lawn");
    }

}