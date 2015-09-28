package com.lamfire.pandora;

public interface Increment extends Container {

    public void increment(String name);

    public void increment(String name, long step);

    public long get(String name);

    public void set(String name, long value);

    public long incrementAndGet(String name);

    public long incrementAndGet(String name, long step);

    public long remove(String name);
}
