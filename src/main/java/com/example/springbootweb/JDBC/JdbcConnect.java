package com.example.springbootweb.JDBC;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class JdbcConnect {
    public Connection getConnection(){
        Connection connection = null;
        try{
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            String url = "jdbc:mysql://localhost:3306/App?createDatabaseIfNotExist=true";
            String username = "root";
            String password = "Hoang0339141241.";
            connection = DriverManager.getConnection(url,username,password);

        }catch (Exception exception){
            exception.printStackTrace();
        }
        return connection;
    }

}
