package com.sah.shardingSphere;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sah.shardingSphere.entity.Bill;
import com.sah.shardingSphere.entity.SysUser;
import com.sah.shardingSphere.service.IBillService;
import com.sah.shardingSphere.service.ISysUserService;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SpringBootTest
class ShardingSphereController {

    @Autowired
    private IBillService billService;

    @Autowired
    private ISysUserService sysUserService;

    @Test
    public void testBillSave() {
        List<Bill> billList = new ArrayList<>();
        for (int i = 0; i < 120; i++) {
            Bill bill = new Bill();
            bill.setUserId(i);
            bill.setAddressId((long) i);
            bill.setStatus("K");
            bill.setCreateTime((new Date(new DateTime(2022, (i % 11) + 1, 7, 00, 00, 00, 000).getMillis())));
            billList.add(bill);
        }
        billService.saveBatch(billList);
    }

    @Test
    public void testGetBillByOrderId() {
        long id = 779036369388810241L; //根据数据修改，无数据会报错
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", id);
        Bill bill = billService.getOne(queryWrapper);
        System.out.println(bill != null ? bill.toString() : "");
    }

    @Test
    public void testGetBillByDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse("2022-02-07 00:00:00");
            QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
            queryWrapper.le("create_time", date);
            List<Bill> billIPage = billService.list(queryWrapper);
            System.out.println(billIPage.size());
            System.out.println(billIPage.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetBillByDate2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse("2022-02-07 00:00:00");
            Date date2 = sdf.parse("2022-03-07 00:00:00");
            QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
            queryWrapper.ge("create_time", date)
                    .le("create_time", date2);
            List<Bill> billIPage = billService.list(queryWrapper);
            System.out.println(billIPage.size());
            billIPage.forEach(System.out::println);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //测试不分库查询用户表

    @Test
    public void testUserSave() {
        List<SysUser> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SysUser user = new SysUser();
            user.setUsername("admin");
            user.setStatus("1");
            user.setCreateTime((new Date(new DateTime(2022, (i % 11) + 1, 7, 00, 00, 00, 000).getMillis())));
            userList.add(user);
        }
        sysUserService.saveBatch(userList);
    }

    @Test
    public void testGetUser() {
        List<SysUser> list = sysUserService.list();
        System.out.println(JSONObject.toJSONString(list));
    }

}


