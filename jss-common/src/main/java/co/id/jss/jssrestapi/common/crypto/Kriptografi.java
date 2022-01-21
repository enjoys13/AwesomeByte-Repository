/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.jss.jssrestapi.common.crypto;

public interface Kriptografi {
    public byte[] encrypt(byte[] data);
	public byte[] decrypt(byte[] data);
}
