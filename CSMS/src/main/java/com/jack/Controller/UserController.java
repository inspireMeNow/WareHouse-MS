package com.jack.Controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jack.Pojo.dto.PermitDto;
import com.jack.Pojo.dto.UserDto;
import com.jack.Service.LoginService;
import com.jack.Service.UserService;
import com.jack.utils.Code;
import com.jack.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;

    @GetMapping("/getUser")
    @ResponseBody
    public Result getUser(@RequestParam String query,
                          @RequestParam(defaultValue = "1",value = "pageIndex") Integer pageIndex,
                          @RequestParam(defaultValue = "5",value = "pageSize") Integer pageSize,
                          @RequestParam String CurrentId){
        PageHelper.startPage(pageIndex,pageSize);
        List<UserDto> userList = userService.selectUser(query);
        PageInfo<UserDto> pageInfo = new PageInfo<>(userList);
        return new Result(Code.GET_OK,pageInfo);
    }
    @PostMapping("/addUser")
    @ResponseBody
    public Result addUser(@RequestBody UserDto userDto){
//        System.out.println(userDto);
        int i = userService.add(userDto);
        if (i==2){
            return new Result(Code.SAVE_OK,0,"添加成功");
        }
        else if(i==-2){
            return new Result(Code.SAVE_ERR,0,"该仓库不存在");
        }
        else if(i==-1){
            return new Result(Code.SAVE_ERR,0,"该用户已存在");
        }
        else {
            return new Result(Code.SAVE_ERR,0,"系统繁忙，请稍后再试");
        }

    }

    @PostMapping("/editUser")
    @ResponseBody
    public Result editUser(@RequestBody UserDto userDto){
//        System.out.println(userDto);
        int i=userService.updateByUcode(userDto);
//        int i = 2;
        if (i==-1){
            return new Result(Code.UPDATE_ERR,0,"该用户不存在");
        }
        else if(i==-2){
            return new Result(Code.UPDATE_ERR,0,"所修改的仓库不存在");
        }
        else if(i==0){
            return new Result(Code.UPDATE_ERR,0,"系统繁忙，请稍后再试");
        }
        else {
            return new Result(Code.UPDATE_OK,0,"修改成功");
        }

    }

    @GetMapping("/deleteUser")
    @ResponseBody
    public Result delUser(@RequestParam("ucode") String ucode){
//        System.out.println(ucode);
        int i=userService.deleteByUcode(ucode);
//        System.out.println(i);
        if (i==2){
            return new Result(Code.DELETE_OK,0,"删除成功");
        }
        else if (i==-1){
            return new Result(Code.DELETE_ERR,0,"该用户不存在");
        }
        else {
            return new Result(Code.DELETE_ERR,0,"系统繁忙，请稍后再试");
        }

    }

    @GetMapping("/getPermit")
    @ResponseBody
    public Result getPermit(@RequestParam String query,
                            @RequestParam(defaultValue = "1",value = "pageIndex") Integer pageIndex,
                            @RequestParam(defaultValue = "5",value = "pageSize") Integer pageSize,
                            @RequestParam String CurrentId){
        PageHelper.startPage(pageIndex,pageSize);
        List<PermitDto> permitDtos = loginService.selectPermit(query);
        PageInfo<PermitDto> pageInfo = new PageInfo<>(permitDtos);
        return new Result(Code.GET_OK,pageInfo);
    }


    @PostMapping("/editPermit")
    @ResponseBody
    public Result editPermit(@RequestBody PermitDto permitDto){
//        System.out.println(permitDto);
        int i= loginService.updateById(permitDto);
//        int i=0;
        if (i==-1){
            return new Result(Code.UPDATE_ERR,0,"该用户不存在");
        }
        else if(i==0){
            return new Result(Code.UPDATE_ERR,0,"系统繁忙，请稍后重试");
        }
        else{
            return new Result(Code.UPDATE_OK,0,"修改成功");
        }
    }

    @GetMapping("/changeState")
    @ResponseBody
    public Result changeState(@RequestParam String id,@RequestParam int state){
        PermitDto permitDto = new PermitDto();
        permitDto.setId(id);
        permitDto.setState((state>0?0:1));
//        System.out.println(permitDto);
        loginService.updateById(permitDto);
        return new Result(Code.UPDATE_OK,0,"切换成功");
    }
}
