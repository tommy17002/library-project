package org.example.controllers;

import org.example.models.Users;
import org.example.services.ILibraryService;
import org.springframework.stereotype.Controller;

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
    public void updateUser() throws Exception {
        System.out.println("============= Update User ===============");
        System.out.print("Id => ");
        String id = scanner.nextLine();
        System.out.print("Update Full Name => ");
        String name = scanner.nextLine();
        System.out.print("Update Phone Number => ");
        String phoneNumber = scanner.nextLine();

        Users users = new Users();
        users.setFull_name(name);
        users.setPhone_number(phoneNumber);
        userService.update(users, id);
    }

    public void getById() throws Exception {
        System.out.println("========== Search User By Id ==========");
        System.out.print("Id => ");
        String id = scanner.nextLine();
        System.out.println(userService.findById(id));
    }

    // Di dalam controller, misalnya UsersController
    public void deleteUser() throws Exception {
        System.out.print("Id => ");
        String idStr = scanner.nextLine(); // Input pengguna dalam bentuk String

        try {
            Integer id = Integer.parseInt(idStr); // Konversi ke Integer
            userService.delete(idStr); // Panggil metode delete dari service
            System.out.println("User with id " + id + " deleted successfully.");
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
