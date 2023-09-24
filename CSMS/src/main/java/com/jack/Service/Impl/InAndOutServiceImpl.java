package com.jack.Service.Impl;

import com.jack.Mapper.InAndOutMapper;
import com.jack.Mapper.PartMapper;
import com.jack.Mapper.WareHouseMapper;
import com.jack.Pojo.*;
import com.jack.Pojo.dto.ShelvePos;
import com.jack.Pojo.dto.StockInfoDto;
import com.jack.Service.InAndOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InAndOutServiceImpl implements InAndOutService {
    @Autowired
    private InAndOutMapper inAndOutMapper;
    @Autowired
    private PartMapper partMapper;
    @Autowired
    private WareHouseMapper wareHouseMapper;

    @Override
    public List<ShelvePos> InStorage(InOrder inOrder) {
        List<ShelvePos> shlist=new ArrayList<>();
        String pcode=inOrder.getPcode();
        String rcode=inOrder.getRcode();
        int num=inOrder.getNum();
        String stype=inOrder.getStype();
        Part part=partMapper.select(pcode);
        Storage storage=wareHouseMapper.select(rcode);
        if(part==null){
            shlist.add(new ShelvePos("-1",null));
            return shlist;
        }
        else if(storage==null){
            shlist.add(new ShelvePos("-2",null));
            return shlist;
        }
        int total=0,size=0;
        List<Shelves> shelves =inAndOutMapper.selectByType(stype);
//        System.out.println(shelves);
        size=shelves.get(0).getSnum();
        if (stype.equals("小型")){
            total=storage.getSnum();
        }
        else if (stype.equals("中型")){
            total=storage.getMnum();
        }
        else {
            total=storage.getBnum();
        }
//        System.out.println(total*size);
        if (num>total*size){
            shlist.add(new ShelvePos("-3",null));
            return shlist;
        }
        else {
            List<Part_Pos> storinfo=inAndOutMapper.selectByStyRco(stype,rcode);
            System.out.println(storinfo);
            List<String> scodelist=new ArrayList<String>();
            for (int i=0,len_i=shelves.size();i<len_i;i++){
                boolean flag=false;
                for(int j=0,len_j=storinfo.size();j<len_j;j++){
                    if (shelves.get(i).getScode().equals(storinfo.get(j).getScode())){
                        flag=true;
                        break;
                    }
                }
                if (!flag){
                    scodelist.add(shelves.get(i).getScode());
                }
            }
//            System.out.println(scodelist);
            //向上取整
            int need= (int) ((num+9)/size);
//            System.out.println("need"+need);
            for (int i=0;i<need-1;i++){
                Part_Pos temp=new Part_Pos(pcode,rcode,scodelist.get(i),size,stype);
                int flag1=inAndOutMapper.add(temp);
                shlist.add(new ShelvePos(temp.getScode(),temp.getStype()));
//                System.out.println("flag1"+flag1);
            }
            int left=num-(need-1)*size;
//            System.out.println("left"+left);
            Part_Pos temp=new Part_Pos(pcode,rcode,scodelist.get(need-1),left,stype);
            int flag2=inAndOutMapper.add(temp);
            shlist.add(new ShelvePos(temp.getScode(),temp.getStype()));
//            System.out.println("flag2"+flag2);
            if (stype.equals("小型")){
                storage.setSnum(storage.getSnum()-need);
            }
            else if (stype.equals("中型")){
                storage.setMnum(storage.getMnum()-need);
            }
            else {
                storage.setBnum(storage.getBnum()-need);
            }
            wareHouseMapper.updateStorage(storage,storage.getWhcode());
            inAndOutMapper.InStorage(inOrder);
            return shlist;
        }
    }

    @Override
    public List<ShelvePos> OutStorage(OutOrder outOrder) {
        ArrayList<ShelvePos> shList = new ArrayList<>();
        String pcode=outOrder.getPcode();
        String rcode=outOrder.getRcode();
        int num=outOrder.getNum();
        Part part=partMapper.select(pcode);
        Storage storage=wareHouseMapper.select(rcode);
        if(part==null){
            shList.add(new ShelvePos("-1",null));
            return shList;
        }
        else if(storage==null){
            shList.add(new ShelvePos("-2",null));
            return shList;
        }
        else {
            List<Part_Pos>remainOut=inAndOutMapper.selectByPcoRco(pcode,rcode);
            //没货
            if (remainOut.size()==0){
                shList.add(new ShelvePos("-3",null));
                return shList;
            }
            int sum=0,length=0;
            int snum=0,mnum=0,bnum=0;
            for (int i=0,len=remainOut.size();i<len;i++){
              sum+=remainOut.get(i).getNum();
              if (remainOut.get(i).getStype().equals("小型")){
                  snum++;
              }
              else if (remainOut.get(i).getStype().equals("中型")){
                  mnum++;
              }
              else {
                  bnum++;
              }
              if (sum>=num){
                  length=i+1;
                  break;
              }
            }
//            System.out.println(snum+" "+mnum+" "+bnum+" "+length);
            //余量不足
            if (sum<num){
                shList.add(new ShelvePos("-3",null));
                return shList;
            }
            else {
                for (int i=0;i<length;i++){
                    if(i==length-1){
                        if (sum>num){
                            int left=sum-num;
                            Part_Pos tem=new Part_Pos(pcode,rcode,remainOut.get(length-1).getScode(),left,remainOut.get(length-1).getStype());
                            Part_Pos pre=remainOut.get(length-1);
                            inAndOutMapper.update(tem,pre);
                            //存储出库货架信息
                            shList.add(new ShelvePos(tem.getScode(),tem.getStype()));
                            if (remainOut.get(i).getStype().equals("小型")){
                                snum--;
                            }
                            else if (remainOut.get(i).getStype().equals("中型")){
                                mnum--;
                            }
                            else {
                                bnum--;
                            }
                            storage.setSnum(storage.getSnum()+snum);
                            storage.setMnum(storage.getMnum()+mnum);
                            storage.setBnum(storage.getBnum()+bnum);
                            wareHouseMapper.updateStorage(storage,storage.getWhcode());
                        }
                        else {
                            int flag1=inAndOutMapper.sub(remainOut.get(length-1));
                            Part_Pos partPos=remainOut.get(length-1);
                            shList.add(new ShelvePos(partPos.getScode(),partPos.getStype()));
//                            System.out.println("flag1="+flag1);
                            storage.setSnum(storage.getSnum()+snum);
                            storage.setMnum(storage.getMnum()+mnum);
                            storage.setBnum(storage.getBnum()+bnum);
                            int flag2=wareHouseMapper.updateStorage(storage,storage.getWhcode());
//                            System.out.println("flag2="+flag2);
                        }
                    }
                    else {
                        inAndOutMapper.sub(remainOut.get(i));
                        Part_Pos partPos=remainOut.get(i);
                        shList.add(new ShelvePos(partPos.getScode(),partPos.getStype()));
                    }
                }
                inAndOutMapper.OutStorage(outOrder);
                return shList;
            }
        }
    }


    @Override
    public int update(Part_Pos partPos) {
        Part_Pos after=partPos;
        Part_Pos pre=partPos;
        return inAndOutMapper.update(after, pre);
    }

    @Override
    public int delete(Part_Pos partPos) {
        return inAndOutMapper.sub(partPos);
    }

    @Override
    public int deleteInOrder(String id) {
        return inAndOutMapper.deleteInOrder(id);
    }

    @Override
    public int deleteOutOrder(String id) {
        return inAndOutMapper.deleteOutOrder(id);
    }

    @Override
    public List<Part> select(String rcode) {

        List<Part_Pos> partPos = inAndOutMapper.selectByRco(rcode);
        List<Part> parts=new ArrayList<Part>();
        for (Part_Pos p:partPos){
            boolean flag=false;
            Part part = partMapper.select(p.getPcode());
            for (Part p1:parts){
                if (p.getPcode().equals(p1.getPcode())){
                    flag=true;
                    break;
                }
            }
            if (flag){
                continue;
            }
            parts.add(part);
        }
        return parts;

    }

    @Override
    public List<StockInfoDto> selectStock(String pcode) {
        return inAndOutMapper.selectStock(pcode);
    }

    @Override
    public List<InOrder> selectInOrder(String query) {
        return inAndOutMapper.selectInOrder(query);
    }

    @Override
    public List<OutOrder> selectOutOrder(String query) {
        return inAndOutMapper.selectOutOrder(query);
    }
}
