package test.demo3.userManagement.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import test.demo3.userManagement.DAO.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import test.demo3.userManagement.MODEL.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class UserServlet extends HttpServlet {
    private UserDAO userDAO;

    public UserServlet(){
        this.userDAO = new UserDAO();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        if (action != null) {
            switch (action) {
                case "/new":
                    // Call method to insert new user
                    ShowNewForm(request, response);
                    break;
                case "/update":
                    // Call method to update user
                    updateUser(request, response);
                    break;
                case "/delete":
                    // Call method to delete user
                    deleteUser(request, response);
                    break;
                case "/insert":
                    // Call method to get user by ID
                    insertUser(request, response);
                    break;
                case "/list":
                    // Call method to get user by ID
                    listAllUsers(request, response);
                    break;
                case "/edit":
                    // Call method to get all users
                    try {
                        showEditForm(request, response);

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    listAllUsers(request, response);
                    break;
            }

        }
    }
   private void ShowNewForm(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
            dispatcher.forward(request,response);
        }
    private void insertUser(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        // Extract parameters from request
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        // Create a User object with extracted parameters
        User user = new User(name, email, country);

        // Call method in UserDAO to insert user
        UserDAO userDAO = new UserDAO(); // Initialize your UserDAO object
        boolean success = userDAO.insertUser(user);

        // Send response based on success or failure
        if (success) {
            response.sendRedirect("list");
        } else {
            response.getWriter().println("Failed to insert user.");
        }
    }
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        // Get the user ID parameter from the request
        int userId = Integer.parseInt(request.getParameter("id"));

        // Call the delete method from the DAO
        UserDAO userDAO = new UserDAO(); // Initialize your UserDAO object
        boolean deleted = userDAO.deleteUserById(userId);

        // Send response back to the client
        if (deleted) {
            response.sendRedirect("list");
        } else {
            response.getWriter().println("Failed to delete user with ID " + userId + ".");
        }
    }
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the user ID parameter from the request
        int userId = Integer.parseInt(request.getParameter("id"));

        // Call the getUserById method from the DAO
        UserDAO userDAO = new UserDAO(); // Initialize your UserDAO object
        User user = userDAO.getUserById(userId);

        // Add the user object as an attribute to the request
        request.setAttribute("user", user);

        // Forward the request to the JSP page for editing
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-flow.jsp");
        dispatcher.forward(request, response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the user ID parameter from the request
        int userId = Integer.parseInt(request.getParameter("id"));

        // Call the getUserById method from the DAO to fetch the existing user details
        UserDAO userDAO = new UserDAO(); // Initialize your UserDAO object
        User existingUser = userDAO.getUserById(userId);

        // Update the existing user object with new data
        if (existingUser != null) {
            existingUser.setName(request.getParameter("name"));
            existingUser.setEmail(request.getParameter("email"));
            existingUser.setCountry(request.getParameter("country"));

            // Call the updateUser method from the DAO to update the user in the database
            boolean updated = userDAO.updateUser(existingUser);

            if (updated) {
                response.sendRedirect("list");
            } else {
                response.getWriter().println("Failed to update user with ID " + userId + ".");
            }
        } else {
            response.getWriter().println("User with ID " + userId + " does not exist.");
        }
    }

    // GET ALL USERS CONTROLLER
    private void listAllUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        List<User> userList = userDAO.getAllUsers();
        request.setAttribute("userList", userList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
        dispatcher.forward(request, response);
    }


}

