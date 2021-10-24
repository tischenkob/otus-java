package ru.otus.cache;

import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    private final Map<K, V> weakMap = new WeakHashMap<>();
    private final Map<HwListener<K, V>, Boolean> listeners = new WeakHashMap<>();

    @Override
    public void put(K key, V value) {
        weakMap.put(key, value);
        notifyListeners(key, value, "put");
    }

    @Override
    public void remove(K key) {
        var value = weakMap.remove(key);
        notifyListeners(key, value, "remove");
    }

    @Override
    public V get(K key) {
        return weakMap.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.put(listener, Boolean.TRUE);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(K key, V value, String action) {
        listeners.keySet().forEach(listener -> listener.notify(key, value, action));
    }
}
