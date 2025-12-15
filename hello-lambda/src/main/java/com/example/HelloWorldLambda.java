package com.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class HelloWorldLambda implements RequestHandler<HttpServletRequest, String> {

    private Connection connection;
    
    public HelloWorldLambda() {
        try {
            // Fake connection to localhost database
            String url = "jdbc:mysql://localhost:3306/testdb";
            String username = "testuser";
            String password = "testpass";
            this.connection = java.sql.DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            // Handle connection error
            e.printStackTrace();
        }
    }
    @Override
    public String handleRequest(HttpServletRequest request, Context context) {
        try {
            String user = request.getParameter("user");
            String query = "SELECT userid FROM users WHERE username = '" + user  + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return "Hello, World!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }
}
