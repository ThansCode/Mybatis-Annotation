package mybatisAnnioation.handler;

import mybatisAnnioation.annotation.Insert;
import mybatisAnnioation.annotation.Param;
import mybatisAnnioation.annotation.Select;
import mybatisAnnioation.utils.JDBCUtils;
import mybatisAnnioation.utils.SQLUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: XMind
 * @description
 * @author: yihang
 * @create: 2020-07-31 21:44
 **/
public class UserMapperInvocationHandler implements InvocationHandler {
    private Class userMapperClazz;

    public UserMapperInvocationHandler(Class userMapperClazz) {
        this.userMapperClazz = userMapperClazz;
    }



    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws IllegalAccessException, SQLException, InstantiationException {

        // 获取查询注解信息
        Select query = method.getAnnotation(Select.class);
        if(query != null) {
            return getResult(method,query,args);
        }
        Insert insert = method.getAnnotation(Insert.class);
        if ( insert != null) {
            String insertSql = insert.value();
            //插入参数
            String[] insertParam = SQLUtils.getInsertParams(insertSql);
            //参数绑定
            ConcurrentHashMap<String, Object> paramMap = getMethodParam(method, args);
            System.out.println("paramList"+ Arrays.toString(insertParam));
            // 进行值的封装
            List<Object> paramValueList = new ArrayList<>();
            //将参数值装入list集合
            for (String param : insertParam) {
                Object paramValue = paramMap.get(param.trim());
                paramValueList.add(paramValue);
            }
            insertSql = SQLUtils.replaceParam(insertSql, insertParam);
            //更优化的处理在于再次设定一个类型转换
            return JDBCUtils.insert(insertSql, false, paramValueList);
        }


        return null;
    }


    /**
     * 返回结果集对象信息
     * @param method
     * @param query
     * @param args
     * @return
     */
    private Object getResult(Method method ,Select query, Object [] args) throws IllegalAccessException, SQLException, InstantiationException {
        // 获取sql语句信息
        String sql = query.value();
        // 获取sql参数信息
        List<Object> paramNames = SQLUtils.getSelectParams(sql);
        // 将sql语句进行替换，替换成预编译的sql
        // 形如select id,username from stu where id = ? and username = ?
        sql = SQLUtils.replaceParam(sql,paramNames);
        //获取方法参数，绑定值。
        ConcurrentHashMap<String, Object> paramMap = getMethodParam(method, args);


        List<Object> paramValueList = new ArrayList<>();
        //将参数值装入list集合
        for (Object param : paramNames) {
            Object paramValue = paramMap.get(param);
            paramValueList.add(paramValue);
        }
        // 生成预编译sql为对应位置设定对应值信息
        ResultSet rs = JDBCUtils.query(sql,paramValueList);
        if( rs == null) {
            System.out.println("userMapper null");
        }
        // 处理异常情况
        if (!rs.next()) {
            return null;
        }
        // 获取到方法的返回值类型信息
        Class<?> returnTypeClazz = method.getReturnType();
        // 实例化一个返回值的对象
        Object obj = returnTypeClazz.newInstance();
        // 光标往前移动一位
        rs.previous();
        while (rs.next()) {
            Field[] fields = returnTypeClazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                String fieldValue = rs.getString(fieldName);
                Type type = field.getGenericType();
                field.setAccessible(true);
                // 简单的处理类型映射
                if ( type.toString().equals("class java.lang.Integer")){
                    field.set(obj, Integer.valueOf(fieldValue));
                }else{
                    field.set(obj, fieldValue);
                }

            }
        }
        return obj;
    }

    /***
     * 获取方法上参数值的信息
     * 存在param注解进行了标注，所有此处核心在于解析注解信息
     * 无法获取方法注解！！！！！！！
     * @param method
     * @param args
     * @return
     */
    private ConcurrentHashMap getMethodParam(Method method, Object[] args) {
        ConcurrentHashMap paramMap = new ConcurrentHashMap();
        Parameter[] parameters = method.getParameters();
        // 获取所有注解信息
        Annotation [][] annotations = method.getParameterAnnotations();
        for( int i = 0 ; i < parameters.length ; i ++ ) {
            for ( Annotation annotation : annotations[i]){
                if ( annotation instanceof Param) {
                    String key = ((Param)annotation).value();
                    paramMap.put(key,args[i]);
                }
            }
        }

        return paramMap;
    }

}
