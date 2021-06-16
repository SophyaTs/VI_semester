package com.knu;

import java.sql.*;

class DatabaseConnection {
    static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/OOPpr2", "postgres", "password");
            } catch (Exception e) {
                System.out.println("Connection failure.");
                e.printStackTrace();
            }
        }

        return connection;
    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
}

public class ClientDao {
    Connection conn;
    
    public ClientDao() {
        conn = DatabaseConnection.getConnection();
    }

    public String getKeyByID(Integer id) throws Exception {
        String sql = "SELECT key FROM users WHERE id = ?";
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        prepStmt.setInt(1, id);

        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()){
            return rs.getString("key");
        }

        return "";
    }

    public void setKeyByID(Integer id, String key) throws Exception {
        String sql0 = "SELECT COUNT(*) FROM users WHERE id = ?";
        PreparedStatement prepStmt = conn.prepareStatement(sql0);
        prepStmt.setInt(1, id);

        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()){
            int count = rs.getInt(1);
            if (count == 0) {
                Statement stmt = conn.createStatement();
                String sql1 = "INSERT INTO users(id, key) VALUES(?, ?)";
                PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
        
                prepStmt1.setInt(1, id); 
                prepStmt1.setString(2, key);
        
                prepStmt1.executeUpdate();
            } else {
                Statement stmt = conn.createStatement();
                String sql1 = "UPDATE users SET key = ? WHERE id = ?";
                PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
        
                prepStmt1.setInt(2, id); 
                prepStmt1.setString(1, key);
        
                prepStmt1.executeUpdate();
            }
        }
    }
}
