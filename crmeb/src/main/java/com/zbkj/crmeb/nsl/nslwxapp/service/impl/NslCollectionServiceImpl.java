package com.zbkj.crmeb.nsl.nslwxapp.service.impl;

import com.zbkj.crmeb.nsl.nslwxapp.model.NslCollection;
import com.zbkj.crmeb.nsl.nslwxapp.dao.NslCollectionMapper;
import com.zbkj.crmeb.nsl.nslwxapp.service.NslCollectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-06-03
 */
@Service
public class NslCollectionServiceImpl extends ServiceImpl<NslCollectionMapper, NslCollection> implements NslCollectionService {

    @Override
    public List getCraneCollectByUserId(Integer userId) {
        return baseMapper.getCraneCollectByUserId(userId);
    }
}
