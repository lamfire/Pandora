package com.lamfire.pandora.test;

import com.lamfire.pandora.test.tester.ListTester;
import com.lamfire.pandora.test.tester.SetTester;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-9-23
 * Time: 下午6:19
 * To change this template use File | Settings | File Templates.
 */
public class ListTest extends Abstract{

    public static void test() throws Exception {
        List<byte[]> list = getPandora().getList("java_list_tester");
        ListTester tester = new ListTester(list);
        tester.test();
    }

    public static void main(String[] args) throws Exception {
        test();
    }
}
