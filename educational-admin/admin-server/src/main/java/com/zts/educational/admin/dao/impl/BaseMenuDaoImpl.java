package com.zts.educational.admin.dao.impl;

import com.sfsctech.data.mybatis.annotation.Namespace;
import com.sfsctech.data.mybatis.dao.impl.BaseDaoImpl;
import com.zts.educational.admin.dao.BaseMenuDao;
import com.zts.educational.admin.model.domain.BaseMenuExample;
import com.zts.educational.admin.model.dto.MenuDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class BaseMenuDaoImpl
 *
 * @author 张麒 2017-11-20.
 * @version Description:
 */
@Repository
@Namespace("com.zts.educational.admin.mapper.BaseMenuMapper")
public class BaseMenuDaoImpl extends BaseDaoImpl<MenuDto, Long, BaseMenuExample> implements BaseMenuDao {

    @Override
    public List<MenuDto> findBySysCode(String sysCode, String parent) {
        Map<String, String> map = new HashMap<>();
        map.put("syscode", sysCode);
        map.put("parent", parent);
        return super.queryForList("findBySystem", map);
    }
}
