package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Glass;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcGlassDao implements GlassDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcGlassDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Glass getGlassById(int glassId) {
        String sql = "SELECT * FROM glass WHERE glass_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, glassId);
            if (results.next()) {
                return mapRowToGlass(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return null;
    }

    @Override
    public List<Glass> getGlasses() {
        String sql = "SELECT * FROM glass;";
        List<Glass> glasses = new ArrayList<>();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Glass glass = mapRowToGlass(results);
                glasses.add(glass);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return glasses;
    }

    @Override
    public Glass getGlassByCocktailId(int cocktailId) {
        String sql = "SELECT * FROM glass g " +
                "JOIN glass_cocktail gc ON gc.glass_id = g.glass_id " +
                "WHERE cocktail_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cocktailId);
            if (results.next()) {
                return mapRowToGlass(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return null;
    }

    @Override
    public Glass createGlass(Glass glass) {
        String sql = "INSERT INTO glass (name) VALUES (?) RETURN glass_id;";
        try {
            int newGlassId = jdbcTemplate.update(sql, int.class, glass.getName());
            return getGlassById(newGlassId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void insertIntoGlassCocktail(int glassId, int cocktailId) {
        String sql = "INSERT INTO glass_cocktail (glass_id, cocktail_id) " +
                "VALUES (?, ?);";
        try {
            jdbcTemplate.update(sql, glassId, cocktailId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public Glass getGlassByName(String glassName) {
        String sql = "SELECT * FROM glass WHERE name ILIKE ?;";
        String searchString = "%" + glassName + "%";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, searchString);
            if (results.next()) {
                return mapRowToGlass(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        }
        return null;
    }

    private Glass mapRowToGlass(SqlRowSet rs) {
        Glass glass = new Glass();
        glass.setId(rs.getInt("glass_id"));
        glass.setName(rs.getString("name"));
        return glass;
    }
}
