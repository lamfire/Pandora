package com.lamfire.pandora;

public interface Queue extends Container {

    public void push(byte[] value);

    public byte[] pull();

    public byte[] peek();
}
