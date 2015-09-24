package com.lamfire.pandora;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-9-23
 * Time: 下午3:28
 * To change this template use File | Settings | File Templates.
 */
public interface LDBIterator<E> extends Iterator<E> {

    public String getId();

    public void close();

    public long getLastUseTimeMillis();
}
