package com.jarvis.cache_example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jarvis.cache.CacheUtil;

public class SpringElTest {

    public static void main(String[] args) {
        Object objs[]=new Object[]{"a", "b", "456789012345678901234567890"};
        String el="$hash(#args)+'_0.'+$hash(#args[0])+'2'+$hash(#args[1])+'end'";
        Pattern pattern_hash=Pattern.compile("(\\+?)\\$hash\\((.[^)]*)\\)");
        Matcher m=pattern_hash.matcher(el);
        StringBuffer sb=new StringBuffer();
        while(m.find()) {
            m.appendReplacement(sb, "$1T(com.jarvis.cache.CacheUtil).getUniqueHashStr($2)");
        }
        m.appendTail(sb);
        System.out.println(sb.toString());
        String res=CacheUtil.getElValue(sb.toString(), objs, String.class);
        System.out.println(res);
    }

}
