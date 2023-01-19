package com.lhy.leaftakeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhy.leaftakeout.common.R;
import com.lhy.leaftakeout.pojo.Employee;
import com.lhy.leaftakeout.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

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

        if (!password.equals(obj.getPassword())) {
            return R.error("Password is error");
        }

        if (obj.getStatus() == 0) {
            return R.error("User is forbidden");
        }

        httpServletRequest.getSession().setAttribute("employee", obj.getId());
        return R.success(obj);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().removeAttribute("employee");
        return R.success("Logout successful");
    }

    @PostMapping
    public R<String> addEmployee(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateUser(Long.parseLong((String) httpServletRequest.getSession().getAttribute("employee")));
        employee.setUpdateUser(Long.parseLong((String) httpServletRequest.getSession().getAttribute("employee")));

        employeeService.save(employee);
        return R.success("Add user successfully");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page: {}, pageSize: {}, name: {}", page, pageSize, name);
        Page pageInfo = new Page(page, pageSize);

        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.like(StringUtils.isNotBlank(name), Employee::getName, name);

        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, lambdaQueryWrapper);

        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> update(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {
        log.info("********************&&&&&&&&&&" + (String) httpServletRequest.getSession().getAttribute("employee"));
        employee.setUpdateUser(Long.parseLong((String) httpServletRequest.getSession().getAttribute("employee")));
        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return R.success("update successfully");
    }

    @GetMapping("/{id}")
    public R<Employee> getEmployee(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        return R.success(employee);
    }
}
