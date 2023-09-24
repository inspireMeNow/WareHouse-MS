package com.jack.Controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jack.Pojo.Shelves;
import com.jack.Pojo.Storage;
import com.jack.Pojo.dto.ShelvesDto;
import com.jack.Service.WareHouseService;
import com.jack.utils.Code;
import com.jack.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
public class WareHouseController {

    @Autowired
    private WareHouseService wareHouseService;
    
    @RequestMapping("/getWh")
    @ResponseBody
    public Result getWh(@RequestParam String query,
                        @RequestParam(defaultValue = "1",value = "pageIndex") Integer pageIndex,
                        @RequestParam(defaultValue = "5",value = "pageSize") Integer pageSize){
        PageHelper.startPage(pageIndex,pageSize);
        List<Storage> list = wareHouseService.selectWh(query);
//        System.out.println(list);
        PageInfo<Storage> pageInfo = new PageInfo<>(list);
        return new Result(Code.GET_OK,pageInfo);
    }
    @PostMapping("/addWh")
    @ResponseBody
    public Result addWh(@RequestBody Storage s){
//        System.out.println(s);
        int i = wareHouseService.addStorage(s);
        if (i==-1){
            return  new Result(Code.SAVE_ERR,0,"该仓库已经存在");
        }
        else if (i==0){
            return new Result(Code.SAVE_ERR,0,"系统繁忙，请稍后再试");
        }
        else {
            return new Result(Code.SAVE_OK,0,"添加成功");
        }
    }

    @PostMapping("/editWh")
    @ResponseBody
    public Result editWh(@RequestBody Storage s){
//        System.out.println(s);
        int i=wareHouseService.updateStorage(s,s.getWhcode());
        if (i==-1){
            return  new Result(Code.UPDATE_ERR,0,"该仓库不存在");
        }
        else if (i==0){
            return new Result(Code.UPDATE_ERR,0,"系统繁忙，请稍后再试");
        }
        else {
            return new Result(Code.UPDATE_OK,0,"修改成功");
        }
    }

    @GetMapping("/deleteWh")
    @ResponseBody
    public Result delWh(@RequestParam("whcode") String whcode){
//        System.out.println(id);
        int i = wareHouseService.deleteStorage(whcode);
//        System.out.println(i);
        if (i==-1){
            return  new Result(Code.DELETE_ERR,0,"该仓库不存在");
        }
        else if (i==-2){
            return new Result(Code.DELETE_ERR,0,"该仓库有货物，不允许删除");
        }
        else if (i==0){
            return new Result(Code.DELETE_ERR,0,"系统繁忙，请稍后再试");
        }
        else {
            return new Result(Code.DELETE_OK,0,"删除成功");
        }
    }

    @RequestMapping("/getSh")
    @ResponseBody
    public Result getSh(@RequestParam String query,
                        @RequestParam(defaultValue = "1",value = "pageIndex") Integer pageIndex,
                        @RequestParam(defaultValue = "5",value = "pageSize") Integer pageSize){
        PageHelper.startPage(pageIndex,pageSize);
        List<ShelvesDto> list = wareHouseService.selectSh(query);
//        System.out.println(list);
        PageInfo<ShelvesDto> pageInfo = new PageInfo<>(list);
        return new Result(Code.GET_OK,pageInfo);
    }
    @PostMapping("/addSh")
    @ResponseBody
    public Result addSh(@RequestBody Shelves s){
//        System.out.println(s);
        int i = wareHouseService.addShelves(s);
        if (i==-1){
            return  new Result(Code.SAVE_ERR,0,"该货架已经存在");
        }
        else if (i==0){
            return new Result(Code.SAVE_ERR,0,"系统繁忙，请稍后再试");
        }
        else {
            return new Result(Code.SAVE_OK,0,"添加成功");
        }
    }

    @PostMapping("/editSh")
    @ResponseBody
    public Result editSh(@RequestBody Shelves s){
//        System.out.println(s);
        int i = wareHouseService.updateShelves(s, s.getScode());
        if (i==-1){
            return  new Result(Code.UPDATE_ERR,0,"该货架不存在");
        }
        else if (i==0){
            return new Result(Code.UPDATE_ERR,0,"系统繁忙，请稍后再试");
        }
        else {
            return new Result(Code.UPDATE_OK,0,"修改成功");
        }
    }

    @GetMapping("/deleteSh")
    @ResponseBody
    public Result delSh(@RequestParam("scode") String scode){
//        System.out.println(id);
        int i = wareHouseService.deleteShelves(scode);
        if (i==-1){
            return  new Result(Code.DELETE_ERR,0,"该货架不存在");
        }
        else if (i==-2){
            return new Result(Code.DELETE_ERR,0,"该货架有货物，不允许删除");
        }
        else if (i==0){
            return new Result(Code.DELETE_ERR,0,"系统繁忙，请稍后再试");
        }
        else {
            return new Result(Code.DELETE_OK,0,"删除成功");
        }
    }
}
