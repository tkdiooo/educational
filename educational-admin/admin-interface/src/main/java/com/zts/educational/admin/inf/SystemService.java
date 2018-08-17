package com.zts.educational.admin.inf;

import com.sfsctech.cloud.base.annotation.CloudService;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.zts.educational.admin.model.dto.SystemDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class SystemService
 *
 * @author 张麒 2018-3-30.
 * @version Description:
 */
@RestController
@RequestMapping("system-service")
@CloudService("Admin-Server")
public interface SystemService {

    @RequestMapping("getByCode")
    RpcResult<SystemDto> getByCode(@RequestBody SystemDto model);

    @RequestMapping("getByGuid")
    RpcResult<SystemDto> getByGuid(@RequestBody SystemDto model);

    @RequestMapping("findAll")
    RpcResult<List<SystemDto>> findAll(@RequestBody SystemDto model);

    @RequestMapping("save")
    RpcResult<SystemDto> save(@RequestBody SystemDto model);

    @RequestMapping("changeStatus")
    void changeStatus(@RequestBody SystemDto model);


}
