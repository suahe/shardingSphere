package com.sah.shardingSphere.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sah.shardingSphere.aspect.annotation.AutoLog;
import com.sah.shardingSphere.common.CommonResponse;
import com.sah.shardingSphere.entity.SysUser;
import com.sah.shardingSphere.security.LoginService;
import com.sah.shardingSphere.security.NotAuthentication;
import com.sah.shardingSphere.security.model.LoginDTO;
import com.sah.shardingSphere.security.model.LoginVO;
import com.sah.shardingSphere.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author suahe
 * @date 2023/3/27
 * @ApiNote
 */
@Api(value = "用户接口", tags = "用户接口")
@RequestMapping("/user")
@RestController
public class UserController extends BaseController<SysUser, ISysUserService> {

    @Autowired
    private LoginService loginService;

    @GetMapping("/selectByPage")
    @NotAuthentication
    @ApiOperation(value = "用户接口-分页查询用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "Integer", paramType = "query"),
    })
    public CommonResponse selectByPage(@Valid SysUser sysUser,
                                       @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        IPage<SysUser> page = baseService.selectByPage(sysUser, pageNo, pageSize);
        return CommonResponse.ok(page);
    }

    @AutoLog("根据账号获取用户")
    @GetMapping("/getUser/{username}")
    @NotAuthentication
    @ApiOperation(value = "用户接口-获取用户信息")
    @ApiImplicitParam(paramType = "path", name = "username", value = "账号", required = true, dataType = "String")
    public CommonResponse getUser(@PathVariable("username") String username) {
        SysUser sysUser = baseService.findByUsername(username);
        return CommonResponse.ok(sysUser);
    }

    @PutMapping
    @NotAuthentication
    @ApiOperation(value = "用户接口-编辑用户信息")
    public CommonResponse editUser(@RequestBody SysUser sysUser) {
        baseService.editUser(sysUser);
        return CommonResponse.ok();
    }

    @PreAuthorize("@ssc.hasPermission('sah:user:query')")
    @PostMapping("/helloWord")
    @ApiOperation(value = "用户接口-测试权限")
    public String hellWord() {
        return "hello word";
    }

    @PostMapping("/login")
    @NotAuthentication
    @ApiOperation("用户接口-登录")
    public CommonResponse<LoginVO> login(@RequestBody LoginDTO dto) {
        return CommonResponse.ok(loginService.login(dto));
    }


    @PostMapping("/refresh")
    @NotAuthentication
    @ApiOperation("用户接口-刷新refreshToken")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "refreshToken", value = "refreshToken", required = true, dataType = "String", paramType = "query"),
    })
    public CommonResponse<LoginVO> refreshToken(@RequestHeader(name = "accessToken") String accessToken,
                                                @RequestParam("refreshToken") String refreshToken) {
        return CommonResponse.ok(loginService.refreshToken(accessToken, refreshToken));
    }

    @PostMapping(value = "/importExcel")
    @NotAuthentication
    @ApiOperation(value = "用户接口-导入用户数据Excel")
    public CommonResponse<?> importExcel(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try (InputStream inputStream = file.getInputStream()) {
                List<SysUser> userList = ExcelImportUtil.importExcel(inputStream, SysUser.class, params);
                return CommonResponse.ok();
            } catch (Exception e) {
                return CommonResponse.error("File import failure:" + e.getMessage());
            }
        }
        return CommonResponse.error("File import failure");
    }

    @GetMapping(value = "/exportXls")
    @NotAuthentication
    @ApiOperation(value = "用户接口-导出用户数据Excel")
    public ModelAndView exportXls(HttpServletRequest request) {
        List<SysUser> userList = baseService.list();
        return super.exportEntityXls("用户数据", SysUser.class, userList);
    }

    public static void main(String[] args) {
        String encode = new BCryptPasswordEncoder().encode("123456");
        System.out.println(encode);
    }
}
