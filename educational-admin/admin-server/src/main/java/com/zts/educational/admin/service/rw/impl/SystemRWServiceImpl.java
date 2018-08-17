package com.zts.educational.admin.service.rw.impl;

import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.support.common.util.StringUtil;
import com.sfsctech.support.common.util.UUIDUtil;
import com.zts.educational.admin.dao.BaseSystemDao;
import com.zts.educational.admin.mapper.BaseMenuMapper;
import com.zts.educational.admin.mapper.BaseSystemMapper;
import com.zts.educational.admin.model.domain.BaseMenu;
import com.zts.educational.admin.model.domain.BaseMenuExample;
import com.zts.educational.admin.model.domain.BaseSystem;
import com.zts.educational.admin.model.domain.BaseSystemExample;
import com.zts.educational.admin.model.dto.SystemDto;
import com.zts.educational.admin.service.rw.SystemRWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class SystemReadServiceImpl
 *
 * @author 张麒 2017-11-16.
 * @version Description:
 */
@Service
public class SystemRWServiceImpl implements SystemRWService {

    @Autowired
    private BaseSystemMapper mapper;
    
    @Autowired
    private BaseMenuMapper menuMapper;

    @Autowired
    private BaseSystemDao dao;

    @Override
    public List<BaseSystem> findAll(BaseSystem system) {
        BaseSystemExample example = new BaseSystemExample();
        return mapper.selectByExample(example);
    }

    @Override
    public BaseSystem getByGuid(String guid) {
        return mapper.selectByGuid(guid);
    }

    @Override
    public SystemDto getByCode(String code) {
        return dao.getByCode(code);
    }

    @Override
    public void save(BaseSystem model) {
        if (StringUtil.isBlank(model.getGuid())) {
            model.setGuid(UUIDUtil.base58Uuid());
            model.setStatus(StatusConstants.Status.Valid.getCode());
            mapper.insert(model);
        } else {
            BaseSystemExample example = new BaseSystemExample();
            example.createCriteria().andGuidEqualTo(model.getGuid());
            mapper.updateByExampleSelective(model, example);
            // 更新菜单里的系统编号
            BaseMenuExample menuExample = new BaseMenuExample();
            menuExample.createCriteria().andSysguidEqualTo(model.getGuid());
            BaseMenu menu = new BaseMenu();
            menu.setSyscode(model.getCode());
            menuMapper.updateByExampleSelective(menu, menuExample);
        }
    }

    @Override
    public void changeStatus(SystemDto model) {
        BaseSystemExample example = new BaseSystemExample();
        example.createCriteria().andGuidEqualTo(model.getGuid());
        mapper.updateByExampleSelective(model, example);
    }
}
