package com.zts.educational.admin.dao.impl;

import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.data.mybatis.annotation.Namespace;
import com.sfsctech.data.mybatis.dao.impl.BaseDaoImpl;
import com.zts.educational.admin.dao.BaseSystemDao;
import com.zts.educational.admin.model.domain.BaseSystemExample;
import com.zts.educational.admin.model.dto.SystemDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Class BaseSystemDaoImpl
 *
 * @author 张麒 2018-3-30.
 * @version Description:
 */
@Repository
@Namespace("com.zts.educational.admin.mapper.BaseSystemMapper")
public class BaseSystemDaoImpl extends BaseDaoImpl<SystemDto, Long, BaseSystemExample> implements BaseSystemDao {

    @Override
    public SystemDto getByCode(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("parent", "0000000000000000000000");
        map.put("status", StatusConstants.Status.Valid.getCode().toString());
        return super.queryForObject("getByCode", map);
    }
}
