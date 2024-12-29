package com.techelevator.dao;

import java.util.List;
import com.techelevator.model.Cocktail;

public interface CocktailDao {

    Cocktail getCocktailById(int cocktailId);

    List<Cocktail> getCocktails();

    List<Cocktail> getCocktailsByName(String cocktailName);

    List<Cocktail> getUserCocktails(int userId);

    List<Cocktail> getUserPublicCocktails(int userId);

    Cocktail createCocktail(Cocktail newCocktail, int userId);

    Cocktail updateCocktail(Cocktail cocktail);

    int deleteCocktail(int cocktailIdg);

    int deleteAllCocktails(int userId);

}
