package com.techelevator.dao;

import com.techelevator.model.Ingredient;
import java.util.List;


public interface IngredientDao {

    Ingredient getIngredientById(int ingredientId);

    List<Ingredient> getIngredients();

    List<Ingredient> getIngredientsByCocktailId(int cocktailId);

    Ingredient createIngredient(Ingredient ingredient);

    void insertIntoCocktailIngredient(int ingredientId, int cocktailId, double measurement, String unit);

    Ingredient getIngredientByName(String ingredientName);
}
