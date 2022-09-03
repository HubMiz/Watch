package com.hub.stoper.model;


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

    //Stopwatches table
    public static final String TABLE_STOPWATCHES = "stopWatches";

    public static final String COLUMNS_STOPWATCHES_ID = "id";
    public static final String COLUMNS_STOPWATCHES_USERID = "userID";
    public static final String COLUMNS_STOPWATCHES_HOURS = "hours";
    public static final String COLUMNS_STOPWATCHES_MINUTES = "minutes";
    public static final String COLUMNS_STOPWATCHES_SECONDS = "seconds";
    public static final String COLUMNS_STOPWATCHES_LABEL = "label";

    //Minute Timers Table
    public static final String TABLE_MINUTE_TIMERS = "minuteTimers";

    public static final String COLUMNS_MINUTE_TIMERS_ID = "id";
    public static final String COLUMNS_MINUTE_TIMERS_USERID = "userID";
    public static final String COLUMNS_MINUTE_TIMERS_HOURS = "hours";
    public static final String COLUMNS_MINUTE_TIMERS_MINUTES = "minutes";
    public static final String COLUMNS_MINUTE_TIMERS_SECONDS = "seconds";
    public static final String COLUMNS_MINUTE_TIMERS_NAME = "name";

    //Alarm table

    public static final String TABLE_ALARMS = "Alarms";

    public static final String COLUMNS_ALARMS_USERID = "userID";
    public static final String COLUMNS_ALARMS_ID = "id";

    public static final String COLUMNS_ALARMS_YEAR = "year";
    public static final String COLUMNS_ALARM_MONTH = "month";
    public static final String COLUMNS_ALARM_DAY = "day";
    public static final String COLUMNS_ALARM_HOUR = "hour";
    public static final String COLUMNS_ALARM_MINUTE = "minute";
    public static final String COLUMNS_ALARM_SECOND = "seconds";
    public static final String COLUMNS_ALARM_REPEATABLEID = "repeatableID";


    //QUERY
    public static final String QUERY_USERS_BY_NAME = "SELECT " +COLUMNS_USERS_ID +","+ COLUMNS_USERS_NAME +","+COLUMNS_USERS_CURRENT_USER+
        " FROM " + TABLE_USERS +
        " WHERE " +COLUMNS_USERS_NAME + " = ?";
    public static final String QUERY_USERS = "SELECT " +COLUMNS_USERS_ID +","+ COLUMNS_USERS_NAME +","+COLUMNS_USERS_CURRENT_USER+
            " FROM " + TABLE_USERS;

    public static final String QUERY_CURRENT_USER = "SELECT " +COLUMNS_USERS_ID +","+ COLUMNS_USERS_NAME +","+COLUMNS_USERS_CURRENT_USER+
            " FROM " + TABLE_USERS+
            " WHERE " + COLUMNS_USERS_CURRENT_USER + " = 1 ";

    public static final String QUERY_MINUTE_TIMERS_BY_CURRENT_USER = "SELECT " + COLUMNS_MINUTE_TIMERS_USERID + "," + COLUMNS_MINUTE_TIMERS_HOURS + "," +
            COLUMNS_MINUTE_TIMERS_MINUTES + "," + COLUMNS_MINUTE_TIMERS_SECONDS + "," + COLUMNS_MINUTE_TIMERS_NAME + "," + COLUMNS_MINUTE_TIMERS_ID +
            " FROM " + TABLE_MINUTE_TIMERS +
            " WHERE " + COLUMNS_MINUTE_TIMERS_USERID + " = ?";

    public static final String QUERY_ALARMS_BY_USER = "SELECT " + COLUMNS_ALARMS_ID + "," + COLUMNS_ALARMS_USERID + "," + COLUMNS_ALARMS_YEAR + "," +
            COLUMNS_ALARM_MONTH + "," + COLUMNS_ALARM_DAY + "," + COLUMNS_ALARM_HOUR + "," + COLUMNS_ALARM_MINUTE + "," + COLUMNS_ALARM_SECOND + "," + COLUMNS_ALARM_REPEATABLEID +
            " FROM " + TABLE_ALARMS +
            " WHERE " + COLUMNS_ALARMS_USERID + " = ?";


    //INSERTS
    public static final String INSERT_USER = " INSERT INTO " + TABLE_USERS +
            " (name) VALUES(?)";
    public static final String INSERT_STOPWATCH_TIME =" INSERT INTO " + TABLE_STOPWATCHES +
            " ("+COLUMNS_STOPWATCHES_USERID+","+COLUMNS_STOPWATCHES_HOURS+","+COLUMNS_STOPWATCHES_MINUTES+","+COLUMNS_STOPWATCHES_SECONDS+","+ COLUMNS_STOPWATCHES_LABEL+")"+
            " VALUES(?,?,?,?,?)";

    public static final String INSERT_TIMER =" INSERT INTO " + TABLE_MINUTE_TIMERS +
            "("+COLUMNS_MINUTE_TIMERS_USERID + "," + COLUMNS_MINUTE_TIMERS_HOURS + "," + COLUMNS_MINUTE_TIMERS_MINUTES + "," + COLUMNS_MINUTE_TIMERS_SECONDS +"," + COLUMNS_MINUTE_TIMERS_NAME +")"+
            " VALUES(?,?,?,?,?)";

    //UPDATES
    public static final String UPDATE_USER_CURRENT_USER_BY_ID = "UPDATE " + TABLE_USERS +
            " SET " + COLUMNS_USERS_CURRENT_USER + " = ?" +
            " WHERE " + COLUMNS_USERS_ID + " = ?";

    //DELETES
    public static final String DELETE_TIMER_BY_ID = "DELETE FROM " + TABLE_MINUTE_TIMERS +
            " WHERE " + COLUMNS_MINUTE_TIMERS_ID + " = ?";

    //Prepared Statements
    //Query
    private PreparedStatement queryUsersByName;
    private PreparedStatement queryMinuteTimersByCurrentUsers;
    private PreparedStatement queryAlarmsByUser;

    //Insert
    private PreparedStatement insertUser;
    private PreparedStatement insertStopwatches;
    private PreparedStatement insertTimer;

    //Update
    private PreparedStatement updateUserCurrentUserById;

    //Delete
    private PreparedStatement deleteTimerByID;

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
            insertStopwatches = connection.prepareStatement(INSERT_STOPWATCH_TIME);
            queryMinuteTimersByCurrentUsers = connection.prepareStatement(QUERY_MINUTE_TIMERS_BY_CURRENT_USER);
            insertTimer = connection.prepareStatement(INSERT_TIMER);
            deleteTimerByID =connection.prepareStatement(DELETE_TIMER_BY_ID);
            queryAlarmsByUser = connection.prepareStatement(QUERY_ALARMS_BY_USER);
        }catch (SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void close(){
        try {
            queryAlarmsByUser.close();
            deleteTimerByID.close();
            insertTimer.close();
            queryMinuteTimersByCurrentUsers.close();
            insertStopwatches.close();
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
    //Get current user
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
    //Returns current user timers
    public List<Timer> getUserTimers(){
        try{
            User currentUser = getCurrentUser();
            queryMinuteTimersByCurrentUsers.setInt(1,currentUser.getId());

            ResultSet resultSet = queryMinuteTimersByCurrentUsers.executeQuery();

            List<Timer> timerList = new ArrayList<>();

            while (resultSet.next()){
                Timer timer = new Timer();
                timer.setUserId(resultSet.getInt(1));
                timer.setHours(resultSet.getInt(2));
                timer.setMinutes(resultSet.getInt(3));
                timer.setSeconds(resultSet.getInt(4));
                timer.setName(resultSet.getString(5));
                timer.setId(resultSet.getInt(6));
                timerList.add(timer);
            }
            return timerList;

        }catch (SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
            return null;
        }
    }
    //takes new user id and set him as current user and makes sure that current user will no longer be current
    //Todo test nulls

    public List<Alarm> getUserAlarms(){
        try{
            User currentUser = getCurrentUser();
            queryAlarmsByUser.setInt(1,currentUser.getId());

            ResultSet set = queryAlarmsByUser.executeQuery();

            List<Alarm> alarmList = new ArrayList<>();

            while (set.next()){
                Alarm alarm = new Alarm();
                alarm.setId(set.getInt(1));
                alarm.setUserId(set.getInt(2));

                alarm.setYear(set.getInt(3));
                alarm.setMonth(set.getInt(4));
                alarm.setDay(set.getInt(5));

                alarm.setHour(set.getInt(6));
                alarm.setMinute(set.getInt(7));
                alarm.setSeconds(set.getInt(8));

                alarm.setRepeatableId(set.getInt(9) );

                alarmList.add(alarm);
            }
            return alarmList;

        }catch (SQLException e){

            return null;
        }
    }

    public boolean updateCurrentUser(int id){
        User currentUser = getCurrentUser();
        try{
            connection.setAutoCommit(false);
            if(currentUser != null){
                updateUserCurrentUserById.setInt(1, 0);
                updateUserCurrentUserById.setInt(2, currentUser.getId());

                int rowsAffected = updateUserCurrentUserById.executeUpdate();
                if (rowsAffected != 1) {
                    throw new SQLException("Updated to many rows");// That shouldn't be error because nothing bad happens --> change it to log
                }
            }

            updateUserCurrentUserById.setInt(1,1);
            updateUserCurrentUserById.setInt(2,id);


            int rowsAffected = updateUserCurrentUserById.executeUpdate();
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

    public boolean insertStopwatchTime(int hours,int minutes,int seconds,String label){

        try{
           connection.setAutoCommit(false);

           User currentUser = getCurrentUser();

           insertStopwatches.setInt(1,currentUser.getId());
           insertStopwatches.setInt(2,hours);
           insertStopwatches.setInt(3,minutes);
           insertStopwatches.setInt(4,seconds);
           insertStopwatches.setString(5,label);

           int rowsAffected = insertStopwatches.executeUpdate();

           if(rowsAffected != 1){
               throw new Exception("To much rows affected");
           }

           connection.commit();
           return true;

        }catch (Exception e){
            try{
                connection.rollback();
            }catch (SQLException e2){
                System.out.println("Rollback failed!! " + e2.getMessage());
            }
            return false;
        }finally {
            try{
                connection.setAutoCommit(true);
            }catch (SQLException e){
                System.out.println("Autocommit still false!!");
            }
        }
    }


    public boolean insertTimer(String label, int hours, int minutes, int seconds) {

        try{
            connection.setAutoCommit(false);

            User currentUser = getCurrentUser();

            insertTimer.setInt(1,currentUser.getId());
            insertTimer.setInt(2,hours);
            insertTimer.setInt(3,minutes);
            insertTimer.setInt(4,seconds);
            insertTimer.setString(5,label);

            int affectedRows = insertTimer.executeUpdate();
            if(affectedRows!=1){
                throw new SQLException("To much rows affected");
            }
            connection.commit();
            return true;

        }catch (Exception e){
            try {
                connection.rollback();
            }catch (SQLException e2){
                System.out.println("Rollback didn't work!! " + e2.getMessage());
            }
            return false;
        }finally {
            try{
                connection.setAutoCommit(true);
            }catch (SQLException e){
                System.out.println("Autocommit still false!! " + e.getMessage());
            }
        }

    }

    public void deleteTimerByID(int id) {

        try {
            connection.setAutoCommit(false);
            deleteTimerByID.setInt(1,id);

            if(deleteTimerByID.executeUpdate() != 1){
                throw  new SQLException("To much rows affected");
            }

            connection.commit();

        }catch (Exception e){
            try{
                connection.rollback();
            }catch (SQLException e2){
                System.out.println("Couldn't rollback!!");
            }
        }finally {
            try {
                connection.setAutoCommit(true);
            }catch (SQLException e){
                System.out.println("Autocommit still off" + e.getMessage());
            }
        }

    }
}
