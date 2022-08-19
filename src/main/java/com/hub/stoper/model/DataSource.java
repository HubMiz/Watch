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

    public static final String QUERY_USERS_BY_NAME = "SELECT " +COLUMNS_USERS_ID +","+ COLUMNS_USERS_NAME +","+COLUMNS_USERS_ID+
        " FROM " + TABLE_USERS +
        " WHERE " +COLUMNS_USERS_NAME + " = ?";

    //INSERTS
    public static final String INSERT_USER = " INSERT INTO " + TABLE_USERS +
            " (name) VALUES(?)";




    //Prepared Statements
    private PreparedStatement queryUsersByName;
    private PreparedStatement insertUser;
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
        }catch (SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void close(){
        try {
            insertUser.close();
            queryUsersByName.close();
            connection.close();
        }catch (SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
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
