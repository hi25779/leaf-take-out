package com.lhy.leaftakeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhy.leaftakeout.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : Jesse(lhy)
 * @mail : 859028027@qq.com
 * @created : 2022-11-18
 **/
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
