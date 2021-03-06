package com.example.demo.mapper;

import com.example.demo.model.RoleResourcesExample;
import com.example.demo.model.RoleResourcesKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleResourcesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_resources
     *
     * @mbggenerated Fri Feb 23 14:04:56 CST 2018
     */
    int countByExample(RoleResourcesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_resources
     *
     * @mbggenerated Fri Feb 23 14:04:56 CST 2018
     */
    int deleteByExample(RoleResourcesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_resources
     *
     * @mbggenerated Fri Feb 23 14:04:56 CST 2018
     */
    int deleteByPrimaryKey(RoleResourcesKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_resources
     *
     * @mbggenerated Fri Feb 23 14:04:56 CST 2018
     */
    int insert(RoleResourcesKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_resources
     *
     * @mbggenerated Fri Feb 23 14:04:56 CST 2018
     */
    int insertSelective(RoleResourcesKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_resources
     *
     * @mbggenerated Fri Feb 23 14:04:56 CST 2018
     */
    List<RoleResourcesKey> selectByExample(RoleResourcesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_resources
     *
     * @mbggenerated Fri Feb 23 14:04:56 CST 2018
     */
    int updateByExampleSelective(@Param("record") RoleResourcesKey record, @Param("example") RoleResourcesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_resources
     *
     * @mbggenerated Fri Feb 23 14:04:56 CST 2018
     */
    int updateByExample(@Param("record") RoleResourcesKey record, @Param("example") RoleResourcesExample example);
}