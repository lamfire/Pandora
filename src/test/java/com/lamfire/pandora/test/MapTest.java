package com.lamfire.pandora.test;

import com.lamfire.pandora.FireMap;
import com.lamfire.pandora.test.benchmark.FireMapBenchmark;
import com.lamfire.pandora.test.tester.FireMapTester;
import com.lamfire.pandora.test.tester.MapTester;
import com.lamfire.utils.ArrayUtils;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-9-23
 * Time: 下午6:19
 * To change this template use File | Settings | File Templates.
 */
public class MapTest extends Abstract{

    public static void test() throws Exception {
        Map<byte[],byte[]> map = getPandora().getMap("java_map_tester");
        MapTester tester = new MapTester(map);
        tester.test();
    }

    public static void main(String[] args) throws Exception {

        test();
    }
}
