package com.zts.educational.admin.service.rw;


import com.zts.educational.admin.model.domain.BaseSystem;
import com.zts.educational.admin.model.dto.SystemDto;

import java.util.List;

/**
 * Class SystemWriteService
 *
 * @author 张麒 2017/10/20.
 * @version Description:
 */
public interface SystemRWService {

    void save(BaseSystem entity);

    void changeStatus(SystemDto model);

    List<BaseSystem> findAll(BaseSystem system);

    BaseSystem getByGuid(String guid);

    SystemDto getByCode(String code);
}
