package com.kpi.arkhipchuk.controller;

import com.kpi.arkhipchuk.model.dao.*;
import com.kpi.arkhipchuk.model.dao.jdbc.QueryConstants.CourseQueryConstants;
import com.kpi.arkhipchuk.model.entity.Course;
import com.kpi.arkhipchuk.model.entity.Mark;
import com.kpi.arkhipchuk.model.entity.Student;
import com.kpi.arkhipchuk.model.entity.Teacher;

import java.util.*;

/**
 * Created by Anya on 07.06.2017.
 */
public class Service<T> {
    private static Service instance;
    private static DaoFactory daoFactory = DaoFactory.getFactory();

    private Service() {
    }

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    public Student checkStudent(String email, String password) {
        DaoStudent studentDao = daoFactory.createStudentDao();
        Student student = studentDao.getStudentByEmail(email, password);
        return student;
    }

    public Teacher checkTeacher(String email, String password) {
        DaoTeacher teacherDao = daoFactory.createTeacherDao();
        Teacher teacher = teacherDao.getTeacherByEmail(email, password);
        return teacher;
    }

    public Student checkAndRegUser(String firstName, String lastName, String login, String password, String email) {
        DaoStudent studentDao = daoFactory.createStudentDao();
        if (studentDao.getStudentByEmail(email, password)==null) {
            return studentDao.getNewStudent(firstName, lastName, login, password, email);
        }
        return null;
    }

    public Course getCourseByStudentId(int id) {
        DaoCourse courseDao = daoFactory.createCourseDao();
        return courseDao.find(id);
    }

    public void updateCourseStatus(String query, int... key) {
        //Boolean ifCourseUpdated;
        DaoCourse courseDao = daoFactory.createCourseDao();
        courseDao.update(query, key);
    }

    public List<Course> findListOfCourses(String query, int... keys) {
        DaoCourse courseDao = daoFactory.createCourseDao();
        return courseDao.findAll(query, keys);
    }
    public List<ArrayList<Object>> findListOfSpecificStrings(String query, int... id) {
        DaoOptional daoOptional = daoFactory.createOptionalDao();
        return daoOptional.findAll(query, id);
    }

    public Map<String, String> findListOfTwoStrings(String query, int... id) {
        DaoCourse courseDao = daoFactory.createCourseDao();
        return courseDao.findMap(query, id);
    }

    public List<Student> findListOfStudentForCourses(String query, int id) {
        DaoStudent studentDao = daoFactory.createStudentDao();
                return studentDao.findAll(query, id);

            }

    public void startNewCourse(String query, int... key){
        DaoCourse courseDao = daoFactory.createCourseDao();
    }
}
