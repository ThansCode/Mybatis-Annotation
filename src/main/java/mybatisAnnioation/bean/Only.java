package mybatisAnnioation.bean;

import mybatisAnnioation.annotation.Param;
import mybatisAnnioation.annotation.Select;

/**
 * @program: XMind
 * @description
 * @author: yihang
 * @create: 2020-08-01 17:15
 **/
public interface Only {

    @Select("select id,username from stu where id = #{id} and username = #{name}")
    void selectUser(@Param("id") Integer id, @Param("name") String name);

}
