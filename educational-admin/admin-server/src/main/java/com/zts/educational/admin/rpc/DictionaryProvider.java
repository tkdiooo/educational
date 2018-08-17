package com.zts.educational.admin.rpc;

import com.sfsctech.core.base.constants.RpcConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.support.common.util.BeanUtil;
import com.sfsctech.support.common.util.ListUtil;
import com.sfsctech.support.common.util.ThrowableUtil;
import com.zts.educational.admin.inf.DictionaryService;
import com.zts.educational.admin.model.domain.BaseDictionary;
import com.zts.educational.admin.model.dto.DictionaryDto;
import com.zts.educational.admin.service.rw.DictionaryRWService;
import com.zts.educational.admin.service.transactional.DictionaryTransactionalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Class DictionaryServiceImpl
 *
 * @author 张麒 2017-11-15.
 * @version Description:
 */
@Service
public class DictionaryProvider implements DictionaryService {

    private final Logger logger = LoggerFactory.getLogger(DictionaryProvider.class);


    @Autowired
    private DictionaryRWService service;

    @Autowired
    private DictionaryTransactionalService transactionalService;

    @Override
    public RpcResult<List<DictionaryDto>> findChildByNumber(@RequestBody DictionaryDto dto) {
        RpcResult<List<DictionaryDto>> result = new RpcResult<>();
        try {
            List<BaseDictionary> list = service.findByParent(dto.getNumber());
            if (ListUtil.isEmpty(list)) {
                result.setSuccess(false);
                result.setStatus(RpcConstants.Status.Failure);
                result.setMessage("parent：" + dto.getNumber() + "获取字典集合为空");
                logger.warn(result.toString());
            }
            result.setResult(BeanUtil.copyListForCglib(list, DictionaryDto.class));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.ServerError);
            result.setMessage(ThrowableUtil.getRootMessage(e));
            logger.warn(result.toString());
        }
        return result;
    }

    @Override
    public RpcResult<DictionaryDto> getByNumber(@RequestBody DictionaryDto dto) {
        RpcResult<DictionaryDto> result = new RpcResult<>();
        try {
            BaseDictionary model = service.getByNumber(dto.getNumber());
            if (null == model) {
                result.setSuccess(false);
                result.setStatus(RpcConstants.Status.Failure);
                result.setMessage("number：" + dto.getNumber() + "获取字典对象为空");
                logger.warn(result.toString());
            } else {
                result.setResult(BeanUtil.copyBeanForCglib(model, DictionaryDto.class));
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.ServerError);
            result.setMessage(ThrowableUtil.getRootMessage(e));
            logger.warn(result.toString());
        }
        return result;
    }

    @Override
    public RpcResult<DictionaryDto> save(@RequestBody DictionaryDto dictionary) {
        RpcResult<DictionaryDto> result = new RpcResult<>();
        try {
            service.save(dictionary);
            result.setResult(dictionary);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.ServerError);
            result.setMessage(ThrowableUtil.getRootMessage(e));
            logger.warn(result.toString());
        }
        return result;
    }

    @Override
    public void changeStatus(@RequestBody DictionaryDto dictionary) {
        service.changeStatus(dictionary);
    }

    @Override
    public void sort(@RequestBody DictionaryDto dto) {
        transactionalService.sort(dto.getSortable());
    }

    @Override
    public RpcResult numberIsExist(@RequestBody DictionaryDto dictionary) {
        RpcResult result = new RpcResult();
        try {
            result.setSuccess(service.numberIsExist(dictionary));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.ServerError);
            result.setMessage(ThrowableUtil.getRootMessage(e));
            logger.warn(result.toString());
        }
        return result;
    }

}
