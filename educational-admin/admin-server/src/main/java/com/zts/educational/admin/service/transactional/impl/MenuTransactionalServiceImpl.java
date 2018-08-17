package com.zts.educational.admin.service.transactional.impl;

import com.zts.educational.admin.mapper.BaseMenuMapper;
import com.zts.educational.admin.model.domain.BaseMenu;
import com.zts.educational.admin.service.transactional.MenuTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class MenuTransactionalServiceImpl
 *
 * @author 张麒 2017-11-23.
 * @version Description:
 */
@Service
@Transactional
public class MenuTransactionalServiceImpl implements MenuTransactionalService {

    @Autowired
    private BaseMenuMapper mapper;

    @Override
    public void sort(String sortable) {
        BaseMenu model;
        for (String sort : sortable.split("#")) {
            model = new BaseMenu();
            model.setGuid(sort.split(",")[0]);
            model.setSort(Integer.valueOf(sort.split(",")[1]));
            mapper.updateByPrimaryKeySelective(model);
        }
    }
}
