package models;


public class Category {

    private String focus;
    private int id;
    
    //Constructor
    public Category(String focus) {
        this.focus = focus;
    }

    //Getters
    public String getFocus() {
        return focus;
    }

    public int getId() {
        return id;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setFocus(String newTitle) {
        this.focus = newTitle;
    }

    //Override methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != category.id) return false;
        return focus.equals(category.focus);
    }

    @Override
    public int hashCode() {
        int result = focus.hashCode();
        result = 31 * result + id;
        return result;
    }
}
