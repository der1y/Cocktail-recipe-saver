package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Cocktail;
import com.techelevator.model.Glass;
import com.techelevator.model.Ingredient;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCocktailDao implements CocktailDao {

    private final JdbcTemplate jdbcTemplate;
    private final IngredientDao ingredientDao;
    private final GlassDao glassDao;

    public JdbcCocktailDao(JdbcTemplate jdbcTemplate, IngredientDao ingredientDao, GlassDao glassDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.ingredientDao = ingredientDao;
        this.glassDao = glassDao;
    }

    @Override
    public Cocktail getCocktailById(int cocktailId) {
        String sql = "SELECT name, c.cocktail_id, instructions, user_id, is_public FROM cocktails c " +
                "JOIN cocktail_book cb ON cb.cocktail_id = c.cocktail_id " +
                "WHERE c.cocktail_id = ?;";
        // Get list of ingredients for that cocktail
        List<Ingredient> ingredientList = ingredientDao.getIngredientsByCocktailId(cocktailId);

        // Get glass for that cocktail
        Glass glass = glassDao.getGlassByCocktailId(cocktailId);
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cocktailId);
            if (results.next()) {
                Cocktail cocktail = mapRowToCocktail(results, ingredientList, glass);
                return cocktail;
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return null;
    }

    @Override
    public List<Cocktail> getCocktails() {
        List<Cocktail> cocktailList = new ArrayList<>();

        String sql = "SELECT name, c.cocktail_id, instructions, user_id, is_public FROM cocktails c " +
                "JOIN cocktail_book cb ON cb.cocktail_id = c.cocktail_id;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                // Get cocktail id
                int cocktailId = results.getInt("cocktail_id");

                // Get list of ingredients for that cocktail
                List<Ingredient> ingredientList = ingredientDao.getIngredientsByCocktailId(cocktailId);

                // Get glass for the cocktail
                Glass glass = glassDao.getGlassByCocktailId(cocktailId);

                // Map all objects to cocktail
                Cocktail cocktail = mapRowToCocktail(results, ingredientList, glass);
                cocktailList.add(cocktail);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return cocktailList;
    }

    @Override
    public List<Cocktail> getCocktailsByName(String cocktailName) {
        List<Cocktail> cocktailListByName = new ArrayList<>();
        String sql = "SELECT * FROM cocktails c " +
                "JOIN cocktail_book cb ON cb.cocktail_id = c.cocktail_id " +
                "WHERE name ILIKE ?;";

        String searchString = "%" + cocktailName + "%";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, searchString);
            while (results.next()) {
                int cocktailId = results.getInt("cocktail_id");
                List<Ingredient> ingredientList = ingredientDao.getIngredientsByCocktailId(cocktailId);
                Glass glass = glassDao.getGlassByCocktailId(cocktailId);
                Cocktail cocktail = mapRowToCocktail(results, ingredientList, glass);
                cocktailListByName.add(cocktail);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return cocktailListByName;
    }

    @Override
    public List<Cocktail> getUserCocktails(int userId) {
        List<Cocktail> cocktailBookList = new ArrayList<>();
        String sql = "SELECT * FROM cocktail_book cb " +
                "JOIN cocktails c ON c.cocktail_id = cb.cocktail_id " +
                "WHERE user_id = ? " +
                "ORDER BY name;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                int cocktailId = results.getInt("cocktail_id");
                List<Ingredient> ingredientList = ingredientDao.getIngredientsByCocktailId(cocktailId);
                Glass glass = glassDao.getGlassByCocktailId(cocktailId);
                Cocktail cocktail = mapRowToCocktail(results, ingredientList, glass);
                cocktailBookList.add(cocktail);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return cocktailBookList;
    }

    @Override
    public List<Cocktail> getUserPublicCocktails(int userId) {
        List<Cocktail> cocktailBookList = new ArrayList<>();
        String sql = "SELECT * FROM cocktail_book cb " +
                "JOIN cocktails c ON c.cocktail_id = cb.cocktail_id " +
                "WHERE user_id = ? AND is_public = true " +
                "ORDER BY name;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                int cocktailId = results.getInt("cocktail_id");
                List<Ingredient> ingredientList = ingredientDao.getIngredientsByCocktailId(cocktailId);
                Glass glass = glassDao.getGlassByCocktailId(cocktailId);
                Cocktail cocktail = mapRowToCocktail(results, ingredientList, glass);
                cocktailBookList.add(cocktail);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return cocktailBookList;
    }

    @Override
    public Cocktail createCocktail(Cocktail cocktail, int userId) {
        Cocktail newCocktail;

        //Get glass
        String glassName = cocktail.getGlass().getName();

        // Get a list of ingredients
        List<Ingredient> ingredients = cocktail.getIngredients();

        // Sql statements to update tables
        String insertCocktailSql = "INSERT INTO cocktails (name, instructions) " +
                                    "VALUES (?, ?) RETURNING cocktail_id;";
        String insertCocktailBookSql = "INSERT INTO cocktail_book (cocktail_id, user_id, is_public) " +
                                    "VALUES (?, ?, ?);";

        try {
            // Add new cocktail to cocktail table and get that id
            int newCocktailId = jdbcTemplate.queryForObject(insertCocktailSql, int.class,
                                cocktail.getName(), cocktail.getInstructions());

            // Associate userId with the new cocktails id
            jdbcTemplate.update(insertCocktailBookSql, newCocktailId, userId, cocktail.isPublic());

            // Associate ingredient with cocktail if it exits, if not add it first and then associate it
            for (Ingredient ingredient : ingredients) {
                Ingredient ingredientRetrieved = ingredientDao.getIngredientByName(ingredient.getName());
                if (ingredientRetrieved != null) {
                    ingredientDao.insertIntoCocktailIngredient(ingredientRetrieved.getId(), newCocktailId, ingredient.getMeasurement(), ingredient.getUnit());
                } else {
                    Ingredient newIngredient = ingredientDao.createIngredient(ingredient);
                    ingredientDao.insertIntoCocktailIngredient(newIngredient.getId(), newCocktailId, ingredient.getMeasurement(), ingredient.getUnit());
                }
            }

            // Get glass and associate it with the cocktail
            Glass glass = glassDao.getGlassByName(glassName);
            int glassId = glass.getId();
            glassDao.insertIntoGlassCocktail(glassId, newCocktailId);

            newCocktail = getCocktailById(newCocktailId);

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newCocktail;
    }

    @Override
    public Cocktail updateCocktail(Cocktail cocktail) {
        Cocktail updatedCocktail = null;
        String glassName = cocktail.getGlass().getName();
        List<Ingredient> ingredients = cocktail.getIngredients();
        String updatedCocktailSql = "UPDATE cocktails " +
                "SET name = ?, instructions = ? " +
                "WHERE cocktail_id = ?;";
        String updatedCocktailBookSql = "UPDATE cocktail_book " +
                "SET cocktail_id = ?, user_id = ?, is_public = ? " +
                "WHERE cocktail_id = ?;";
        String deleteIngredients = "DELETE FROM cocktail_ingredient WHERE cocktail_id = ?;";

        try {
            
            // Update cocktail table
            int numberOfRows = jdbcTemplate.update(updatedCocktailSql, cocktail.getName(), cocktail.getInstructions(), cocktail.getId());
            if (numberOfRows == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
            // Update cocktail_book table
            jdbcTemplate.update(updatedCocktailBookSql, cocktail.getId(), cocktail.getUserId(), cocktail.isPublic(), cocktail.getId());
            
            // Delete ingredients associated with the cocktail to update
            int rowsDeleted = jdbcTemplate.update(deleteIngredients, cocktail.getId());
            if (rowsDeleted == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
            for (Ingredient ingredient : ingredients) {
                Ingredient ingredientRetrieved = ingredientDao.getIngredientByName(ingredient.getName());
                if (ingredientRetrieved != null) {
                    ingredientDao.insertIntoCocktailIngredient(ingredientRetrieved.getId(), cocktail.getId(), ingredient.getMeasurement(), ingredient.getUnit());
                } else {
                    Ingredient newIngredient = ingredientDao.createIngredient(ingredient);
                    ingredientDao.insertIntoCocktailIngredient(newIngredient.getId(), cocktail.getId(), ingredient.getMeasurement(), ingredient.getUnit());
                }
            }

            // Update glass associated with the cocktail
            Glass glass = glassDao.getGlassByName(glassName);
            int glassId = glass.getId();
            glassDao.insertIntoGlassCocktail(glassId, cocktail.getId());

            updatedCocktail = getCocktailById(cocktail.getId());

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedCocktail;
    }

    @Override
    public int deleteCocktail(int cocktailId) {
        String deleteIngredientCocktail = "DELETE FROM cocktail_ingredient WHERE cocktail_id = ?;";
        String deleteCocktailBook = "DELETE FROM cocktail_book WHERE cocktail_id = ?;";
        String deleteGlassCocktail = "DELETE FROM glass_cocktail WHERE cocktail_id = ?;";
        String deleteCocktail = "DELETE FROM cocktails WHERE cocktail_id = ?;";
        int numberOfRows;
        try {
            jdbcTemplate.update(deleteIngredientCocktail, cocktailId);
            jdbcTemplate.update(deleteCocktailBook, cocktailId);
            jdbcTemplate.update(deleteGlassCocktail, cocktailId);
            numberOfRows = jdbcTemplate.update(deleteCocktail, cocktailId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    @Override
    public int deleteAllCocktails(int userId) {
        List<Cocktail> userCocktails = getUserCocktails(userId);
        String deleteCocktailUser = "DELETE FROM cocktail_book WHERE user_id = ?;";
        String deleteIngredientCocktail = "DELETE FROM cocktail_ingredient WHERE cocktail_id = ?;";
        String deleteGlassCocktail = "DELETE FROM glass_cocktail WHERE cocktail_id = ?;";
        String deleteCocktail = "DELETE FROM cocktails WHERE cocktail_id = ?;";
        int numberOfRows;
        try {
            numberOfRows = jdbcTemplate.update(deleteCocktailUser, userId);
            for (Cocktail cocktail : userCocktails) {
                int cocktailId = cocktail.getId();
                jdbcTemplate.update(deleteIngredientCocktail, cocktailId);
                jdbcTemplate.update(deleteGlassCocktail, cocktailId);
                jdbcTemplate.update(deleteCocktail, cocktailId);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    private Cocktail mapRowToCocktail(SqlRowSet rs, List<Ingredient> ingredients, Glass glass) {
        Cocktail cocktail = new Cocktail();
        cocktail.setUserId(rs.getInt("user_id"));
        cocktail.setId(rs.getInt("cocktail_id"));
        cocktail.setName(rs.getString("name"));
        cocktail.setInstructions(rs.getString("instructions"));
        cocktail.setIngredients(ingredients);
        cocktail.setGlass(glass);
        cocktail.setPublic(rs.getBoolean("is_public"));
        return cocktail;
    }
}
