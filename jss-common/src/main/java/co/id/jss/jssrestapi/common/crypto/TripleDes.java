/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.jss.jssrestapi.common.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class TripleDes implements Kriptografi {

    private byte[] key;
    private Cipher cipher;
    private SecretKeySpec keySpec;
    private static final byte[] ivBytes = new byte[]{(byte) 0x20,
        (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20,
        (byte) 0x20, (byte) 0x20};
    IvParameterSpec paramSpec;

    public TripleDes(byte[] key24byte) {
        this.key = key24byte;
        paramSpec = new IvParameterSpec(ivBytes);
        try {
            cipher = Cipher.getInstance("DESede/CBC/ISO10126Padding");
            keySpec = new SecretKeySpec(this.key, "DESede");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public byte[] encrypt(byte[] data) {
        try {
            this.cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
            return this.cipher.doFinal(data);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public byte[] decrypt(byte[] data) {
        try {
            this.cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
            byte[] result = this.cipher.doFinal(data);

            return result;
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
