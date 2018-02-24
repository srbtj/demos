package com.example.demo.mapper;

import com.example.demo.model.Resources;
import com.example.demo.model.ResourcesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ResourcesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Fri Feb 23 14:04:56 CST 2018
     */
    int countByExample(ResourcesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Fri Feb 23 14:04:56 CST 2018
     */
    int deleteByExample(ResourcesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Fri Feb 23 14:04:56 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Fri Feb 23 14:04:56 CST 2018
     */
    int insert(Resources record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Fri Feb 23 14:04:56 CST 2018
     */
    int insertSelective(Resources record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Fri Feb 23 14:04:56 CST 2018
     */
    List<Resources> selectByExample(ResourcesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Fri Feb 23 14:04:56 CST 2018
     */
    Resources selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Fri Feb 23 14:04:56 CST 2018
     */
    int updateByExampleSelective(@Param("record") Resources record, @Param("example") ResourcesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Fri Feb 23 14:04:56 CST 2018
     */
    int updateByExample(@Param("record") Resources record, @Param("example") ResourcesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Fri Feb 23 14:04:56 CST 2018
     */
    int updateByPrimaryKeySelective(Resources record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Fri Feb 23 14:04:56 CST 2018
     */
    int updateByPrimaryKey(Resources record);
}