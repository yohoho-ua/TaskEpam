package com.kpi.arkhipchuk.model.dao.jdbc;

import com.kpi.arkhipchuk.model.dao.DaoCourse;
import com.kpi.arkhipchuk.model.dao.jdbc.QueryConstants.CourseQueryConstants;
import com.kpi.arkhipchuk.model.entity.Course;
import com.kpi.arkhipchuk.model.entity.Mark;
import org.apache.log4j.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Anya on 07.06.2017.
 */
public class JdbcCourseDao implements DaoCourse {

    protected String getCurrentCoursesQueryStudent() {
        return CourseQueryConstants.STUDENT_SELECT_CURRENT_COURSES;
    }

    protected String getCoursesForJoinQueryStudent() {
        return CourseQueryConstants.STUDENT_SELECT_ACTUAL_COURSES_FOR_JOIN;
    }

    protected String getFinishedCoursesQueryStudent() {
        return CourseQueryConstants.STUDENT_SELECT_FINISHED_COURSES;
    }

    protected String getInsertQueryForJoinToNewCourseStudent() {
        return CourseQueryConstants.STUDENT_UPDATE_FOR_JOIN_TO_NEW_COURSE;
    }

    protected String getInsertQuery() {
        return "INSERT INTO course (name, status) VALUES (?, ?)";
    }


    //    protected void prepareStatementForCreate(PreparedStatement statement, Course entity) throws SQLException {
//        statement.setString(1, entity.getName());
//        statement.setInt(2, entity.getStatus());
//
//    }
//
//    @Override
//    protected void prepareStatementForUpdate(PreparedStatement statement, Course entity) throws SQLException {
//        statement.setString(1, entity.getName());
//        statement.setInt(3, entity.getStatus());
//        statement.setInt(4, entity.getId());
//    }
//
//    @Override
//    protected void prepareStatementForDelete(PreparedStatement statement, Course entity) throws SQLException {
//        statement.setInt(1, entity.getId());
//    }
//
    protected List<Course> parseResultSet(ResultSet rs) throws SQLException {
        List<Course> res = new ArrayList<>();
        while (rs.next()) {
            try {
                Course course = new Course();
                course.setId(rs.getInt(CourseQueryConstants.COURSE_COLUMN_ID));
                course.setName(rs.getString(CourseQueryConstants.COURSE_COLUMN_NAME));
                course.setStatus(rs.getInt(CourseQueryConstants.COURSE_COLUMN_STATUS));
                res.add(course);
            } catch (SQLException ex) {
                // Logger.getLogger(JdbcCourseDao.class.getName()).log(Level.ERROR, null, ex);
                throw ex;
            }
        }
        return res;
    }


    @Override
    public void create(Course entity) {

    }

    @Override
    public void update(Course entity) {

    }

    @Override
    public void insert(Course entity) {

    }

    @Override
    public void delete(Course entity) {

    }


    @Override
    public Course find(int id) {
        return null;
    }

    @Override
    public List<Course> findAll(String query, int... key) {
        List<Course> list;
        int[] keys = key;
        String sql = query;
        try (Connection connection = JdbcDaoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < keys.length; i++) {
                statement.setInt(i + 1, keys[i]);
            }
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
            if (list == null || list.isEmpty()) {
                return null;
            }

            return list;
        } catch (Exception e) {
            //   logger.log(Level.ERROR, null, e);
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> findMap(String query, int... key) {
        Map<String, String> result = new TreeMap<>();
        int[] keys = key;
        try (Connection connection = JdbcDaoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < keys.length; i++) {
                statement.setInt(i + 1, keys[i]);
            }
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                try {
                    String courseName = rs.getString("course_name");
                    String mark = rs.getString("mark_name");
                    result.put(courseName, mark);
                } catch (SQLException ex) {
                    // Logger.getLogger(JdbcCourseDao.class.getName()).log(Level.ERROR, null, ex);
                    throw ex;
                }
                if (result == null || result.isEmpty()) {
                    return null;
                }


            }
        } catch (Exception e) {
            //   logger.log(Level.ERROR, null, e);
            throw new RuntimeException(e);
        }
        return result;
    }

    public void update(String query, int... key) {
        int[] keys = key;
        try (Connection connection = JdbcDaoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < keys.length; i++) {
                statement.setInt(i + 1, keys[i]);
            }
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}




