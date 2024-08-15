package org.example.controllers;

import org.example.models.Users;
import org.example.services.ILibraryService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class UsersController {

    private Scanner scanner = new Scanner(System.in);
    private ILibraryService<Users> userService;

    public UsersController(ILibraryService<Users> userService) {
        this.userService = userService;
    }
    public void addUser() {
        System.out.println("============= Create User ===============");
        System.out.print("Full Name => ");
        String name = scanner.nextLine().trim();
        System.out.print("Phone Number => ");
        String phoneNumber = scanner.nextLine().trim();

        // Validasi input
        if (name.isEmpty() || phoneNumber.isEmpty()) {
            System.out.println("Full Name and Phone Number cannot be empty.");
            return;
        }

        Users user = new Users();
        user.setFull_name(name);
        user.setPhone_number(phoneNumber);

        try {
            userService.create(user);
            System.out.println("User successfully created.");
        } catch (Exception e) {
            System.err.println("Failed to create user: " + e.getMessage());
        }
    }

    public void updateUser() {
        try {
            System.out.println("============= Update User ===============");
            System.out.print("Id => ");
            String id = scanner.nextLine();

            if (id == null || id.trim().isEmpty()) {
                System.out.println("Error: ID cannot be empty.");
                return;
            }

            List<Users> usersList = userService.findById(id);

            if (usersList.isEmpty()) {
                System.out.println("Error: User with ID " + id + " not found.");
                return;
            }

            Users existingUser = usersList.get(0);

            System.out.print("Update Full Name => ");
            String name = scanner.nextLine();
            System.out.print("Update Phone Number => ");
            String phoneNumber = scanner.nextLine();

            existingUser.setFull_name(name);
            existingUser.setPhone_number(phoneNumber);

            userService.update(existingUser, id);

            System.out.println("Update Success: User with ID " + id + " has been updated.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void getById() {
        try {
            System.out.println("========== Search User By Id ==========");
            System.out.print("Id => ");
            String id = scanner.nextLine();

            // Check if the ID is valid (i.e., not empty or null)
            if (id == null || id.trim().isEmpty()) {
                System.out.println("Error: ID cannot be empty.");
                return;
            }

            // Fetch users by ID
            List<Users> usersList = userService.findById(id);

            // Check if any user with the given ID exists
            if (usersList.isEmpty()) {
                System.out.println("Error: User with ID " + id + " not found.");
            } else {
                // Print the details of the first user in the list
                Users user = usersList.get(0);
                System.out.println("User Details: ");
                System.out.println("ID: " + user.getId());
                System.out.println("Full Name: " + user.getFull_name());
                System.out.println("Phone Number: " + user.getPhone_number());
            }

        } catch (Exception e) {
            // Handle and display any exceptions
            System.out.println("Error: " + e.getMessage());
        }
    }


    public void deleteUser() throws Exception {
        System.out.println("============= Delete User ===============");
        System.out.print("Id => ");
        String idStr = scanner.nextLine();

        try {
            Integer id = Integer.parseInt(idStr);
            userService.delete(idStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid id format. Please enter a valid integer.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void getAllUsers() throws Exception {
        System.out.println("============ All Users =============");
        userService.findAll().forEach(System.out::println);
    }
}
