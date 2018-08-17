package com.zts.educational.admin.dao;

import com.sfsctech.data.mybatis.dao.IBaseDao;
import com.zts.educational.admin.model.domain.BaseSystemExample;
import com.zts.educational.admin.model.dto.SystemDto;

/**
 * Class BaseSystemDao
 *
 * @author 张麒 2018-3-30.
 * @version Description:
 */
public interface BaseSystemDao extends IBaseDao<SystemDto, Long, BaseSystemExample> {

    SystemDto getByCode(String code);
}
