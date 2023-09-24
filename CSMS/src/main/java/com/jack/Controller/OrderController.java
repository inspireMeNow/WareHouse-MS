package com.jack.Controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jack.Pojo.InOrder;
import com.jack.Pojo.OutOrder;
import com.jack.Service.InAndOutService;
import com.jack.utils.Code;
import com.jack.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
public class OrderController {

    @Autowired
    private InAndOutService inAndOutService;

    @GetMapping("/getInOrder")
    @ResponseBody
    public Result findAllin(@RequestParam String query,
                            @RequestParam(defaultValue = "1",value = "pageIndex") Integer pageIndex,
                            @RequestParam(defaultValue = "5",value = "pageSize") Integer pageSIze){
        PageHelper.startPage(pageIndex,pageSIze);
        List<InOrder> orders = inAndOutService.selectInOrder(query);
        PageInfo<InOrder> pageInfo = new PageInfo<>(orders);
        return new Result(Code.GET_OK,pageInfo);
    }


    @GetMapping("/getOutOrder")
    @ResponseBody
    public Result findAllOut(@RequestParam String query,
                             @RequestParam(defaultValue = "1",value = "pageIndex") Integer pageIndex,
                             @RequestParam(defaultValue = "5",value = "pageSize") Integer pageSize){
        PageHelper.startPage(pageIndex,pageSize);
        List<OutOrder> orders = inAndOutService.selectOutOrder(query);
        PageInfo<OutOrder> pageInfo = new PageInfo<>(orders);
        return new Result(Code.GET_OK,pageInfo);
    }
}
