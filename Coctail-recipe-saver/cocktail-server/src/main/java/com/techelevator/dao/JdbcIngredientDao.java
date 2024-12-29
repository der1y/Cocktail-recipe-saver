package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Ingredient;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcIngredientDao implements IngredientDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcIngredientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Ingredient getIngredientById(int ingredientId) {
        String sql = "SELECT * FROM ingredients WHERE ingredient_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, ingredientId);
            if (results.next()) {
                return mapRowToIngredient(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return null;
    }

    @Override
    public Ingredient getIngredientByName(String ingredientName) {
        String sql = "SELECT * FROM ingredients WHERE name ILIKE ?;";
        String searchString = "%" + ingredientName + "%";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, searchString);
            if (results.next()) {
                return mapRowToIngredient(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return null;
    }
    @Override
    public List<Ingredient> getIngredients() {
        String sql = "SELECT * FROM ingredients;";
        List<Ingredient> listOfIngredients = new ArrayList<>();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Ingredient ingredient = mapRowToIngredient(results);
                listOfIngredients.add(ingredient);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return listOfIngredients;
    }

    @Override
    public List<Ingredient> getIngredientsByCocktailId(int cocktailId) {
        String sql = "SELECT * FROM ingredients i " +
                "JOIN cocktail_ingredient ci ON ci.ingredient_id = i.ingredient_id " +
                "WHERE cocktail_id = ?;";
        List<Ingredient> cocktailIngredients = new ArrayList<>();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cocktailId);
            while (results.next()) {
                Ingredient ingredient = mapRowToIngredientRecipe(results);
                cocktailIngredients.add(ingredient);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return cocktailIngredients;
    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        String sql = "INSERT INTO ingredients (name) VALUES (?) RETURNING ingredient_id;";
        Ingredient newIngredient;
        try {
            String ingredientName = ingredient.getName();
            int newIngredientId = jdbcTemplate.queryForObject(sql, int.class, ingredientName);
            newIngredient = getIngredientById(newIngredientId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newIngredient;
    }

    @Override
    public void insertIntoCocktailIngredient(int ingredientId, int cocktailId, double measurement, String unit) {
        String sql = "INSERT INTO cocktail_ingredient (cocktail_id, ingredient_id, measurement, unit) " +
                "VALUES (?, ?, ?, ?);";
        try {
            jdbcTemplate.update(sql, cocktailId, ingredientId, measurement, unit);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    private Ingredient mapRowToIngredient(SqlRowSet rs) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(rs.getInt("ingredient_id"));
        ingredient.setName(rs.getString("name"));
        return ingredient;
    }

    private Ingredient mapRowToIngredientRecipe(SqlRowSet rs) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(rs.getInt("ingredient_id"));
        ingredient.setName(rs.getString("name"));
        ingredient.setMeasurement(rs.getFloat("measurement"));
        ingredient.setUnit(rs.getString("unit"));
        return ingredient;
    }
}
