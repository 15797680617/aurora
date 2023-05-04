package top.javap.aurora.common;

/**
 * @Author: pch
 * @Date: 2023/5/4 14:10
 * @Description:
 */
public interface SingletonCache<K, V> {

    V get(K key);
}