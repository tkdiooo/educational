package com.zts.educational.admin.model.dto;


import com.zts.educational.admin.model.domain.BaseSystem;

import java.util.List;

/**
 * Class SystemDto
 *
 * @author 张麒 2018-3-30.
 * @version Description:
 */
public class SystemDto extends BaseSystem {

    private static final long serialVersionUID = 8565536045395106450L;

    private List<MenuDto> child;

    public List<MenuDto> getChild() {
        return child;
    }

    public void setChild(List<MenuDto> child) {
        this.child = child;
    }
}
