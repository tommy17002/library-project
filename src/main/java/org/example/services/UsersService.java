package org.example.services;

import org.example.models.Categories;
import org.example.models.Users;
import org.example.repository.ILibraryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService implements ILibraryService<Users>{
    private ILibraryRepository<Users> usersRepository;

    public UsersService(ILibraryRepository<Users> usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Users create(Users users) throws Exception {
        try {
            List<Users> users1 = usersRepository.findAll();
            if(users1.stream().anyMatch(existingCategory -> existingCategory.getFull_name().equalsIgnoreCase(users.getFull_name()))){
                throw new DataIntegrityViolationException("User Already Exist");
            }
            usersRepository.create(users);
            System.out.println("Create User Success");
            return users;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Users> findAll() throws Exception {
        try {
            List<Users> users = usersRepository.findAll();
            if(users.isEmpty()){
                throw new Exception("Data User Not Found!");
            }
            return usersRepository.findAll();
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Users> findById(String id) throws Exception {
        try {
            List<Users> users = usersRepository.findById(id);
            if(users.isEmpty()){
                throw new Exception("ID Not Found");
            }
            return usersRepository.findById(id);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws Exception {
        try {
            usersRepository.delete(id);
            System.out.println("User with id " + id + " successfully deleted.");
        } catch (Exception e) {
            throw new Exception("Failed to delete user with id " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Users users, String id) throws Exception {
        try {
            // Retrieve the user by ID
            List<Users> existingUsers = usersRepository.findById(id);

            // Check if the user exists
            if (existingUsers.isEmpty()) {
                throw new Exception("ID Not Found");
            }

            // Check for duplicate full names
            List<Users> allUsers = usersRepository.findAll();
            boolean isDuplicate = allUsers.stream()
                    .anyMatch(existingUser ->
                            !existingUser.getId().equals(id) &&
                                    existingUser.getFull_name().equalsIgnoreCase(users.getFull_name()));

            if (isDuplicate) {
                throw new DataIntegrityViolationException("User Already Exists");
            }

            // Update the user details
            Users existingUser = existingUsers.get(0);
            existingUser.setFull_name(users.getFull_name());
            // Perform the update operation
            usersRepository.update(existingUser, id);

            System.out.println("Update Success");
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Data integrity violation: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("An error occurred: " + e.getMessage());
        }
    }

}
