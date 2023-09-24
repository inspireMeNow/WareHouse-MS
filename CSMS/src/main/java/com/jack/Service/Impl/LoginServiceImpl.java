package com.jack.Service.Impl;

import com.jack.Mapper.LoginMapper;
import com.jack.Mapper.UserMapper;
import com.jack.Pojo.LoginUser;
import com.jack.Pojo.dto.PermitDto;
import com.jack.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public int login(Map map) {
        if(map!=null) {
            String username=(String) map.get("username");
            String password=(String) map.get("password");
//            System.out.println(username);
            LoginUser user=loginMapper.findUser(username);

//            System.out.println(user);
            //无此用户
            if(user==null) {
                return 2;
            }
            else {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                //使用encode方法对原密码进行加密,每次加密可以产生不同的结果
                if (passwordEncoder.matches(password,user.getPasswd())){
                    //身份为管理员
                    if(user.getIdtype()==0 && user.getState()==1){
                        return 0;
                    }
                    //身份为普通用户
                    else if(user.getIdtype()==1 && user.getState()==1){
                        return 1;
                    }
                    //登录权限被禁止
                    else {
                        return 2;
                    }
                }
                //密码错误
                else {
                    return 2;
                }
            }
        }
        else {
            return 2;
        }
    }


    @Override
    public int updateById(PermitDto permitDto) {
        LoginUser userCheck=loginMapper.findUser(permitDto.getId());
        if(userCheck==null){
            return -1;
        }
        else {
            return loginMapper.updateById(permitDto);
        }
    }

    @Override
    public List<PermitDto> selectPermit(String ucode) {
        return loginMapper.selectPermit(ucode);
    }

}
