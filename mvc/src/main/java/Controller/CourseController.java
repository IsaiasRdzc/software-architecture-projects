package Controller;

import Model.Course;
import Model.Courses;
import View.RegisterCourse;
import View.RegisterToCourse;

public class CourseController extends Controller {
    private Courses model;

    public CourseController(Courses model) {
        super(model);
        this.model = model;
    }

    public void addCourse(String courseName, String courseCode) {
        Course course = new Course(courseName, courseCode);
        model.addCourse(course);
    }

    @Override
    public void handdleEvent(Object event) {
        if (event instanceof Course) {
            Course course = (Course) event;
            addCourse(course.getCourseCode(), course.getCourseName());
        }
    }
}
