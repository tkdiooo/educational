package com.zts.educational.admin.service.rw.impl;

import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.support.common.util.StringUtil;
import com.sfsctech.support.common.util.UUIDUtil;
import com.zts.educational.admin.dao.BaseMenuDao;
import com.zts.educational.admin.mapper.BaseMenuMapper;
import com.zts.educational.admin.model.domain.BaseMenu;
import com.zts.educational.admin.model.domain.BaseMenuExample;
import com.zts.educational.admin.model.dto.MenuDto;
import com.zts.educational.admin.service.rw.MenuRWService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class MenuRWServiceImpl
 *
 * @author 张麒 2018-8-17.
 * @version Description:
 */
@Service
public class MenuRWServiceImpl implements MenuRWService {

    @Autowired
    private BaseMenuMapper mapper;

    @Autowired
    private BaseMenuDao dao;

    @Override
    public List<BaseMenu> findAll(BaseMenu model) {
        BaseMenuExample example = new BaseMenuExample();
        BaseMenuExample.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotBlank(model.getParent())) {
            criteria.andParentEqualTo(model.getParent());
        }
        if (StringUtil.isNotBlank(model.getSysguid())) {
            criteria.andSysguidEqualTo(model.getSysguid());
        }
        return mapper.selectByExample(example);
    }

    @Override
    public BaseMenu getByGuid(String guid) {
        return mapper.selectByGuid(guid);
    }

    @Override
    public List<MenuDto> findBySysCode(String sysCode, String parent) {
        return dao.findBySysCode(sysCode, parent);
    }

    @Override
    public void save(BaseMenu model) {
        if (StringUtil.isBlank(model.getGuid())) {
            model.setGuid(UUIDUtil.base58Uuid());
            model.setStatus(StatusConstants.Status.Valid.getCode());
            model.setSort(NumberUtils.INTEGER_ZERO);
            mapper.insert(model);
        } else {
            BaseMenuExample example = new BaseMenuExample();
            example.createCriteria().andGuidEqualTo(model.getGuid());
            mapper.updateByExampleSelective(model, example);
        }
    }

    @Override
    public void changeStatus(MenuDto model) {
        BaseMenuExample example = new BaseMenuExample();
        example.createCriteria().andGuidEqualTo(model.getGuid());
        mapper.updateByExampleSelective(model, example);
    }

}
