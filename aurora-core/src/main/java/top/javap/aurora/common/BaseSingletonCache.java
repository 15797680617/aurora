package top.javap.aurora.common;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/4
 **/
public abstract class BaseSingletonCache<K, V> implements SingletonCache<K, V> {

    private final ConcurrentMap<K, V> CACHE = new ConcurrentHashMap<>();
    private final Object MUTEX = new Object();

    @Override
    public V get(K key) {
        V result = CACHE.get(key);
        if (Objects.isNull(result)) {
            synchronized (MUTEX) {
                result = CACHE.get(key);
                if (Objects.isNull(result)) {
                    CACHE.put(key, result = createInstance(key));
                }
            }
        }
        return result;
    }

    protected abstract V createInstance(K key);
}