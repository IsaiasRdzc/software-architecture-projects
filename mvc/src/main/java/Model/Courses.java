package Model;

import java.util.ArrayList;
import java.util.List;
import View.View;

public class Courses extends Model {

    ArrayList<Course> courses = new ArrayList<>();

    public void addCourse(Course course) {
        if (!courseExists(course.getCourseCode())) {
            courses.add(course);
            notifyChange(courses);
        } else {
            notifyChange(null);
        }
    }


    public boolean courseExists(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return true;
            }
        }
        return false;
    }
}
