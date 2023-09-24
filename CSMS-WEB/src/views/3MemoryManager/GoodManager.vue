<template>
  <div>
    <el-card>
      <el-form :inline="true">
        <el-form-item>
          <el-input placeholder="产品号" v-model="query" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getDatalist()">查询</el-button>
        </el-form-item>
      </el-form>
      <!-- 表格 -->
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
        <el-table-column prop="rcode" label="仓库号" align="center">
        </el-table-column>
        <el-table-column
          prop="whname"
          label="仓库名"
          align="center"
          :filters="whfilter"
          :filter-method="filterStorage"
          filter-placement="bottom-end"
        >
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.whname === '一号仓' ? 'primary' : 'success'"
              disable-transitions
              >{{ scope.row.whname }}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column prop="scode" label="货架号" align="center">
        </el-table-column>
        <el-table-column
          prop="stype"
          label="货架类型"
          align="center"
          :filters="stypefilter"
          :filter-method="filterSh"
          filter-placement="bottom-end"
        >
          <template slot-scope="scope">
              {{scope.row.stype}}
          </template>
        </el-table-column>
        <el-table-column prop="num" label="数量" align="center">
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" @click="handleEdit(scope.row)"
              >编辑</el-button
            >
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
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
      <!-- 编辑窗口 -->
      <el-dialog
        title="编辑库存信息"
        width="35%"
        :visible.sync="editDialogFormVisible"
        @close="closeEditDialog()"
      >
        <el-form :model="editDataDialogForm">
          <el-form-item label="产品编号" label-width="120px">
            <el-input
              v-model="editDataDialogForm.pcode"
              placeholder="产品编号"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="仓库编号" label-width="120px">
            <el-select placeholder="仓库编号" v-model="editDataDialogForm.rcode">
              <el-option v-for="item in whEditFilter" :key="item.whcode" :label="item.whcode" :value="item.whcode"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="货架号" label-width="120px">
            <el-input
              v-model="editDataDialogForm.scode"
              placeholder="货架号"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="数量" label-width="120px">
            <el-input
              type="number"
              v-model="editDataDialogForm.num"
              placeholder="数量"
              style="width: 250px"
            ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="editDialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="SubmitEditFormData()"
            >确 定</el-button
          >
        </div>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
export default {
name:'GoodManager',
  data() {
    return {
      tableData: [],
      // 编辑窗口是否可见
      editDialogFormVisible: false,
      //修改数据模型
      editDataDialogForm: {
          pcode: "",
          rcode:"",
          scode: "",
          stype:"",
          num:0
      },
      whfilter: [],
      whEditFilter:[],
      stypefilter: [],
      query: "",
      pageIndex: 1,
      pageSize: 7,
      totalPage: 0,
      totaNum: 0,
      CurrentId: "",
      //防止多次提交表单
      dialogFormSubmitVisible: false,
      //首次获取仓库信息
      whflag: true,
    };
  },
  methods: {
    filterSh(value, row) {
      return row.stype === value;
    },
    filterStorage(value, row) {
      return row.whname === value;
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
    //打开编辑窗口
    handleEdit(row) {
      // console.log(row)
      //将信息写入到窗口中
      this.editDataDialogForm.pcode = row.pcode;
      this.editDataDialogForm.pname = row.pname;
      this.editDataDialogForm.rcode = row.rcode;
      this.editDataDialogForm.scode = row.scode;
      this.editDataDialogForm.stype = row.stype;
      this.editDataDialogForm.num = row.num;
      //打开窗口
      this.editDialogFormVisible = true;
    },
    //关闭编辑窗口
    closeEditDialog() {
      this.editDataDialogForm.pcode = "";
      this.editDataDialogForm.pname = "";
      this.editDataDialogForm.whname = "";
      this.editDataDialogForm.scode = "";
      this.editDataDialogForm.stype = "";
      this.editDataDialogForm.num = 0;

      this.editDialogFormVisible = false;
    },
    // 获取库存数据
    getDatalist() {
      // 声明params 查询的参数
      const params = {
        params: {
          pageIndex: this.pageIndex,
          pageSize: this.pageSize,
          CurrentId: this.CurrentId,
          query: this.query,
        },
      };
      //获取数据
      this.$http
        .get("/getStock", params)
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
    },
    //获取过滤器信息
    getFilterList() {
      var dic = new Array();
      // console.log(this.tableData);
      this.tableData.forEach((item) => {
        if (!this.whfilter.find((i) => i.value === item.whname)) {
          dic["text"] = item.whname;
          dic["value"] = item.whname;
          this.whfilter.push(dic);
          dic = [];
        }
      });

      dic=[];
      this.tableData.forEach((item) => {
        if (!this.stypefilter.find((i) => i.value === item.stype)) {
          dic["text"] = item.stype;
          dic["value"] = item.stype;
          this.stypefilter.push(dic);
          dic = [];
        }
      });

      const params = {
        params: {
          pageIndex: 1,
          pageSize: 0,
          CurrentId: this.CurrentId,
          query: "",
        },
      };
      this.$http.get("/getWh", params).then((res) => {
        this.whEditFilter = res.data.data.list;
        // console.log(this.whEditFilter);
      });
      // console.log(this.whEditFilter);
      // console.log(this.stypefilter);
    },
    //提交修改数据表单
    SubmitEditFormData() {
      if (this.dialogFormSubmitVisible) {
        return;
      }
      this.dialogFormSubmitVisible = true;
      // console.log("flag1")
      this.$http
        .post("/editStock", this.editDataDialogForm)
        .then((res) => {
          if (res.data.code == 3001) {
            this.$message({
              message: "修改成功",
              type: "success",
            });
          } else {
            this.$message.error("修改失败");
          }
        })
        .finally(() => {
          // 关闭窗口
          this.dialogFormSubmitVisible = false;
          this.closeEditDialog();
          // 刷新数据
          this.getDatalist();
        });
    },
    //删除数据
    handleDelete(row) {
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          //确认删除
          const params = {
            params: {
              pcode:row.pcode,
              rcode:row.rcode,
              scode:row.scode
            },
          };
          this.$http
            .get("/deleteStock", params)
            .then((res) => {
              if (res.data.code == 2001) {
                this.$message({
                  type: "success",
                  message: "删除成功!",
                });
              } else {
                this.$message.error("删除失败");
              }
            })
            .finally(() => {
              this.getDatalist();
            });
        })
        .catch(() => {
          //取消删除
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
  },
  mounted() {
    this.getDatalist();
  },
}
</script>

<style>

</style>