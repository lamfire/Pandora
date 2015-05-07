package com.lamfire.pandora;

import java.util.List;

public interface FireMap extends FireCollection {

    public void put(byte[] key, byte[] value);

    public List<byte[]> keys();

    public byte[] get(byte[] key);

    public void remove(byte[] key);

    public boolean exists(byte[] key);

}
