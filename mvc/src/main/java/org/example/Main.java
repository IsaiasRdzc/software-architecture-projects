package org.example;

import Controller.CourseController;
import View.RegisterCourse;
import View.RegisterToCourse;
import Model.*;
public class Main {
    public static void main(String[] args) {
        Courses courses = new Courses();
        CourseController courseController = new CourseController(courses);
        RegisterCourse registerCourse = new RegisterCourse(courseController);
        RegisterToCourse registerCourse2 = new RegisterToCourse(courseController);
        courses.addView(registerCourse);
        courses.addView(registerCourse2);
    }
}