package com.example.demo.util;

import com.example.demo.model.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordUtil {

    private static String algorithName = "md5";
    private static int hashIteration = 2;

    public static void encryptPasswork(User user) {

        String newPassword = new SimpleHash(algorithName, user.getPassword(), ByteSource.Util.bytes(user.getUsername()), hashIteration).toHex();

        user.setPassword(newPassword);
    }
}
