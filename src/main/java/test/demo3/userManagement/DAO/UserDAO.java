package test.demo3.userManagement.DAO;


import test.demo3.userManagement.MODEL.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// this DAO provide all CRUD operations of USERS
public class UserDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/demo"; //  JDBC URL
    private String jdbcUsername = "root"; //  database username
    private String jdbcPassword = ""; //  database password

    public UserDAO() {}

    // Method to establish a database connection
    private Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException
            e.printStackTrace();
            throw new SQLException("Database driver not found.");
        } catch (SQLException e) {
            // Handle SQLException
            e.printStackTrace();
            throw new SQLException("Error connecting to the database.");
        }
        return connection;
    }
    
    
    // SQL query
    private static final String INSERT_USER = "INSERT INTO users "+"(name , email , country ) VALUES" +"(?,?,?);";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id=? ;";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users ;";
    private static final String DELET_USER_ID = "DELETE from suers where id = ? ;";
    private static final String UPDATE_USERS_ID = "UPDATE users set name = ? , email = ? , country = ?  WHERE id = ? ;";

   


    // create USER
    public boolean insertUser(User user){
        boolean test=false;
        try ( Connection conn = getConnection()){
            PreparedStatement prepareStatement = conn.prepareStatement(INSERT_USER);
            prepareStatement.setString(1 , user.getName());
            prepareStatement.setString(2 , user.getEmail());
            prepareStatement.setString(3 , user.getCountry());
          test =  prepareStatement.executeUpdate() > 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return test;
    }
    // update USER
    public boolean updateUser (User user){
        boolean row = false ;
        try ( Connection conn = getConnection()){
            PreparedStatement prepareStatement = conn.prepareStatement(UPDATE_USERS_ID);
            prepareStatement.setString(1 , user.getName());
            prepareStatement.setString(2 , user.getEmail());
            prepareStatement.setString(3 , user.getCountry());
            prepareStatement.setInt(4 , user.getId());
             row =  prepareStatement.executeUpdate() > 0 ;
        }catch(Exception e){
            e.printStackTrace();
        }
        return row ;
    }

    // get user by id
    public User getUserById(int userId) {
        User user = null;
        try (Connection connection = getConnection()){
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String country = resultSet.getString("country");
                user = new User(id, name, email, country);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    // get ALL users
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = getConnection()){
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = null ;
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String country = resultSet.getString("country");
                user = new User(id, name, email, country);
                userList.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userList;
    }


    // delete user by id
    public boolean deleteUserById(int userId) {
        boolean deleted = false;
        try (Connection connection = getConnection()){
             PreparedStatement preparedStatement = connection.prepareStatement(DELET_USER_ID);
            preparedStatement.setInt(1, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            deleted = rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return deleted;
    }

}
