package com.techelevator.dao;

import com.techelevator.model.Glass;
import java.util.List;

public interface GlassDao {

    Glass getGlassById(int glassId);

    List<Glass> getGlasses();

    Glass createGlass(Glass glass);

    Glass getGlassByCocktailId(int cocktailId);

    Glass getGlassByName(String glassName);

    void insertIntoGlassCocktail(int glassId, int cocktailId);
}
