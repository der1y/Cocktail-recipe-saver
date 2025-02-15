package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Model class representing an application user.
 *
 * Contains information about the user - their id, username, address information,
 * password (hashed) and authorities (user roles).
 */
public class User {

   private int id;
   private String username;
   private String name;
   private String city;
   private String stateCode;
   @JsonIgnore
   private String password;
   @JsonIgnore
   private boolean activated;
   private Set<Authority> authorities = new HashSet<>();

   public User() { }


   public User(int id, String username, String password, String authorities, String name, String city, String stateCode) {
      this.id = id;
      this.username = username;
      this.password = password;
      if(authorities != null) this.setAuthorities(authorities);
      this.name = name;
      this.city = city;
      this.stateCode = stateCode;
      this.activated = true;
   }

   public User(String username, String password, String authorities, String name, String city, String stateCode) {
      this(0, username, password, authorities, name, city, stateCode);
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public boolean isActivated() {
      return activated;
   }

   public void setActivated(boolean activated) {
      this.activated = activated;
   }

   public Set<Authority> getAuthorities() {
      return authorities;
   }

   public String getAuthoritiesString() {
      String authString = "";
      for (Authority auth : authorities) {
         if (authString.length() == 0) {
            authString += auth.getName();
         } else {
            authString += "," + auth.getName();
         }
      }
      return authString;
   }


   public void setAuthorities(Set<Authority> authorities) {
      this.authorities = authorities;
   }

   public void setAuthorities(String authorities) {
      String[] roles = authorities.split(",");
      for(String role : roles) {
         String authority = role.contains("ROLE_") ? role : "ROLE_" + role.toUpperCase();
         this.authorities.add(new Authority(authority));
      }
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getCity() {
      return city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getStateCode() {
      return stateCode;
   }

   public void setStateCode(String stateCode) {
      this.stateCode = stateCode;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return id == user.id &&
              activated == user.activated &&
              Objects.equals(username, user.username) &&
              Objects.equals(password, user.password) &&
              Objects.equals(name, user.name) &&
              Objects.equals(city, user.city) &&
              Objects.equals(stateCode, user.stateCode) &&
              Objects.equals(authorities, user.authorities);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, username, password, activated, authorities);
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", username='" + username + '\'' +
              ", name='" + name + '\'' +
              ", city='" + city + '\'' +
              ", state='" + stateCode + '\'' +
              ", activated=" + activated +
              ", authorities=" + authorities +
              '}';
   }
}
