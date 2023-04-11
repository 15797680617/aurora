package top.javap.aurora.example;

import top.javap.aurora.Aurora;
import top.javap.aurora.example.api.GaoDeMapper;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/4
 **/
public class SimpleExample {
    public static void main(String[] args) {
        GaoDeMapper mapper = Aurora.getInstance(GaoDeMapper.class);
        System.err.println(mapper.abcd("dawdwad", 123));
    }
}