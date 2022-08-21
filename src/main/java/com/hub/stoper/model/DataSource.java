package com.hub.stoper.model;


import java.io.File;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    //Database String
    public static final String DATABASE_PATH = "jdbc:sqlite:C:\\Users\\hub\\Nauka\\Testy\\JavaFX\\Stoper\\src\\main\\resources\\com\\hub\\database\\stoper.db";

    //Users table
    public static final String TABLE_USERS = "users";

    public static final String COLUMNS_USERS_ID = "id";
    public static final String COLUMNS_USERS_NAME = "name";
    public static final String COLUMNS_USERS_CURRENT_USER = "currentUser";


    //QUERY

    public static final String QUERY_USERS_BY_NAME = "SELECT " +COLUMNS_USERS_ID +","+ COLUMNS_USERS_NAME +","+COLUMNS_USERS_CURRENT_USER+
        " FROM " + TABLE_USERS +
        " WHERE " +COLUMNS_USERS_NAME + " = ?";
    public static final String QUERY_USERS = "SELECT " +COLUMNS_USERS_ID +","+ COLUMNS_USERS_NAME +","+COLUMNS_USERS_CURRENT_USER+
            " FROM " + TABLE_USERS;

    public static final String QUERY_CURRENT_USER = "SELECT " +COLUMNS_USERS_ID +","+ COLUMNS_USERS_NAME +","+COLUMNS_USERS_CURRENT_USER+
            " FROM " + TABLE_USERS+
            " WHERE " + COLUMNS_USERS_CURRENT_USER + " = 1 ";

    //INSERTS
    public static final String INSERT_USER = " INSERT INTO " + TABLE_USERS +
            " (name) VALUES(?)";

    //UPDATES
    public static final String UPDATE_USER_CURRENT_USER_BY_ID = "UPDATE " + TABLE_USERS +
            " SET " + COLUMNS_USERS_CURRENT_USER + " = ?" +
            " WHERE " + COLUMNS_USERS_ID + " = ?";

    //Prepared Statements
    private PreparedStatement queryUsersByName;
    private PreparedStatement insertUser;

    private PreparedStatement updateUserCurrentUserById;


    private static DataSource dataSource = new DataSource();
    private Connection connection;


    private DataSource(){

    }

    public static DataSource getInstance(){
        return dataSource;
    }

    public void open(){
        try{
            connection = DriverManager.getConnection(DATABASE_PATH);
            queryUsersByName = connection.prepareStatement(QUERY_USERS_BY_NAME);
            insertUser = connection.prepareStatement(INSERT_USER);
            updateUserCurrentUserById = connection.prepareStatement(UPDATE_USER_CURRENT_USER_BY_ID);
        }catch (SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void close(){
        try {
            updateUserCurrentUserById.close();
            insertUser.close();
            queryUsersByName.close();
            connection.close();
        }catch (SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public List<User> getUsers(){
        try(Statement queryUsers = connection.createStatement();
            ResultSet resultSet = queryUsers.executeQuery(QUERY_USERS))   {
            List<User> userList = new ArrayList<>();
            while(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setCurrentUser(resultSet.getInt(3));
                userList.add(user);
            }
            userList.forEach( (user ) -> System.out.println("Name: " + user.getName() + " ID: " + user.getId()));
            return userList;
        }catch (SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
            return null;
        }
    }

    public List<User> getUserByName(String name){
        try {
            queryUsersByName.setString(1, name);
            ResultSet set = queryUsersByName.executeQuery();
            List<User> users = new ArrayList<>();
            while (set.next()){
                User user = new User();
                user.setId(set.getInt(1));
                user.setName(set.getString(2));
                user.setCurrentUser(set.getInt(3));
                users.add(user);
            }
            return users;

        }catch (SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
            return null;
        }
    }

    public User getCurrentUser(){
        try(Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(QUERY_CURRENT_USER)){

            if(set.next()) {
                User user = new User();
                user.setId(set.getInt(1));
                user.setName(set.getString(2));
                user.setCurrentUser(1);
                return user;
            }else{
                return null;
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    //takes new user id and set him as current user and makes sure that current user will no longer be current
    public boolean updateCurrentUser(int id){
        User currentUser = getCurrentUser();
        try{
            connection.setAutoCommit(false);
            if(currentUser != null){
                updateUserCurrentUserById.setInt(1, 0);
                updateUserCurrentUserById.setInt(2, currentUser.getId());

                System.out.println(updateUserCurrentUserById.toString());

                int rowsAffected = updateUserCurrentUserById.executeUpdate();
                if (rowsAffected != 1) {
                    throw new SQLException("Updated to many rows");// That shouldn't be error because nothing bad happens --> change it to log
                }
            }

            updateUserCurrentUserById.setInt(1,1);
            updateUserCurrentUserById.setInt(2,id);

            System.out.println(updateUserCurrentUserById.toString());

            int rowsAffected = updateUserCurrentUserById.executeUpdate();
            System.out.println(id);
            if(rowsAffected > 1){
                throw new SQLException("Updated to many rows");// Here it is error
            }
            connection.commit();
            return true;

        }catch (Exception e){
            System.out.println(e.getMessage());
            try {
                connection.rollback();
            }catch (SQLException e2){
                System.out.println("Rollback didn't work !!!");
            }
            return false;
        }finally {
            try {
                connection.setAutoCommit(true);
            }catch (SQLException e){
                System.out.println("Autocommit is false");
            }
        }
    }

    public boolean insertUser(String name){
        try{
            connection.setAutoCommit(false);
            insertUser.setString(1,name);

            int rowsAffected = insertUser.executeUpdate();
            if(rowsAffected != 1){
                throw new SQLException("Too much rows added");
            }
            connection.commit();
            return true;
        }catch (Exception e){
            System.out.println("Something went wrong: " + e.getMessage());
            try {
                connection.rollback();
            }catch (SQLException e2){
                System.out.println("Rollback didn't work: " + e2.getMessage());
            }
            return false;
        }finally {
            try{
                connection.setAutoCommit(true);
            }catch (SQLException e){
                System.out.println("Autocommit off");
            }
        }

    }




}
