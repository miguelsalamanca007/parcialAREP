package org.example;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectiveService {
    public String classCommand(String className){
        boolean isFirst = true;
        String result = "{" +
                "\"methods\":[";
        try {
            Class c = Class.forName(className);
            Method[] declaredMethods = c.getDeclaredMethods();
            for (Method method: declaredMethods){
                if (isFirst) {
                    result = result +"\""+ method.getName()+"\"";
                    isFirst = false;
                }
                result = result+", "+"\""+method.getName()+"\"";
            }
            result = result + "]}";

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public String invokeCommand(String className, String methodName){
        try {
            Class c = Class.forName(className);
            Method method = c.getMethod(methodName);
            String obj = method.invoke(c).toString();
            return obj;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public String unaryInvokeCommand(String className, String methodName, String paramType, String paramValue){
        return className + " " + methodName + " " + paramType + " " + paramValue;
    }

    //binaryInvoke([class name],[method name],[paramtype 1],[param value], [paramtype 1],[param value],)
    public String binaryInvokeCommand(String className, String methodName, String paramType1, String paramValue1, String paramType2, String paramValue2){
        return className + " " + methodName + " " + paramType1 + " " + paramValue1 + " " + paramType2 + " " + paramValue2;
    }

}
