package com.sah.shardingshere.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sah.shardingshere.entity.Bill;
import com.sah.shardingshere.mapper.BillMapper;
import com.sah.shardingshere.service.IBillService;
import org.springframework.stereotype.Service;

/**
 * @author suahe
 * @date 2022/9/20
 * @ApiNote
 */
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements IBillService {

}
