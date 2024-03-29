package com.hermesstore.projetexamen2021.utils;

/**
 * utility class for the project
 * @author Daryl MEDENOU
 */
public class Util {
    /**
     * method to check email validity
     * @param email
     * @return true if email is valid, false otherwise
     */
     public static boolean isValidEmail(String email) {
         return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
     }
}
