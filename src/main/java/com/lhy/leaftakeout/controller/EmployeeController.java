package com.lhy.leaftakeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lhy.leaftakeout.common.R;
import com.lhy.leaftakeout.pojo.Employee;
import com.lhy.leaftakeout.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : Jesse(lhy)
 * @mail : 859028027@qq.com
 * @created : 2022-11-17
 **/
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {
        String password = employee.getPassword();
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee obj = employeeService.getOne(queryWrapper);

        if (obj == null) {
            return R.error("User is not existed");
        }

        password = DigestUtils.md5DigestAsHex(password.getBytes());

        if (password.equals(obj.getPassword())) {
            return R.error("Password is error");
        }

        if (obj.getStatus() == 0) {
            return R.error("User is forbidden");
        }

        return R.success(obj);

    }

}
