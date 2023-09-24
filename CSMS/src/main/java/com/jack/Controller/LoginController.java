package com.jack.Controller;

import com.alibaba.fastjson.JSONObject;
import com.jack.Pojo.InOrder;
import com.jack.Pojo.OutOrder;
import com.jack.Pojo.Storage;
import com.jack.Pojo.User;
import com.jack.Pojo.dto.OrderNum;
import com.jack.Pojo.dto.StorageRest;
import com.jack.Pojo.dto.UserDto;
import com.jack.Service.InAndOutService;
import com.jack.Service.LoginService;
import com.jack.Service.UserService;
import com.jack.Service.WareHouseService;
import com.jack.utils.Code;
import com.jack.utils.JWTUtils;
import com.jack.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private WareHouseService wareHouseService;
    @Autowired
    private InAndOutService inAndOutService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestBody Map<String,String> map) {
        int login = loginService.login(map);
        if (login == 2) {
            return new Result(Code.LOGIN_ERR, "用户名或密码错误！");
        } else {
            Map<String, String> payload = new HashMap<>();
            //用户登录成功后的信息放入payload
            payload.put("id", map.get("username"));
            payload.put("idtype", String.valueOf(login));
            //生成JWT令牌
            String token = JWTUtils.getToken(payload);
            User user = userService.selectByUcode(map.get("username"));
            Map<String, Object> InfoMap = new HashMap<String, Object>();
            InfoMap.put("token", token);
            InfoMap.put("username", user.getUname());
            InfoMap.put("type", String.valueOf(login));
            InfoMap.put("ucode",user.getUcode());
            if (login==1){
                InfoMap.put("rcode",user.getRcode());
            }
            return new Result(Code.LOGIN_OK, InfoMap);
        }
    }

    @GetMapping("/getBar")
    @ResponseBody
    public Result GetBar() {
        List<Storage> storages = wareHouseService.selectWh(null);
        List<StorageRest> list = new ArrayList<>();
        for (Storage s : storages) {
            int sum = 0;
            sum = s.getSnum() + s.getMnum() + s.getBnum();
            StorageRest rest = new StorageRest(s.getWhname(), sum);
            list.add(rest);
        }
        return new Result(Code.GET_OK, list);
    }

    @GetMapping("/getInLine")
    @ResponseBody
    public Result GetInLine(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<InOrder> inOrders = inAndOutService.selectInOrder(null);
        Map<String,Integer> inOrderNum=new HashMap<>();
        for (InOrder inOrder : inOrders) {
            String date = format.format(inOrder.getItime());
            inOrderNum.merge(date, inOrder.getNum(), Integer::sum);
        }
        List<OrderNum>list=new ArrayList<>();
        for (Map.Entry<String, Integer> entry : inOrderNum.entrySet()){
            OrderNum orderNum = new OrderNum(entry.getKey(),entry.getValue());
            list.add(orderNum);
        }
        return new Result(Code.GET_OK,list);
    }

    @GetMapping("/getOutLine")
    @ResponseBody
    public Result GetOutLine(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<OutOrder> OutOrders = inAndOutService.selectOutOrder(null);
        Map<String,Integer> outOrderNum=new HashMap<>();
        for (OutOrder outOrder : OutOrders) {
            String date = format.format(outOrder.getOtime());
            outOrderNum.merge(date, outOrder.getNum(), Integer::sum);
        }
        List<OrderNum>list=new ArrayList<>();
        for (Map.Entry<String, Integer> entry : outOrderNum.entrySet()){
            OrderNum orderNum = new OrderNum(entry.getKey(),entry.getValue());
            list.add(orderNum);
        }
        return new Result(Code.GET_OK,list);
    }
}
