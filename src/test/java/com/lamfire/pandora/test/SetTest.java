package com.lamfire.pandora.test;

import com.lamfire.pandora.test.tester.MapTester;
import com.lamfire.pandora.test.tester.SetTester;

import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-9-23
 * Time: 下午6:19
 * To change this template use File | Settings | File Templates.
 */
public class SetTest extends Abstract{

    public static void test() throws Exception {
        Set<byte[]> set = getPandora().getSet("java_set_tester");
        SetTester tester = new SetTester(set);
        tester.test();
    }

    public static void main(String[] args) throws Exception {
        test();
    }
}
