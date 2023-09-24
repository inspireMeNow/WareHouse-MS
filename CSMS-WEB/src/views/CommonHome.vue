<template>
  <div>
    <el-card>
      <el-form :inline="true">
        <el-form-item>
          <el-input placeholder="产品号" v-model="query" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="none" @click="getDatalist()">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="openInDialog()">入库</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="success" @click="openOutDialog()">出库</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="shData" border>
        <el-table-column prop="shtype" label="货架类型">
          <template slot-scope="scope">
            <el-tag type="success" disable-transitions>{{
              scope.row.shtype
            }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="num" label="货架余量">
          <template slot-scope="scope">
            <el-tag type="primary" disable-transitions>{{
              scope.row.num
            }}</el-tag>
          </template>
        </el-table-column>
      </el-table>
      <!-- 产品表格 -->
      <el-table ref="filterTable" :data="tableData" style="width: 100%">
        <el-table-column
          type="index"
          label="序号"
          :index="Nindex"
          align="center"
          width="80"
        ></el-table-column>
        <el-table-column prop="pcode" label="产品号" align="center">
        </el-table-column>
        <el-table-column prop="pname" label="产品名" align="center">
        </el-table-column>
        <el-table-column
          prop="ptype"
          label="产品类型"
          align="center"
          :filters="prodfilter"
          :filter-method="filterTag"
          filter-placement="bottom-end"
        >
          <template slot-scope="scope">
            {{ scope.row.ptype }}
          </template>
        </el-table-column>

        <el-table-column
          prop="manufacture"
          label="厂商"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="protime"
          label="生产日期"
          align="center"
          :formatter="dateFormat"
        ></el-table-column>
        <el-table-column
          prop="warrantytime"
          label="保修期"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="size"
          label="规格"
          align="center"
        ></el-table-column>
        <el-table-column prop="info" label="详细信息" align="center">
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination
        @size-change="sizeChangeHandle"
        @current-change="currentChangeHandle"
        :current-page="pageIndex"
        :page-sizes="[7, 10, 20, 50, 100]"
        :page-size="pageSize"
        :total="totaNum"
        layout="total, sizes, prev, pager, next, jumper"
        style="margin-top: 30px"
      >
      </el-pagination>
      <!-- 入库窗口 -->
      <el-dialog
        title="产品入库"
        width="35%"
        :visible.sync="InFormVisible"
        @close="closeInDialog()"
      >
        <el-form :model="InData">
          <el-form-item label="产品编号" label-width="120px">
            <el-input
              v-model="InData.pcode"
              placeholder="产品为4位"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="入库数量" label-width="120px">
            <el-input
              v-model="InData.num"
              type="number"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="货架类型" label-width="120px">
            <el-select v-model="InData.stype" placeholder="点击选择">
              <el-option label="小型" value="小型"></el-option>
              <el-option label="中型" value="中型"></el-option>
              <el-option label="大型" value="大型"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="closeInDialog()">取 消</el-button>
          <el-button type="primary" @click="SubmitInFormData()"
            >确 定</el-button
          >
        </div>
      </el-dialog>
      <!-- 出库窗口 -->
      <el-dialog
        title="产品出库"
        width="35%"
        :visible.sync="OutFormVisible"
        @close="closeOutDialog()"
      >
        <el-form :model="OutData">
          <el-form-item label="产品编号" label-width="120px">
            <el-input
              v-model="OutData.pcode"
              placeholder="产品为4位"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="出库数量" label-width="120px">
            <el-input
              v-model="OutData.num"
              type="number"
              style="width: 250px"
            ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="closeOutDialog()">取 消</el-button>
          <el-button type="primary" @click="SubmitOutFormData()"
            >确 定</el-button
          >
        </div>
      </el-dialog>
      <!-- 货架信息显示窗口 -->
      <el-dialog
        :title="flag ? '请去以下货架取货' : '请将货物放入以下货架'"
        width="35%"
        :visible.sync="dialogVisible"
        @close="dialogVisible = false"
      >
        <el-table :data="shPos">
          <el-table-column prop="scode" label="编号"></el-table-column>
          <el-table-column prop="stype" label="类型"></el-table-column>
        </el-table>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogVisible = false"
            >确 定</el-button
          >
        </span>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import moment from "moment";
export default {
  name: "CommonHome",
  data() {
    return {
      flag: false,
      dialogVisible: false,
      shData: [],
      shPos: [],
      tableData: [],
      // 入库窗口是否可见
      InFormVisible: false,
      //入库数据模型
      InData: {
        pcode: "",
        num: 0,
        rcode: "",
        stype: "",
        ucode: "",
      },
      // 出库窗口是否可见
      OutFormVisible: false,
      //出库数据模型
      OutData: {
        pcode: "",
        num: 0,
        rcode: "",
        stype: "",
        ucode: "",
      },
      prodfilter: [],
      query: "",
      pageIndex: 1,
      pageSize: 7,
      totalPage: 0,
      totaNum: 0,
      //防止多次提交表单
      dialogFormSubmitVisible: false,
      //首次获取配件信息
      whflag: true,
    };
  },
  methods: {
    filterTag(value, row) {
      return row.ptype === value;
    },
    //每页多少条
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDatalist();
    },
    //当前页
    currentChangeHandle(val) {
      // console.log(val);
      this.pageIndex = val;
      this.getDatalist();
    },
    //设置每页的起始序号
    Nindex(index) {
      // 当前页数 - 1 * 每页数据条数 + 1
      const page = this.pageIndex; // 当前页码
      const pagesize = this.pageSize; // 每页条数
      return index + 1 + (page - 1) * pagesize;
    },
    //打开入库窗口
    openInDialog() {
      // 打开窗口
      this.InFormVisible = true;
    },
    //关闭入库窗口
    closeInDialog() {
      (this.InData.pcode = ""),
        (this.InData.stype = ""),
        (this.InData.num = 0),
        //关闭窗口
        (this.InFormVisible = false);
    },
    //打开出库窗口
    openOutDialog() {
      // console.log(row)
      //将信息写入到窗口中
      //打开窗口
      this.OutFormVisible = true;
    },
    //关闭出库窗口
    closeOutDialog() {
      (this.OutData.pcode = ""),
        (this.OutData.num = 0),
        (this.OutFormVisible = false);
    },
    // 获取配件数据
    getDatalist() {
      // 声明params 查询的参数
      const params = {
        params: {
          pageIndex: this.pageIndex,
          pageSize: this.pageSize,
          query: this.query,
          ucode: sessionStorage.getItem("ucode"),
        },
      };
      //获取数据
      this.$http
        .get("/getPart", params)
        .then((res) => {
          // console.log(res);
          this.tableData = res.data.data.list;
          this.totalPage = res.data.data.pages;
          this.totaNum = res.data.data.total;
          // console.log(this.tableData);
        })
        .finally(() => {
          if (this.whflag) {
            // console.log("1")
            this.getFilterList();
            this.whflag = false;
          }
        });
      this.$http.get("/getInAndOut", params).then((res) => {
        this.shData = res.data.data;
        // console.log(this.shData);
      });
    },

    //获取过滤器信息
    getFilterList() {
      var dic = new Array();
      // console.log(this.tableData);
      this.tableData.forEach((item) => {
        if (!this.prodfilter.find((i) => i.value === item.ptype)) {
          dic["text"] = item.ptype;
          dic["value"] = item.ptype;
          this.prodfilter.push(dic);
          dic = [];
        }
      });
      // console.log(this.prodfilter);
      // console.log(this.whAddAndEdit);
    },
    //提交入库表单数据
    SubmitInFormData() {
      if (this.dialogFormSubmitVisible) {
        return;
      }
      this.dialogFormSubmitVisible = true;
      // console.log("flag1")
      this.$http
        .post("/in", this.InData)
        .then((res) => {
          if (res.data.code == 1001) {
            this.$message({
              message: "入库成功",
              type: "success",
            });
            this.shPos = res.data.data;
            // console.log(res.data.data);
            this.dialogVisible = true;
            this.flag = false;
          } else {
            this.$message.error("入库失败");
          }
        })
        .finally(() => {
          // 关闭窗口
          // this.dialogFormVisible = false;
          this.dialogFormSubmitVisible = false;
          this.closeInDialog();
          // 刷新数据
          this.getDatalist();
        });
    },
    //提交出库表单数据
    SubmitOutFormData() {
      if (this.dialogFormSubmitVisible) {
        return;
      }
      this.dialogFormSubmitVisible = true;
      // console.log("flag1")
      this.$http
        .post("/out", this.OutData)
        .then((res) => {
          if (res.data.code == 1001) {
            this.$message({
              message: "出库成功",
              type: "success",
            });
            this.shPos = res.data.data;
            // console.log(res.data.data)
            this.dialogVisible = true;
            this.flag = true;
          } else {
            this.$message.error("出库失败");
          }
        })
        .finally(() => {
          // 关闭窗口
          this.dialogFormSubmitVisible = false;
          this.closeOutDialog();
          // 刷新数据
          this.getDatalist();
        });
    },
    //时间格式化
    dateFormat: function (row, column) {
      var date = row[column.property];
      if (date == undefined) {
        return "";
      }
      return moment(date).format("YYYY-MM-DD");
    },
  },
  mounted() {
    this.getDatalist();
    this.InData.ucode = sessionStorage.getItem("ucode");
    this.OutData.ucode = sessionStorage.getItem("ucode");
    this.InData.rcode = sessionStorage.getItem("rcode");
    this.OutData.rcode = sessionStorage.getItem("rcode");
  },
};
</script>

<style>
</style>