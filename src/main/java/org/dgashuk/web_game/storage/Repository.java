package org.dgashuk.web_game.storage;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Repository <K,V> implements Iterable<Map.Entry<K, V>> {
    private final Map<K,V> map;
    public Repository(){
        map = new ConcurrentHashMap<>();
    }
    public Repository(Map<K,V> repository){
        this.map = repository;
    }
    public V get(K kay){
        return map.get(kay);
    }

    public void save(K kay, V value){
        map.put(kay,value);
    }
    public boolean isExist(V value){
       return map.containsValue(value);
    }
    public boolean isEmpty(){
        return map.size() == 0;
    }
    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
         return map.entrySet().iterator();
    }

}
