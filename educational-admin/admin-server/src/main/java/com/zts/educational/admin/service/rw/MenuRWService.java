package com.zts.educational.admin.service.rw;

import com.zts.educational.admin.model.domain.BaseMenu;
import com.zts.educational.admin.model.dto.MenuDto;

import java.util.List;

/**
 * Class MenuRWService
 *
 * @author 张麒 2018-8-17.
 * @version Description:
 */
public interface MenuRWService {

    void save(BaseMenu model);

    void changeStatus(MenuDto model);

    List<BaseMenu> findAll(BaseMenu model);

    BaseMenu getByGuid(String guid);

    List<MenuDto> findBySysCode(String sysCode, String parent);
}
