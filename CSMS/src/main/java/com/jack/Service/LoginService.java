package com.jack.Service;

import com.jack.Pojo.LoginUser;
import com.jack.Pojo.dto.PermitDto;

import java.util.List;
import java.util.Map;

public interface LoginService {
    /**
     * 登录
     * @param map 表现层传来的username和password
     * @return 0管理员，1普通用户，2无此用户
     */
    public int login(Map map);

    /**
     * 修改登录用户的信息
     * @return 返回修改信息，-1该用户不存在，0数据库出错，其他表示修改成功
     */
    public int updateById(PermitDto permitDto);



    //v2方法

    /**
     * 查询所用用户的登录信息
     * @param ucode 工号
     * @return 登录信息集合
     */
    List<PermitDto> selectPermit(String ucode);

}
