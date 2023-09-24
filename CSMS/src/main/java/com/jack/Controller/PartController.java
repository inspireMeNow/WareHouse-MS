package com.jack.Controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jack.Pojo.Part;
import com.jack.Pojo.Part_Pos;
import com.jack.Pojo.dto.StockInfoDto;
import com.jack.Service.InAndOutService;
import com.jack.Service.PartService;
import com.jack.utils.Code;
import com.jack.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
public class PartController {

    @Autowired
    private PartService partService;
    @Autowired
    private InAndOutService inAndOutService;


    @RequestMapping("getPart")
    @ResponseBody
    public Result getPart(@RequestParam String query,
                          @RequestParam(defaultValue = "1", value = "pageIndex") Integer pageIndex,
                          @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSIze) {
        PageHelper.startPage(pageIndex, pageSIze);

        List<Part> list = partService.selectPart(query);
        PageInfo<Part> pageInfo = new PageInfo<>(list);
        return new Result(Code.GET_OK, pageInfo);
    }

    @PostMapping("/addPart")
    @ResponseBody
    public Result addPart(@RequestBody Part p) {
//        System.out.println(p);
        int i = partService.add(p);
        if (i == -1) {
            return new Result(Code.SAVE_ERR, 0, "该配件已经存在");
        } else if (i == 0) {
            return new Result(Code.SAVE_ERR, 0, "系统繁忙，请稍后重试");
        } else {
            return new Result(Code.SAVE_OK, 0, "添加成功");
        }
    }

    @PostMapping("/editPart")
    @ResponseBody
    public Result editPart(@RequestBody Part p) {
//        System.out.println(p);
        int i = partService.update(p, p.getPcode());
//        System.out.println(i);
        if (i == -1) {
            return new Result(Code.UPDATE_ERR, 0, "该配件不存在");
        } else if (i == 0) {
            return new Result(Code.UPDATE_ERR, 0, "系统繁忙，请稍后重试");
        } else {
            return new Result(Code.UPDATE_OK, 0, "修改成功");
        }
    }

    @GetMapping("/deletePart")
    @ResponseBody
    public Result deletePart(@RequestParam String pcode) {
//        System.out.println(id);
        int i = partService.delete(pcode);
//        System.out.println(i);
        if (i == -1) {
            return new Result(Code.DELETE_ERR, 0, "该配件不存在");
        } else if (i == -2) {
            return new Result(Code.DELETE_ERR, 0, "仓库中还有此货物，不允许删除");
        } else if (i == 0) {
            return new Result(Code.DELETE_ERR, 0, "系统繁忙，请稍后再试");
        } else {
            return new Result(Code.DELETE_OK, 0, "删除成功");
        }
    }

    @RequestMapping("getStock")
    @ResponseBody
    public Result getStock(@RequestParam String query,
                           @RequestParam(defaultValue = "1", value = "pageIndex") Integer pageIndex,
                           @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSIze) {
        PageHelper.startPage(pageIndex, pageSIze);
        List<StockInfoDto> list = inAndOutService.selectStock(query);
        PageInfo<StockInfoDto> pageInfo = new PageInfo<>(list);
        return new Result(Code.GET_OK, pageInfo);
    }

    @PostMapping("/editStock")
    @ResponseBody
    public Result editStock(@RequestBody Part_Pos s) {
        System.out.println(s);
        int i = inAndOutService.update(s);
        if (i == 0) {
            return new Result(Code.UPDATE_ERR, 0, "系统繁忙，请重试");
        } else {
            return new Result(Code.UPDATE_OK, 0, "修改成功");
        }
    }

    @GetMapping("/deleteStock")
    @ResponseBody
    public Result deleteStock(@RequestParam String pcode,
                              @RequestParam String rcode,
                              @RequestParam String scode) {
        Part_Pos partPos = new Part_Pos(pcode, rcode, scode, 0,"");
        int i = inAndOutService.delete(partPos);
        if (i == 0) {
            return new Result(Code.DELETE_ERR, 0, "系统繁忙，请稍后再试");
        } else {
            return new Result(Code.DELETE_OK, 0, "删除成功");
        }
    }
}
