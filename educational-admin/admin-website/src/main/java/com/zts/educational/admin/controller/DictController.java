package com.zts.educational.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.core.security.annotation.Verify;
import com.sfsctech.core.web.constants.UIConstants;
import com.sfsctech.core.web.domain.result.ActionResult;
import com.sfsctech.support.bootstrap.breadcrumb.Breadcrumb;
import com.sfsctech.support.bootstrap.constants.BootstrapConstants;
import com.sfsctech.support.bootstrap.util.BootstrapUtil;
import com.sfsctech.support.common.util.StringUtil;
import com.zts.educational.admin.common.constants.CommonConstants;
import com.zts.educational.admin.model.domain.BaseDictionary;
import com.zts.educational.admin.rpc.DictConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Class IndexController
 *
 * @author 张麒 2017/10/17.
 * @version Description:
 */
@Controller
@RequestMapping("dictionary")
public class DictController {

    @Autowired
    private DictConsumer dictConsumer;

    @Autowired
    private CacheFactory<RedisProxy<String, Object>> factory;

    @GetMapping("index")
    public String grid(ModelMap model, BaseDictionary dictionary) {
        // 父节点parent为空
        if (StringUtil.isBlank(dictionary.getParent())) {
            dictionary.setParent(CommonConstants.ROOT_GUID);
        }
        // 列表面包屑设置
        List<Breadcrumb> list = factory.getList(BaseDictionary.class.getSimpleName() + dictionary.getParent());
        // 缓存为空
        if (list == null) {
            // 根节点为空，设置根节点
            if (CommonConstants.ROOT_GUID.equals(dictionary.getParent())) {
                Breadcrumb breadcrumb = new Breadcrumb(CommonConstants.ROOT_NAME, CommonConstants.ROOT_CLASS);
                breadcrumb.addParams("guid", CommonConstants.ROOT_GUID);
                list = new ArrayList<>();
                list.add(breadcrumb);
            } else {
                BaseDictionary dict = dictConsumer.getByNumber(dictionary.getParent());
                Breadcrumb breadcrumb = new Breadcrumb(dict.getContent(), CommonConstants.ROOT_CLASS);
                breadcrumb.addParams("guid", dict.getNumber());
                list = factory.getList(BaseDictionary.class.getSimpleName() + dict.getParent());
                list.add(breadcrumb);
            }
            factory.getCacheClient().put(BaseDictionary.class.getSimpleName() + dictionary.getParent(), list);
        }
        model.put("parent", dictionary.getParent());
        model.put("data", dictConsumer.findChildByNumber(dictionary.getParent()));
        model.put("options", BootstrapUtil.matchOptions("dictionary_index_options", StatusConstants.Status.Valid, StatusConstants.Status.Disable));
        model.put("breadcrumbs", list);
        model.put("status", BootstrapConstants.StatusColumns.getColumns());
        return "admin/dictionary/index";
    }

    @GetMapping("add")
    public String add(ModelMap model, String parent) {
        model.put(UIConstants.Operation.Added.getCode(), UIConstants.Operation.Added.getDescription());
        // 不是跟节点的情况下，获取父节点编号
        if (!CommonConstants.ROOT_GUID.equals(parent)) {
            model.put("parent_number", parent);
        }
        // 父节点Number
        model.put("parent", parent);
        return "admin/dictionary/edit";
    }

    @GetMapping("edit")
    public String edit(ModelMap model, String number) {
        model.put(UIConstants.Operation.Editor.getCode(), UIConstants.Operation.Editor.getDescription());
        BaseDictionary dictionary = dictConsumer.getByNumber(number);
        // 不是跟节点的情况下，获取父节点编号
        if (!CommonConstants.ROOT_GUID.equals(dictionary.getParent())) {
            model.put("parent_number", dictionary.getParent());
            dictionary.setNumber(dictionary.getNumber().substring(dictionary.getNumber().length() - 4));
        }
        // 父节点Number
        model.put("parent", dictionary.getParent());
        model.put("model", dictionary);
        return "admin/dictionary/edit";
    }

    @ResponseBody
    @PostMapping("save")
    public ActionResult<BaseDictionary> save(@Verify BaseDictionary dictionary) {
        return ActionResult.forSuccess(dictConsumer.save(dictionary));
    }

    @ResponseBody
    @PostMapping("disable")
    public ActionResult<BaseDictionary> disable(String number) {
        dictConsumer.changeStatus(number, StatusConstants.Status.Disable);
        return ActionResult.forSuccess();
    }

    @ResponseBody
    @PostMapping("valid")
    public ActionResult<BaseDictionary> valid(String number) {
        dictConsumer.changeStatus(number, StatusConstants.Status.Valid);
        return ActionResult.forSuccess();
    }

    @GetMapping("ordering")
    public String ordering(ModelMap model, String parent) {
        // 获取所有当前节点数据
        model.put("data", dictConsumer.findChildByNumber(parent));
        return "admin/dictionary/sort";
    }

    @ResponseBody
    @PostMapping("sort")
    public ActionResult<String> sort(String sortable) {
        dictConsumer.sort(sortable);
        return ActionResult.forSuccess();
    }

    @ResponseBody
    @PostMapping("load")
    public ActionResult<BaseDictionary> load(String number) {
        BaseDictionary dictionary = dictConsumer.getByNumber(number);
        // 不是跟节点的情况下，获取父节点编号
        if (!CommonConstants.ROOT_GUID.equals(dictionary.getParent())) {
            dictionary.setNumber(dictionary.getNumber().substring(dictionary.getNumber().length() - 4));
        }
        return ActionResult.forSuccess(dictionary);
    }

    @ResponseBody
    @PostMapping("exist")
    public JSONObject exist(BaseDictionary dictionary) {
        JSONObject json = new JSONObject();
        json.put("valid", dictConsumer.numberIsExist(dictionary));
        return json;
    }
}
