package com.demo.database;

public class DatabaseData {

    //Database data
    public static final  String DRIVER = "org.postgresql.Driver";
    public static final  String HOST = "localhost";
    public static final String DATABASE_NAME = "postgres";
    public static final String SCHEMA = "public";
    public static final  int port = 5433;
    public static final  String URL = "jdbc:postgresql://"+HOST+":"+port+"/"+DATABASE_NAME;
    public static final  String USERNAME = "postgres";
    public static final  String PASSWORD = "1";

    //Table data
    public static final String TABLE_NAME = "QUESTIONS";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_QUESTION = "QUESTION";
    public static final String COLUMN_ANSWER = "ANSWER";

    //Messages
    public static final String COULD_CONNECT = "Connection established successfully";
    public static final String COULD_NOT_CONNECT = "Could not establish connection";
    public static final String TABLE_CREATED = "Table created successfully";
    public static final String TABLE_NOT_CREATED = "Could not create table";

}
