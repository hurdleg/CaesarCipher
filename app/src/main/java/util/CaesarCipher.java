package util;

import static com.algonquincollege.hurdleg.caesarcipher.Constants.*;

/**
 * Caesar Cipher
 *
 * @author Gerald.Hurdle@AlgonquinCollege.com
 */
public class CaesarCipher {

    private static final int MAX_ROTATION;

    static {
        MAX_ROTATION = ROTATIONS.length();
    }

    private CaesarCipher() {
        // NOP
    }

    static String decrypt(String encryptedMessage) {
        return CaesarCipher.decrypt(encryptedMessage, ROT13 );
    }

    public static String decrypt( String encryptedMessage, int rotation ) {
        return CaesarCipher.encrypt( encryptedMessage, MAX_ROTATION - rotation );
    }

    static String encrypt(String plainMessage) {
        return CaesarCipher.encrypt( plainMessage, ROT13 );
    }

    /**
     * Encrypt the plain-message using the rotation value.
     *
     * Pseudo-code:
     *
     * 1. IF plainMessage is null THEN return the empty string ""
     * 2. Create a local variable, named encryptedMessage, of type StringBuffer. Use the
     *    appropriate StringBuffer constructor that accepts 1 argument of type String; use the
     *    plainMessage as the constructor's parameter.
     * 3. For each character in plainMessage:
     * 3a. IF the character is a letter ( Character.isLetter(c) )
     *     THEN IF the character is lower-case
     *          THEN encrypt the character (remember to use 'a' because the character is a lower-case
     *               letter)
     *          ELSE encrypt the character (remember to use 'A')
     *          To the encryptedMessage, set the character at index (from the for loop) with the
     *          encrypted character.
     *
     *     RETURN encryptedMessage
     */
    public static String encrypt( String plainMessage, int rotation ) {
        char alpha;
        char c;

        if ( plainMessage == null ) return "";

        StringBuffer encryptedMessage = new StringBuffer( plainMessage );

        for( int i = 0; i < plainMessage.length(); i++ ) {
            c = plainMessage.charAt( i );
            if ( Character.isLetter(c) ) {
                alpha = Character.isLowerCase( c ) ? 'a' : 'A';			// ternary operator
                encryptedMessage.setCharAt( i, (char) (((c - alpha + rotation) % MAX_ROTATION) + alpha) );
            }
        }

        return encryptedMessage.toString();
    }
}
