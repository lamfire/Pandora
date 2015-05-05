package com.lamfire.pandora;

public interface FireQueue extends FireCollection {

    public void push(byte[] value);

    public byte[] pop();

    public byte[] peek();
}
