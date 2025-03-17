package Map61B;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;

public class MapHelper {
    //输入map和需要查询的键值，如果存在，则返回对应的值，不存在返回null
    public static <K, V> V get(Map61B<K, V> map, K key) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return null;
    }

    public static <K extends Comparable<K>, V> K maxKey(Map61B<K, V> map) {
        List<K> Keylist = map.keys();
        K largest = Keylist.get(0);
        for (K k : Keylist) {
            if (k.compareTo(largest) > 0) {
                largest = k;
            }
        }
        return largest;
    }

    @Test
    public void tstGet() {
        Map61B<String, Integer> m = new ArrayMap<String, Integer>();
        m.put("one", 1);
        Integer actual = MapHelper.get(m, "one");
        Integer expected = 1;
        assertEquals(expected, actual);

        assertEquals(null, MapHelper.get(m, "dsf"));
    }

}
