package com.budgetmanagementapp.utility;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import com.budgetmanagementapp.exception.ResetPasswordException;
import org.springframework.stereotype.Component;

import static com.budgetmanagementapp.utility.MsgConstant.INVALID_RESET_PASSWORD_MSG;

@Component
public class EncryptionTool {

    private final static String algorithm = Constant.ENCRYPT_ALGORITHM;
    private static final SecretKey key = generateKey(256);
    private static final IvParameterSpec iv = generateIv();

    public static SecretKey generateKey(int n){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(Constant.SECRET_KEY);
            keyGenerator.init(n);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e){
            throw new ResetPasswordException(INVALID_RESET_PASSWORD_MSG);
        }
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static String encrypt(String input){
        try{
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] cipherText = cipher.doFinal(input.getBytes());
            String encrypted = Base64.getEncoder().encodeToString(cipherText);
            return URLEncoder.encode(encrypted, StandardCharsets.UTF_8);

        } catch (NoSuchPaddingException | NoSuchAlgorithmException |
                InvalidAlgorithmParameterException | InvalidKeyException |
                BadPaddingException | IllegalBlockSizeException e){
            throw new ResetPasswordException(INVALID_RESET_PASSWORD_MSG);
        }
    }

    public static String decrypt(String cipherText){
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] plainText = cipher.doFinal(Base64.getDecoder()
                    .decode(cipherText));
            return URLDecoder.decode(new String(plainText), StandardCharsets.UTF_8);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException |
                InvalidAlgorithmParameterException | InvalidKeyException |
                BadPaddingException | IllegalBlockSizeException e){
            throw new ResetPasswordException(INVALID_RESET_PASSWORD_MSG);
        }
    }
}
