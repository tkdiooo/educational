package com.zts.educational.admin.service.rw.impl;

import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.constants.StatusConstants;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.support.common.util.Cn2SpellUtil;
import com.sfsctech.support.common.util.StringUtil;
import com.zts.educational.admin.common.constants.CommonConstants;
import com.zts.educational.admin.mapper.BaseDictionaryMapper;
import com.zts.educational.admin.model.domain.BaseDictionary;
import com.zts.educational.admin.model.domain.BaseDictionaryExample;
import com.zts.educational.admin.service.rw.DictionaryRWService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class DictionaryWriteServiceImpl
 *
 * @author 张麒 2017/10/26.
 * @version Description:
 */
@Service
public class DictionaryRWServiceImpl implements DictionaryRWService {

    @Autowired
    private BaseDictionaryMapper mapper;
    @Autowired
    private CacheFactory<RedisProxy<String, Object>> factory;

    @Override
    public void save(BaseDictionary model) {
        model.setPinyin(Cn2SpellUtil.converterToSpell(model.getContent()) + LabelConstants.COMMA + Cn2SpellUtil.converterToFirstSpell(model.getContent()));
        if (!CommonConstants.ROOT_GUID.equals(model.getParent())) {
            model.setNumber(model.getParent() + model.getNumber());
        }
        if (null == model.getId()) {
            model.setStatus(StatusConstants.Status.Valid.getCode());
            model.setSort(NumberUtils.INTEGER_ZERO);
            mapper.insert(model);
        } else {
            BaseDictionaryExample example = new BaseDictionaryExample();
            example.createCriteria().andIdEqualTo(model.getId());
            mapper.updateByExampleSelective(model, example);
        }
    }

    @Override
    public void changeStatus(BaseDictionary dictionary) {
        BaseDictionaryExample example = new BaseDictionaryExample();
        example.createCriteria().andNumberEqualTo(dictionary.getNumber());
        mapper.updateByExampleSelective(dictionary, example);
    }

    @Override
    public List<BaseDictionary> findByParent(String parent) {
        BaseDictionaryExample example = new BaseDictionaryExample();
        BaseDictionaryExample.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotBlank(parent)) {
            criteria.andParentEqualTo(parent);
        }
        example.setOrderByClause("sort asc");
        return mapper.selectByExample(example);
    }

    @Override
    public BaseDictionary getByNumber(String number) {
        return mapper.selectByNumber(number);
    }

    @Override
    public boolean numberIsExist(BaseDictionary dictionary) {
        BaseDictionaryExample example = new BaseDictionaryExample();
        if (null != dictionary.getId()) {
            BaseDictionary dict = mapper.selectByPrimaryKey(dictionary.getId());
            if (dict.getNumber().equals(dictionary.getNumber())) {
                return true;
            }
        }
        example.createCriteria().andNumberEqualTo(dictionary.getParent().equals(CommonConstants.ROOT_GUID) ? dictionary.getNumber() : (dictionary.getParent() + dictionary.getNumber()));
        return mapper.selectByExample(example).size() == 0;
    }
}
