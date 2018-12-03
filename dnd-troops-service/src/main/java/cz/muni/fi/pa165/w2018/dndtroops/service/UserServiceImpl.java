package cz.muni.fi.pa165.w2018.dndtroops.service;

import cz.muni.fi.pa165.w2018.dndtroops.backend.dao.UserDao;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

/**
 * Implementation of User Service
 *
 * @author Marek Valko
 */
@Service
public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;

    @Override
    public User getById(long Id) {
        return userDao.getById(Id);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public void create(User user, String plainPassword) {
        if (plainPassword == null) {
            throw new NullPointerException("Plain password cannot be null");
        }
        if (plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        user.setPasswordHash(createHash(plainPassword));
        userDao.create(user);
    }


    /**
     * Creates hash for given String (password).
     * See https://crackstation.net/hashing-security.htm#javasourcecode
     *
     * @param password Password for hashing
     * @return Result of hashing - hashed password
     */
    private static String createHash(String password) {
        final int SALT_BYTE_SIZE = 24;
        final int HASH_BYTE_SIZE = 24;
        final int PBKDF2_ITERATIONS = 1000;
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        // Hash the password
        byte[] hash = pbkdf2(password.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        // format iterations:salt:hash
        return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
    }

    /**
     * Generates secure hash for password from parameters.
     *
     * @param password User password.
     * @param salt Additional string, which is included in hashing.
     * @param iterations Number of iterations.
     * @param bytes Length of key in bytes.
     * @return Raw key bytes.
     */
    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Validates password. Checks if given password matches with the one stored in database.
     *
     * @param password Password which is controlled.
     * @param correctHash Correct hash of password.
     * @return True if given password with correctHash match, false otherwise.
     */
    public static boolean validatePassword(String password, String correctHash) {
        if(password==null) return false;
        if(correctHash==null) throw new IllegalArgumentException("password hash is null");
        String[] params = correctHash.split(":");
        int iterations = Integer.parseInt(params[0]);
        byte[] salt = fromHex(params[1]);
        byte[] hash = fromHex(params[2]);
        byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length);
        return slowEquals(hash, testHash);
    }

    /**
     * Compares two byte arrays
     * @param a byte array
     * @param b byte array
     * @return true if both arrays are the same
     */
    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        return paddingLength > 0 ? String.format("%0" + paddingLength + "d", 0) + hex : hex;
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(long id) {
        User user = userDao.getById(id);
        if (user != null) {
            userDao.remove(user);
        }
    }

    @Override
    public boolean authenticate(User user, String plainPassword) {
        if (plainPassword == null) {
            throw new NullPointerException("Password cannot be null");
        }
        if (plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        return validatePassword(plainPassword, user.getPasswordHash());
    }

    @Override
    public User getByLogin(String login) {
        return userDao.getByLogin(login);
    }

    @Override
    public boolean isAdmin(User user) {
        return user.isAdmin();
    }

    @Override
    public boolean isUser(User user) {
        return user.isUser();
    }
}
