package com.jack.Service.Impl;

import com.jack.Mapper.LoginMapper;
import com.jack.Mapper.UserMapper;
import com.jack.Mapper.WareHouseMapper;
import com.jack.Pojo.LoginUser;
import com.jack.Pojo.Storage;
import com.jack.Pojo.User;
import com.jack.Pojo.dto.UserDto;
import com.jack.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private WareHouseMapper wareHouseMapper;
    @Override
    public int add(UserDto userDto) {
        Storage select = wareHouseMapper.select(userDto.getRcode());
//        System.out.println(select);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //使用encode方法对原密码进行加密,每次加密可以产生不同的结果
        if (select==null){
            return -2;
        }
        else {
            User user = new User();
            user.setUcode(userDto.getUcode());
            user.setUname(userDto.getUname());
            user.setRcode(userDto.getRcode());
            user.setPhone(userDto.getPhone());
//            System.out.println(user);
            int flag1=userMapper.add(user);
            String ucode=user.getUcode();
//            System.out.println(ucode);
            String pwd = passwordEncoder.encode(ucode);
            LoginUser loginUser=new LoginUser(ucode, pwd, "邮箱待添加",1,0);
//            System.out.println(loginUser);
            int flag2=loginMapper.addUser(loginUser);
            if(flag1==0||flag2==0){
                if (flag1!=0){
                    userMapper.deleteByUcode(user.getUcode());
                }
                if (flag2!=0){
                    loginMapper.deleteById(user.getUcode());
                }
                return 0;
            }
            else {
                return 2;
            }
        }
    }

    @Override
    public int deleteByUcode(String Ucode) {
        List<UserDto> userCheck = userMapper.selectUser(Ucode);
        LoginUser loginUserCheck=loginMapper.findUser(Ucode);
        if (userCheck.isEmpty()){
            return -1;
        }
        else {
            int flag1=userMapper.deleteByUcode(Ucode);
            int flag2=loginMapper.deleteById(Ucode);
            if(flag1==0){
                return 0;
            }
            else {
                return 2;
            }
        }
    }

    @Override
    public int updateByUcode(UserDto userDto) {
        List<UserDto> userCheck=userMapper.selectUser(userDto.getUcode());
        Storage storage = wareHouseMapper.select(userDto.getRcode());
        if (userCheck.isEmpty()){
            return -1;
        }
        else if (storage==null){
            return -2;
        }
        else {
            User user = new User(userDto.getUcode(),userDto.getRcode(),userDto.getUname(),userDto.getPhone());
            return userMapper.updateByUcode(user);
        }
    }

    @Override
    public User selectByUcode(String Ucode) {
        return userMapper.selectByUcode(Ucode);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public List<UserDto> selectUser(String ucode) {
        return userMapper.selectUser(ucode);
    }
}
