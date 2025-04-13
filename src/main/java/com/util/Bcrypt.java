package com.util;

import org.mindrot.jbcrypt.BCrypt;

public class  Bcrypt{

    // Hash a password with Bcrypt
    public  String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Check if the entered password matches the stored hash
    public  boolean checkPassword(String enteredPassword, String storedHash) {
        return BCrypt.checkpw(enteredPassword, storedHash);
    }
}
