package com.atguigu.yygh.cmn.hosp.controller.api;

import com.atguigu.common.helper.HttpRequestHelper;
import com.atguigu.common.util.MD5;
import com.atguigu.yygh.cmn.common.exception.YyghException;
import com.atguigu.yygh.cmn.common.result.Result;
import com.atguigu.yygh.cmn.common.result.ResultCodeEnum;
import com.atguigu.yygh.cmn.hosp.service.DepartmentService;
import com.atguigu.yygh.cmn.hosp.service.HospitalService;
import com.atguigu.yygh.cmn.hosp.service.HospitalSetService;
import com.atguigu.yygh.cmn.hosp.service.ScheduleService;
import com.atguigu.yygh.cmn.model.hosp.Department;
import com.atguigu.yygh.cmn.model.hosp.Hospital;
import com.atguigu.yygh.cmn.model.hosp.Schedule;
import com.atguigu.yygh.cmn.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.cmn.vo.hosp.ScheduleQueryVo;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private HospitalSetService hospitalSetService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ScheduleService scheduleService;

    //上传医院接口
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        String hospSign = (String) paramMap.get("sign");
        String hospCode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hospCode);
        String signKeyMD5 = MD5.encrypt(signKey);
        if (!hospSign.equals(signKeyMD5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        //传输过程中“+”转换为了“ ”，因此我们要转换回来
        String logoDataString = (String)paramMap.get("logoData");
        String logoData = logoDataString.replaceAll(" ", "+");
        paramMap.put("logoData", logoData);

        hospitalService.save(paramMap);
        return Result.ok();
    }

    //医院查询接口
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取医院的编号
        String hospSign = (String) paramMap.get("sign");
        String hospCode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hospCode);
        String signKeyMD5 = MD5.encrypt(signKey);
        if (!hospSign.equals(signKeyMD5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        //根据编号查询
        Hospital hospital = hospitalService.getByHoscode(hospCode);
        return Result.ok(hospital);
    }

    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        String hospSign = (String) paramMap.get("sign");
        String hospCode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hospCode);
        String signKeyMD5 = MD5.encrypt(signKey);
        if (!hospSign.equals(signKeyMD5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        departmentService.save(paramMap);
        return Result.ok();
    }

    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        String hospCode = (String) paramMap.get("hoscode");
        int page = StringUtils.isEmpty(paramMap.get("hscode"))? 1 : Integer.parseInt((String)paramMap.get("hoscode"));
        int limit = StringUtils.isEmpty(paramMap.get("limit"))? 1 : Integer.parseInt((String)paramMap.get("limit"));
        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hospCode);
        //调用service方法
        Page<Department> pageModel = departmentService.findPageDepartment(page,limit,departmentQueryVo);
        return Result.ok(pageModel);
    }

    //删除科室接口
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request) {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //医院编号 和 科室编号
        String hoscode = (String)paramMap.get("hoscode");
        String depcode = (String)paramMap.get("depcode");
        //TODO 签名校验
        departmentService.remove(hoscode,depcode);
        return Result.ok();
    }

    //删除排班
    @PostMapping("schedule/remove")
    public Result remove(HttpServletRequest request) {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取医院编号和排班编号
        String hoscode = (String)paramMap.get("hoscode");
        String hosScheduleId = (String)paramMap.get("hosScheduleId");

        //TODO 签名校验

        scheduleService.remove(hoscode,hosScheduleId);
        return Result.ok();
    }

    //查询排班接口
    @PostMapping("schedule/list")
    public Result findSchedule(HttpServletRequest request) {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //医院编号
        String hoscode = (String)paramMap.get("hoscode");

        //科室编号
        String depcode = (String)paramMap.get("depcode");
        //当前页 和 每页记录数
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String)paramMap.get("limit"));
        //TODO 签名校验

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);
        //调用service方法
        Page<Schedule> pageModel = scheduleService.findPageSchedule(page,limit,scheduleQueryVo);
        return Result.ok(pageModel);
    }

    //上传排班接口
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request) {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //TODO 签名校验
        scheduleService.save(paramMap);
        return Result.ok();
    }

}

