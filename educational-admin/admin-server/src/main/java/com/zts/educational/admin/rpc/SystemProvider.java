package com.zts.educational.admin.rpc;

import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.constants.RpcConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.core.base.json.FastJson;
import com.sfsctech.support.common.util.BeanUtil;
import com.sfsctech.support.common.util.ListUtil;
import com.zts.educational.admin.inf.SystemService;
import com.zts.educational.admin.model.dto.SystemDto;
import com.zts.educational.admin.service.rw.SystemRWService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Class SystemServiceProvider
 *
 * @author 张麒 2018-3-30.
 * @version Description:
 */
@Service
public class SystemProvider implements SystemService {

    private final Logger logger = LoggerFactory.getLogger(SystemProvider.class);

    @Autowired
    private SystemRWService service;

    @Override
    public RpcResult<SystemDto> getByCode(@RequestBody SystemDto model) {
        return new RpcResult<>(service.getByCode(model.getCode()));
    }

    @Override
    public RpcResult<SystemDto> getByGuid(@RequestBody SystemDto model) {
        return new RpcResult<>(BeanUtil.copyBeanForCglib(service.getByGuid(model.getGuid()), SystemDto.class));
    }

    @Override
    public RpcResult<List<SystemDto>> findAll(@RequestBody SystemDto model) {
        return new RpcResult<>(BeanUtil.copyListForCglib(service.findAll(model), SystemDto.class));
    }

    @Override
    public RpcResult<SystemDto> save(@RequestBody SystemDto model) {
        service.save(model);
        return new RpcResult<>(BeanUtil.copyBeanForCglib(model, SystemDto.class));
    }

    @Override
    public void changeStatus(@RequestBody SystemDto model) {
        service.changeStatus(model);
    }
}
