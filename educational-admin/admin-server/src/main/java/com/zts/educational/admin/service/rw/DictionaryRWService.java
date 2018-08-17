package com.zts.educational.admin.service.rw;

import com.zts.educational.admin.model.domain.BaseDictionary;

import java.util.List;

/**
 * Class DictionaryService
 *
 * @author 张麒 2018-8-17.
 * @version Description:
 */
public interface DictionaryRWService {

    List<BaseDictionary> findByParent(String parent);

    BaseDictionary getByNumber(String number);

    boolean numberIsExist(BaseDictionary dictionary);

    void save(BaseDictionary dictionary);

    void changeStatus(BaseDictionary dictionary);
}
