package com.zts.educational.admin.rpc;

import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.support.common.util.BeanUtil;
import com.zts.educational.admin.inf.DictionaryService;
import com.zts.educational.admin.model.domain.BaseDictionary;
import com.zts.educational.admin.model.dto.DictionaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class DictionaryServiceConsumer
 *
 * @author 张麒 2018-6-1.
 * @version Description:
 */
@Service
public class DictConsumer {

    @Autowired
    private DictionaryService service;

    public List<DictionaryDto> findChildByNumber(String number) {
        DictionaryDto dto = new DictionaryDto();
        dto.setNumber(number);
        RpcResult<List<DictionaryDto>> result = service.findChildByNumber(dto);
        return result.getResult();
    }

    public DictionaryDto getByNumber(String number) {
        DictionaryDto dto = new DictionaryDto();
        dto.setNumber(number);
        RpcResult<DictionaryDto> result = service.getByNumber(dto);
        return result.getResult();
    }

    public DictionaryDto save(BaseDictionary dictionary) {
        RpcResult<DictionaryDto> result = service.save(BeanUtil.copyBeanForCglib(dictionary, DictionaryDto.class));
        return result.getResult();
    }

    public void changeStatus(String number, StatusConstants.Status status) {
        DictionaryDto dto = new DictionaryDto();
        dto.setNumber(number);
        dto.setStatus(status.getCode());
        service.changeStatus(dto);
    }

    public void sort(String sortable) {
        DictionaryDto dto = new DictionaryDto();
        dto.setSortable(sortable);
        service.sort(dto);
    }

    public boolean numberIsExist(BaseDictionary dictionary) {
        RpcResult result = service.numberIsExist(BeanUtil.copyBeanForCglib(dictionary, DictionaryDto.class));
        return result.isSuccess();
    }

}
