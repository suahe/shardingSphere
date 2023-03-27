package com.sah.shardingshere;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sah.shardingshere.entity.Bill;
import com.sah.shardingshere.entity.User;
import com.sah.shardingshere.service.IBillService;
import com.sah.shardingshere.service.IUserService;
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
class TestController {

    @Autowired
    private IBillService billService;

    @Autowired
    private IUserService userService;

    @Test
    public void testBillSave(){
        List<Bill> billList = new ArrayList<>();
        for (int i = 0 ; i< 120 ; i++){
            Bill bill = new Bill();
            bill.setUserId(i);
            bill.setAddressId((long)i);
            bill.setStatus("K");
            bill.setCreateTime((new Date(new DateTime(2022,(i % 11)+1,7,00, 00,00,000).getMillis())));
            billList.add(bill);
        }
        billService.saveBatch(billList);
    }

    @Test
    public void testGetBillByOrderId(){
        long id = 779036369388810241L; //根据数据修改，无数据会报错
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", id);
        Bill bill = billService.getOne(queryWrapper);
        System.out.println(bill != null? bill.toString(): "");
    }

    @Test
    public void testGetBillByDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse("2022-02-07 00:00:00");
            QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
            queryWrapper.le("create_time",date);
            List<Bill> billIPage = billService.list(queryWrapper);
            System.out.println(billIPage.size());
            System.out.println(billIPage.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetBillByDate2(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse("2022-02-07 00:00:00");
            Date date2 = sdf.parse("2022-03-07 00:00:00");
            QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
            queryWrapper.ge("create_time",date)
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
    public void testUserSave(){
        List<User> userList = new ArrayList<>();
        for (int i = 0 ; i< 10 ; i++){
            User user = new User();
            user.setUsername("admin");
            user.setStatus("1");
            user.setCreateTime((new Date(new DateTime(2022,(i % 11)+1,7,00, 00,00,000).getMillis())));
            userList.add(user);
        }
        userService.saveBatch(userList);
    }

    @Test
    public void testGetUser() {
        List<User> list = userService.list();
        System.out.println(JSONObject.toJSONString(list));
    }

}


