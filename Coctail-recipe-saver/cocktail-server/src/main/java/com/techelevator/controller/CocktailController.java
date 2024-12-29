package com.techelevator.controller;

import com.techelevator.dao.CocktailDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Cocktail;
import com.techelevator.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
//@RequestMapping("/cocktails")
public class CocktailController {

    private final CocktailDao cocktailDao;
    private final UserDao userDao;

    public CocktailController(CocktailDao dao, UserDao userDao) {
        this.cocktailDao = dao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/cocktails/{id}", method = RequestMethod.GET)
    public Cocktail getCocktailById(@PathVariable int id, Principal user, Authentication authentication) {
        int userId = getUserId(user);
        Cocktail retrievedCocktail = cocktailDao.getCocktailById(id);
        if (retrievedCocktail == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cocktail was not found");
        }
        if ((retrievedCocktail.getUserId() != userId & !retrievedCocktail.isPublic()) && !(hasAnyRole(authentication, "ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot access that cocktail");
        }

        return retrievedCocktail;
    }

    @RequestMapping(path = "/users/{id}/cocktails", method = RequestMethod.GET)
    public List<Cocktail> getUsersPublicCocktails(@PathVariable int id, Authentication authentication, Principal User) {
        List<Cocktail> results = new ArrayList<>();
        if (hasAnyRole(authentication, "ROlE_ADMIN")) {
            return cocktailDao.getUserCocktails(id);
        } else {
            return cocktailDao.getUserPublicCocktails(id);
        }

    }

    @RequestMapping(path = "/cocktails", method = RequestMethod.GET)
    public List<Cocktail> getCocktails(@RequestParam(defaultValue = "") String cocktailName, Principal user, Authentication authentication) {

        List<Cocktail> results = new ArrayList<>();

        if (hasAnyRole(authentication, "ROLE_ADMIN")) {
            return cocktailDao.getCocktails();
        }
        int userId = getUserId(user);

        if (cocktailName.isEmpty()) {
            results = cocktailDao.getUserCocktails(userId);
        } else {
            results = cocktailDao.getCocktailsByName(cocktailName);
        }

        if (results.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cocktails not found");
        }
        for (Cocktail cocktail : results) {
            if (cocktail.getUserId() != userId) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot access those cocktails");
            }
        }
        return results;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/cocktails", method = RequestMethod.POST)
    public Cocktail createCocktail(@RequestBody Cocktail cocktail, Principal user) {
        int userId = getUserId(user);

        return cocktailDao.createCocktail(cocktail, userId);
    }


    @RequestMapping(path = "/cocktails/{id}", method = RequestMethod.PUT)
    public Cocktail updateCocktail(@RequestBody Cocktail cocktail, @PathVariable int id, Principal user, Authentication authentication) {

        if (cocktail.getId() != id) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cocktail id must match id in path");
        }
        if (hasAnyRole(authentication, "ROLE_ADMIN")) {
            return cocktailDao.updateCocktail(cocktail);
        }
        getCocktailById(id, user, authentication);

        return cocktailDao.updateCocktail(cocktail);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/cocktails/{cocktailId}", method = RequestMethod.DELETE)
    public int deleteCocktail(@PathVariable int cocktailId, @RequestParam(required = false, defaultValue = "0") int id, Authentication authentication, Principal user) {
        if (hasAnyRole(authentication, "ROLE_ADMIN")) {
            return cocktailDao.deleteCocktail(cocktailId);
        }

        getCocktailById(cocktailId, user, authentication);
        return cocktailDao.deleteCocktail(cocktailId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/cocktails", method = RequestMethod.DELETE)
    public int deleteAllCocktails(Principal user, @RequestParam(required = false, defaultValue = "0") int id, Authentication authentication) {
        if (hasAnyRole(authentication, "ROLE_ADMIN")) {
            return cocktailDao.deleteAllCocktails(id);
        }
        int userId = getUserId(user);
        return cocktailDao.deleteAllCocktails(userId);
    }

    private int getUserId(Principal user) {
        String userName = user.getName();
        User retrievedUser = userDao.getUserByUsername(userName);
        int userId = retrievedUser.getId();
        return userId;
    }

    public static boolean hasAnyRole(Authentication authentication, String... roles) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        for (String role : roles) {
            boolean hasRole = authentication
                    .getAuthorities()
                    .stream()
                    .anyMatch(authority -> authority.getAuthority().equalsIgnoreCase(role));

            if (hasRole) {
                return true;
            }
        }

        return false;
    }

}
