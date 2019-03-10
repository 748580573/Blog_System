package com.heng.blog_system.utils;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达
 */
public class RegexUtils {

    /**
     *
     * @param str
     * @param regex
     * @return
     */
    public static boolean match(String str,String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static boolean match(String str, Collection<String> regexs){
        for (String regex : regexs){
            if (Pattern.compile(regex).matcher(str).find()){
                return true;
            }
        }
        return false;
    }



    interface RegexCallBack{

        boolean deal(String str,Iterable iterable);
    }
}
