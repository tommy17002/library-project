package org.example.repository;

import org.example.models.Categories;
import org.example.models.mappers.CategoryMapper;
import org.example.utils.IRandomStringGenerator;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class CategoriesRepository implements ILibraryRepository<Categories>{
    private JdbcTemplate jdbcTemplate;
    private IRandomStringGenerator randomStringGenerator;

    public CategoriesRepository(DataSource dataSource, IRandomStringGenerator randomStringGenerator) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.randomStringGenerator = randomStringGenerator;
    }

    @Override
    public Categories create(Categories categories) throws Exception {
        try {
            // Validasi input tidak boleh kosong
            if (categories.getCategory_name() == null || categories.getCategory_name().trim().isEmpty()) {
                throw new Exception("Category name cannot be empty");
            }

            // Generate a random ID
            categories.setId(randomStringGenerator.random());

            // Define the SQL statement with column names
            String sql = "INSERT INTO categories (id, category_name) VALUES (?, ?)";

            // Execute the update
            int result = jdbcTemplate.update(sql, categories.getId(), categories.getCategory_name());

            // Check if the insertion was successful
            if (result <= 0) {
                throw new Exception("Failed to insert category");
            }

            System.out.println("Category created successfully with ID: " + categories.getId());
            return categories;
        } catch (DataAccessException e) {
            // Lempar exception dengan pesan yang lebih deskriptif
            throw new Exception("Error while inserting category: " + e.getMessage(), e);
        }
    }



    @Override
    public List<Categories> findAll() throws Exception {
        try {
            return jdbcTemplate.query("select * from categories", new CategoryMapper());
        } catch (DataAccessException e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Categories> findById(String id) throws Exception {
        try {
            return jdbcTemplate.query("select * from categories where id=?", new CategoryMapper(), id);
        } catch (DataAccessException e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws Exception {
        try {
            jdbcTemplate.update("delete from categories where id = ?", id);
        } catch (DataAccessException e){
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public void update(Categories categories, String id) throws Exception {
        try {
            jdbcTemplate.update("update categories set category_name = ? where id = ?", categories.getCategory_name(), id);
        } catch (DataAccessException e){
            throw new Exception("Failed to update");
        }
    }
}
