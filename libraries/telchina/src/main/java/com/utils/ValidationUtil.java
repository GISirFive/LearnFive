package com.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验
 * @author GISirFive
 *
 */
public class ValidationUtil {
	
	/** 
     * 手机号验证 
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isMobile(String str) {
    	if(str == null || str.trim().length() == 0)
    		return false;
    	
        Pattern p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    /**
     * 验证数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
    	if(str == null || str.trim().length() == 0)
    		return false;
    	
    	Pattern p = Pattern.compile("^[0-9]*$");
    	Matcher m = p.matcher(str);
    	
    	return m.matches();
    }
    
    /**
     * 验证字母
     * @param str
     * @return
     */
    public static boolean isABC(String str){
    	if(str == null || str.trim().length() == 0)
    		return false;
    	
    	Pattern p = Pattern.compile("^[A-Za-z]+$");
    	Matcher m = p.matcher(str);
    	
    	return m.matches();
    }
    
    /**
     * 验证数字+字母
     * @param str
     * @return
     */
    public static boolean isABCNumber(String str){
    	if(str == null || str.trim().length() == 0)
    		return false;
    	
    	Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
    	Matcher m = p.matcher(str);
    	
    	return m.matches();
    }
}
