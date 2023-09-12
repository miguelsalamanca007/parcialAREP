package org.example;

public class ReflectiveService {
    public String classCommand(String className){
        return className;
    }

    public String invokeCommand(String className, String methodName){
        return className + " " + methodName;
    }

    public String unaryInvokeCommand(String className, String methodName, String paramType, String paramValue){
        return className + " " + methodName + " " + paramType + " " + paramValue;
    }

    //binaryInvoke([class name],[method name],[paramtype 1],[param value], [paramtype 1],[param value],)
    public String binaryInvokeCommand(String className, String methodName, String paramType1, String paramValue1, String paramType2, String paramValue2){
        return className + " " + methodName + " " + paramType1 + " " + paramValue1 + " " + paramType2 + " " + paramValue2;
    }

}
