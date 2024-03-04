package top.hcode.hoj.dao.user.impl;

import top.hcode.hoj.pojo.entity.user.UserPreferences;
import top.hcode.hoj.mapper.UserPreferencesMapper;
import top.hcode.hoj.dao.user.UserPreferencesEntityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class UserPreferencesEntityServiceImpl extends ServiceImpl<UserPreferencesMapper, UserPreferences>
        implements UserPreferencesEntityService {

}
