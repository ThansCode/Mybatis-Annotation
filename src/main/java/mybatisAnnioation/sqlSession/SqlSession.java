package mybatisAnnioation.sqlSession;

import mybatisAnnioation.handler.UserMapperInvocationHandler;
import mybatisAnnioation.mapper.UserMapper;

import java.lang.reflect.Proxy;

/**
 * @program: XMind
 * @description
 * @author: yihang
 * @create: 2020-07-31 21:44
 **/
public class SqlSession {
    public static <T> T getUserMapper(Class clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz },
                new UserMapperInvocationHandler(clazz));
    }


}
