package com.sah.shardingSphere.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sah.shardingSphere.common.CommonResponse;
import com.sah.shardingSphere.security.model.LoginDTO;
import com.sah.shardingSphere.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.MapExcelConstants;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecgframework.poi.excel.view.JeecgMapExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author suahe
 * @date 2023/3/30
 * @ApiNote
 */
@Slf4j
public class BaseController<T, S extends IService<T>> {

    @Autowired
    protected S baseService;

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    protected CommonResponse<?> importExcel(HttpServletRequest request, HttpServletResponse response, Class<T> clazz) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<T> list = ExcelImportUtil.importExcel(file.getInputStream(), clazz, params);
                //update-begin-author:taoyan date:20190528 for:批量插入数据
                long start = System.currentTimeMillis();
                baseService.saveBatch(list);
                //400条 saveBatch消耗时间1592毫秒  循环插入消耗时间1947毫秒
                //1200条  saveBatch消耗时间3687毫秒 循环插入消耗时间5212毫秒
                log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
                //update-end-author:taoyan date:20190528 for:批量插入数据
                return CommonResponse.ok("文件导入成功！数据行数：" + list.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return CommonResponse.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return CommonResponse.error("文件导入失败！");
    }

    /**
     * 导出实体excel
     */
    protected ModelAndView exportEntityXls(String title, Class<T> clazz, List<T> exportList) {
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        mv.addObject(NormalExcelConstants.CLASS, clazz);
        LoginDTO loginDTO = JwtUtil.getLoginUserByToken();
        String username = loginDTO == null || StringUtils.isEmpty(loginDTO.getUsername()) ? "" : loginDTO.getUsername();
        ExportParams exportParams = new ExportParams(title + "报表", "导出人:" + username, title);
        mv.addObject(NormalExcelConstants.PARAMS, exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }

    /**
     * 导出MapList excel
     */
    protected ModelAndView exportMapListXls(String title, List<ExcelExportEntity> entity, List<Map<String, Object>> mapList) {
        ModelAndView mv = new ModelAndView(new JeecgMapExcelView());
        mv.addObject(MapExcelConstants.FILE_NAME, title);
        mv.addObject(MapExcelConstants.ENTITY_LIST, entity);
        LoginDTO loginDTO = JwtUtil.getLoginUserByToken();
        String username = loginDTO == null || StringUtils.isEmpty(loginDTO.getUsername()) ? "" : loginDTO.getUsername();
        ExportParams exportParams = new ExportParams(title + "报表", "导出人:" + username, title);
        mv.addObject(MapExcelConstants.PARAMS, exportParams);
        mv.addObject(MapExcelConstants.MAP_LIST, mapList);
        return mv;
    }
}
