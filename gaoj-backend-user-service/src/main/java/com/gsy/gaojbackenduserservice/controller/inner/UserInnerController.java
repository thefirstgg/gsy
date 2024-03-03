package com.gsy.gaojbackenduserservice.controller.inner;

import com.gsy.gaojbackendserviceclient.service.UserFeignClient;
import com.gsy.gaojbackenduserservice.service.UserService;
import gaojbackendmodel.model.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 该服务只用于内部
 */
@RestController
@RequestMapping("/inner")
public class UserInnerController implements UserFeignClient {

    @Resource
    private UserService userService;
    /**
     * 根据 Id 获得用户
     * @param userId
     * @return
     */
    @Override
    @GetMapping("/get/id")
    public User getById(@RequestParam("userId") long userId){
        return userService.getById(userId);
    }

    /**
     * 根据 Id 获取用户列表
     * @param idList
     * @return
     */
    @Override
    @GetMapping("/get/ids")
    public List<User> listByIds(@RequestParam("idList") Collection<Long> idList){
        return userService.listByIds(idList);
    }
}
