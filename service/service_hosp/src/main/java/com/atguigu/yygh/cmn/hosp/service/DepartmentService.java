package com.atguigu.yygh.cmn.hosp.service;

import com.atguigu.yygh.cmn.model.hosp.Department;
import com.atguigu.yygh.cmn.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.cmn.vo.hosp.DepartmentVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    void save(Map<String, Object> paramMap);

    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

    void remove(String hoscode, String depcode);

    List<DepartmentVo> findDeptTree(String hoscode);

    String getDepName(String hoscode, String depcode);

    Department getDepartment(String hoscode, String depcode);
}
