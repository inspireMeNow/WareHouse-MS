package com.jack.Controller;

import com.jack.Pojo.*;
import com.jack.Pojo.dto.ShelveNum;
import com.jack.Pojo.dto.ShelvePos;
import com.jack.Service.InAndOutService;
import com.jack.Service.PartService;
import com.jack.Service.UserService;
import com.jack.Service.WareHouseService;
import com.jack.utils.Code;
import com.jack.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
public class InAndOutController {
    @Autowired
    private InAndOutService inAndOutService;
    @Autowired
    private UserService userService;
    @Autowired
    private WareHouseService wareHouseService;
    @Autowired
    private PartService partService;

    @GetMapping("/getInAndOut")
    @ResponseBody
    public Result getInAndOut(@RequestParam Map<String,String> map){
        String ucode = map.get("ucode");
//        System.out.println(ucode);
        User user = userService.selectByUcode(ucode);
        Storage storage = wareHouseService.select(user.getRcode());
//        Storage s2=new Storage("ck0001","一号仓","北京市海淀区",10,20,30);
        ShelveNum BNum = new ShelveNum("大型", storage.getBnum());
        ShelveNum MNum = new ShelveNum("中型", storage.getMnum());
        ShelveNum SNum = new ShelveNum("小型", storage.getSnum());
        ArrayList<ShelveNum> list = new ArrayList<>();
        list.add(BNum);
        list.add(MNum);
        list.add(SNum);
        return new Result(Code.GET_OK,list);
    }
    @PostMapping("/in")
    @ResponseBody
    public Result inO(@RequestBody Order order){
//        System.out.println(order);
        String icode=(new Date().getTime()/1000)+order.getUcode();
        String ucode=order.getUcode();
        InOrder in=new InOrder(icode,order.getPcode(),order.getNum(),new Timestamp(System.currentTimeMillis()),order.getRcode(),order.getStype(),ucode);
//        System.out.println(in);
        List<ShelvePos> list = inAndOutService.InStorage(in);
//        int i=-1;
        //-1无此配件，-2无此仓库，-3容量不足，0数据库错误，其他入库成功
        if (list.get(0).getScode().equals("-1")){
            return new Result(Code.SAVE_ERR,0,"无此配件");
        }
        else if (list.get(0).getScode().equals("-3")){
            return new Result(Code.SAVE_ERR,0,"仓库容量不足");
        }
        else {
            return new Result(Code.SAVE_OK,list,"入库成功");
        }
    }
    @PostMapping("/out")
    @ResponseBody
    public Result outO(@RequestBody Order order){
//        System.out.println(order);
        String ocode=(new Date().getTime()/1000)+order.getUcode();
        String ucode=order.getUcode();
        OutOrder on=new OutOrder(ocode,order.getPcode(),order.getRcode(),order.getNum(),new Timestamp(System.currentTimeMillis()),ucode);
//        System.out.println(on);
        List<ShelvePos> list = inAndOutService.OutStorage(on);
//        int i=-3;
        //-3库存容量不足，0数据库错误，其他出库成功
//        System.out.println(list);
        if (list.get(0).getScode().equals("-3")){
            return new Result(Code.SAVE_ERR,0,"库存余量不足");
        }
        else {
            return new Result(Code.SAVE_OK,list,"出库成功");
        }
    }
}
