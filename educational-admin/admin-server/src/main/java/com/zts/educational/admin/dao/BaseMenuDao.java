package com.zts.educational.admin.dao;

import com.sfsctech.data.mybatis.dao.IBaseDao;
import com.zts.educational.admin.model.domain.BaseMenuExample;
import com.zts.educational.admin.model.dto.MenuDto;

import java.util.List;

/**
 * Class BaseMenuDao
 *
 * @author 张麒 2017-11-20.
 * @version Description:
 */
public interface BaseMenuDao extends IBaseDao<MenuDto, Long, BaseMenuExample> {

    List<MenuDto> findBySysCode(String sysCode, String parent);

}
