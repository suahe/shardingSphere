package com.sah.shardingSphere.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sah.shardingSphere.entity.Bill;
import com.sah.shardingSphere.mapper.BillMapper;
import com.sah.shardingSphere.service.IBillService;
import org.springframework.stereotype.Service;

/**
 * @author suahe
 * @date 2022/9/20
 * @ApiNote
 */
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements IBillService {

}
