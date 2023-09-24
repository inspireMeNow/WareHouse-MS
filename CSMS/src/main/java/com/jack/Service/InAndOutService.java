package com.jack.Service;

import com.jack.Pojo.InOrder;
import com.jack.Pojo.OutOrder;
import com.jack.Pojo.Part;
import com.jack.Pojo.Part_Pos;
import com.jack.Pojo.dto.ShelvePos;
import com.jack.Pojo.dto.StockInfoDto;

import java.util.List;

public interface InAndOutService {
    /**
     * 处理入库请求
     * @param inOrder 业务层传来的订单信息
     * @return 返回处理信息，-1无此配件，-2无此仓库，-3容量不足，0数据库错误，其他入库成功
     */
    public List<ShelvePos> InStorage(InOrder inOrder);

    /**
     * 处理出库请求
     * @param outOrder 业务层传来的订单信息
     * @return 返回处理信息，-3库存容量不足，0数据库错误，其他出库成功
     */
    public List<ShelvePos> OutStorage(OutOrder outOrder);

    /**
     * 修改库存信息
     * @param partPos 库存信息
     * @return 0数据库错误，其他成功
     */
    public int update(Part_Pos partPos);

    /**
     * 删除库存信息
     * @param partPos 库存信息
     * @return 0数据库错误，其他成功
     */
    public int delete(Part_Pos partPos);

    /**
     * 删除入库订单
     * @param id 订单编号
     * @return 0失败，其他成功
     */
    public int deleteInOrder(String id);

    /**
     * 删除库出库订单
     * @param id 订单编号
     * @return 0失败，其他成功
     */
    public int deleteOutOrder(String id);

    public List<Part> select(String rcode);

    //v2方法

    /**
     * 查询库存信息，不传参为查询所有库存信息
     * @param pcode 配件号
     * @return 库存信息
     */
    List<StockInfoDto> selectStock(String pcode);

    /**
     * 查询所有入库订单
     * @param query 订单号
     * @return 入库订单集合
     */
    List<InOrder> selectInOrder(String query);

    /**
     * 查询所用出库订单
     * @param query 订单号
     * @return 出库订单集合
     */
    List<OutOrder> selectOutOrder(String query);
}
