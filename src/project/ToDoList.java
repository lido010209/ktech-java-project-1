package project;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ToDoList {
    private String title;
    private String deadline;

    public ToDoList(String title, String deadline) {

        // Define a date format pattern
        this.title=title;
        this.deadline=deadline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
