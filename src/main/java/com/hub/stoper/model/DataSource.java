package com.hub.stoper.model;


import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {


    public static final String DATABASE_PATH = "jdbc:sqlite:C:\\Users\\hub\\Nauka\\Testy\\JavaFX\\Stoper\\src\\main\\resources\\com\\hub\\database\\stoper.db";





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
        }catch (SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void close(){
        try {
            connection.close();
        }catch (SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }

    }





}
