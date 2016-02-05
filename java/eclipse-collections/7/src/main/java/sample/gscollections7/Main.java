package sample.gscollections7;

import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.eclipse.collections.impl.factory.primitive.IntObjectMaps;

public class Main {

    public static void main(String[] args) {
        MutableIntObjectMap<String> map = IntObjectMaps.mutable.of();
        
        map.put(10, "hoge");
        
    }
}
