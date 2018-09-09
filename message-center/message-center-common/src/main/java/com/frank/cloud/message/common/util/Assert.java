package com.frank.cloud.message.common.util;

import java.util.List;


import com.frank.cloud.message.common.enums.ErrorCode;
import com.frank.cloud.message.common.exception.AppException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class Assert {

    public static void assertNotEmpty(String param, String name) throws AppException
    {
        if (StringUtils.isBlank(param))
        {
            throw new AppException(ErrorCode.INVALID_PARAMETER,name);
        }
    }
    
    public static void assertLongNotEmpty(Long param, String name) throws AppException
    {
    	if(param == null) 
    	{
    		throw new AppException(ErrorCode.INVALID_PARAMETER,name);
    	}
    }
    
    public static void assertIntegerNotEmpty(Integer param, String name) throws AppException
    {
    	if(param == null) 
    	{
    		throw new AppException(ErrorCode.INVALID_PARAMETER,name);
    	}
    }
    
    public static void assertNotArrayEmpty(List<String> param, String name) throws AppException 
    {
    	if (CollectionUtils.isEmpty(param)) {
			throw new AppException(ErrorCode.INVALID_PARAMETER,name);
		}
    }
    
    public static void assertNotNumArrayEmpty(List<Long> param, String name) throws AppException 
    {
    	if (CollectionUtils.isEmpty(param)) {
			throw new AppException(ErrorCode.INVALID_PARAMETER,name);
		}
    }

    public static void assertBooleanNotEmpty(Boolean param, String name) throws AppException
    {
        if (param == null)
        {
            throw new AppException(ErrorCode.INVALID_PARAMETER,name);
        }
    }

    public static void assertNotGt(String param, int number, String name) throws AppException
    {
        if (StringUtils.isNotEmpty(param))
        {
            int var = Integer.valueOf(param);
            if(var > number)
            {
                throw new AppException(ErrorCode.INVALID_PARAMETER, name);
            }
        }
    }

    public static void assertNumber(String param, String name) throws AppException
    {
        try
        {
            if (StringUtils.isEmpty(param) || !isNumeric(param))
            {
                throw new AppException(ErrorCode.INVALID_PARAMETER,name);
            }

            Long.parseLong(param);
        }
        catch (Exception e)
        {
            throw new AppException(ErrorCode.INVALID_PARAMETER,name);
        }

    }

    public static void assertInNumbers(String param, String numbers, String name) throws AppException
    {
        try
        {
            String[]  numArray = StringUtils.split( numbers,",");
            boolean flag = false;
            for(String number:numArray)
            {
                if((number.trim()).equals(param))
                {
                    flag = true;
                    break;
                }
            }

            if(!flag)
            {
                throw new AppException(ErrorCode.INVALID_PARAMETER, name);
            }
        }
        catch (Exception e)
        {
            throw new AppException(ErrorCode.INVALID_PARAMETER, name);
        }
    }

    public static boolean isNumeric(String str) {
        if (str != null)
        {
            if(0 == str.length())
            {
                return false;
            }

            for (int i = 0; i < str.length(); i++) {
                if (!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
