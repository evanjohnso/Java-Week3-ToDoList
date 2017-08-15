package dao;

import models.*;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oCategoryDaoTest {
    private Sql2oCategoryDao categoryDao;
    private Sql2oTaskDao taskDao;
    private Connection conn;
    //Helper
    public Category testOne() {
        return new Category("HouseHold Chores");
    }

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        categoryDao = new Sql2oCategoryDao(sql2o);
        taskDao = new Sql2oTaskDao(sql2o);

        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingCourseSetsId() throws Exception {
        Category firstCategory = testOne();
        int originalCategoryId = firstCategory.getId();
        categoryDao.add(firstCategory);
        assertNotEquals(originalCategoryId, firstCategory.getId());
    }

    @Test
    public void existingCategoriesCanBeFoundById() throws Exception {
        Category test = testOne();
        categoryDao.add(test);
        Category foundCategory = categoryDao.findById(test.getId());
        assertEquals(test, foundCategory);
    }

    @Test
    public void updateCategoryFocus() throws Exception {
        Category testing = testOne();
        categoryDao.add(testing);
        categoryDao.update(testing.getId(), "Yard Work");
        Category freshCategory = categoryDao.findById(testing.getId());
        assertEquals("Yard Work", freshCategory.getFocus());
    }

    @Test
    public void deleteCategoryById() throws Exception {
        Category testing = testOne();
        categoryDao.add(testing);
        categoryDao.add(new Category("homies" ));
        categoryDao.add(new Category("pickles" ));
        categoryDao.deleteById(testing.getId());
        assertEquals(2, categoryDao.getAll().size());
    }

    @Test
    public void clearAllCategoriesById() throws Exception {
        Category firstOne = testOne();
        categoryDao.add(firstOne);
        categoryDao.add(new Category("homies" ));
        categoryDao.add(new Category("pickles" ));
        categoryDao.clearAllCategories();
        assertEquals(0, categoryDao.getAll().size());
    }

    @Test
    public void deleteMultipleCategoryById() throws Exception {
        Category testing = testOne();
        categoryDao.add(testing);
        categoryDao.add(new Category("homies" ));
        categoryDao.add(new Category("pickles" ));
        int deletingId = testing.getId();
        categoryDao.deleteById(deletingId);
        categoryDao.deleteById(2);
        String answer = categoryDao.findById(3).getFocus();
        assertEquals("pickles", answer);
    }

}