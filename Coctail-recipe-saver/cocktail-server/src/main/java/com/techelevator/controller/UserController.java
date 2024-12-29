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

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/users")
public class UserController {

    private final UserDao userDao;
    private final CocktailDao cocktailDao;

    public UserController(UserDao userDao, CocktailDao cocktailDao) {
        this.userDao = userDao;
        this.cocktailDao = cocktailDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getPublicUsers() {
        List<User> users = new ArrayList<>();
        users = userDao.getPublicUsers();

        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Users not found");
        }
        return users;
    }

//    @RequestMapping(path = "/{id}/cocktails", method = RequestMethod.GET)
//    public List<Cocktail> getPublicCocktails() {
//        List<Cocktail> cocktails = new ArrayList<>();
//        cocktails =
//    }
}
