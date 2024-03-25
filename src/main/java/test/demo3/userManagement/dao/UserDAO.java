package test.demo3.userManagement.dao;


import test.demo3.userManagement.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// this DAO provide all CRUD operations of USERS
public class UserDAO {
    private String jdbcURL = "jdbc:mysql://hostname:3306/demo?useSSL=false"; // Your JDBC URL
    private String jdbcUsername = "root"; // Your database username
    private String jdbcPassword = "password"; // Your database password


    // SQL query
    private static final String INSERT_USER = "INSERT INTO users "+"(name , email , country ) VALUES" +"(?,?,?);";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id=? ;";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users ;";
    private static final String DELET_USER_ID = "DELETE from suers where id = ? ;";
    private static final String UPDATE_USERS_ID = "UPDATE users set name = ? , email = ? , country = ?  WHERE id = ? ;";

    // Method to establish a database connection
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }


    // create USER
    public void insertUser(User user){
        try ( Connection conn = getConnection()){
            PreparedStatement prepareStatement = conn.prepareStatement(INSERT_USER);
            prepareStatement.setString(1 , user.getName());
            prepareStatement.setString(2 , user.getEmail());
            prepareStatement.setString(3 , user.getCountry);
            prepareStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    // update USER
    public int updateUser (User user){
        try ( Connection conn = getConnection()){
            PreparedStatement prepareStatement = conn.prepareStatement(UPDATE_USERS_ID);
            prepareStatement.setString(1 , user.getName());
            prepareStatement.setString(2 , user.getEmail());
            prepareStatement.setString(3 , user.getCountry());
            prepareStatement.setString(4 , user.getId());
            return  prepareStatement.executeUpdate() ;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
