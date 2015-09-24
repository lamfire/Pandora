package com.lamfire.pandora.test;

import com.lamfire.pandora.test.tester.CollectionTester;
import com.lamfire.pandora.test.tester.SetTester;

import java.util.Collection;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-9-23
 * Time: 下午6:19
 * To change this template use File | Settings | File Templates.
 */
public class CollectionTest extends Abstract{

    public static void test() throws Exception {
        Collection<byte[]> col = getPandora().getCollection("java_collection_tester");
        CollectionTester tester = new CollectionTester(col);
        tester.test();
    }

    public static void main(String[] args) throws Exception {
        test();
    }
}
