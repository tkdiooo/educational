package com.zts.educational.admin.rpc;

import com.sfsctech.core.base.constants.CacheConstants;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.core.exception.ex.BizException;
import com.sfsctech.support.common.util.BeanUtil;
import com.sfsctech.support.common.util.ListUtil;
import com.zts.educational.admin.common.constants.CommonConstants;
import com.zts.educational.admin.inf.SystemService;
import com.zts.educational.admin.model.domain.BaseSystem;
import com.zts.educational.admin.model.dto.SystemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class SystemConsumer
 *
 * @author 张麒 2018-6-29.
 * @version Description:
 */
@Service
public class SystemConsumer {

    @Autowired
    private SystemService systemService;

    @Autowired
    private CacheFactory<RedisProxy<String, Object>> factory;

    public SystemDto getByGuid(String guid) {
        SystemDto dto = new SystemDto();
        dto.setGuid(guid);
        RpcResult<SystemDto> result = systemService.getByGuid(dto);
        return result.getResult();
    }

    public List<SystemDto> findAll(BaseSystem model) {
        RpcResult<List<SystemDto>> result = systemService.findAll(BeanUtil.copyBeanForCglib(model, SystemDto.class));
        return result.getResult();
    }

    public SystemDto save(BaseSystem model) {
        RpcResult<SystemDto> result = systemService.save(BeanUtil.copyBeanForCglib(model, SystemDto.class));
        return result.getResult();
    }

    public void changeStatus(String guid, StatusConstants.Status status) {
        SystemDto dto = new SystemDto();
        dto.setGuid(guid);
        dto.setStatus(status.getCode());
        systemService.changeStatus(dto);
    }

    /**
     * 获取系统菜单
     *
     * @return
     */
    public SystemDto findSystemMenu() {
        String cache_key = CommonConstants.SYSTEM_CODE + LabelConstants.DOUBLE_POUND;
        SystemDto system = factory.get(cache_key);
        if (null == system) {
            system = new SystemDto();
            system.setCode(CommonConstants.SYSTEM_CODE);
            RpcResult<SystemDto> result = systemService.getByCode(system);
            if (result.isSuccess()) {
                system = result.getResult();
                factory.getCacheClient().putTimeOut(cache_key, system, CacheConstants.MilliSecond.Minutes30.getContent());
            } else {
                BizException e = new BizException(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
//                e.setViewName(CommonConstants.VIEW_404);
                throw e;
            }
        }
        return system;
    }

}
