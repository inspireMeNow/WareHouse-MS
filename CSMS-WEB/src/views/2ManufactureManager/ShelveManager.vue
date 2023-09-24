<template>
  <div>
    <el-card>
      <el-form :inline="true">
        <el-form-item>
          <el-input placeholder="货架号" v-model="query" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="none" @click="getDatalist()">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="openDialog()">新增</el-button>
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
        <el-table-column prop="scode" label="货架号" align="center">
        </el-table-column>
        <el-table-column
          prop="stype"
          label="货架类型"
          align="center"
          :filters="StypeFilter"
          :filter-method="filterTag"
          filter-placement="bottom-end"
        >
          <template slot-scope="scope">
            <el-tag
              :type="
                scope.row.stype === '小型'
                  ? 'primary'
                  : scope.row.stype === '中型'
                  ? 'success'
                  : 'warning'
              "
              disable-transitions
              >{{ scope.row.stype }}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column prop="snum" label="货架容量" align="center">
        </el-table-column>
        <el-table-column prop="whcode" label="所属仓库编号" align="center">
        </el-table-column>
        <el-table-column prop="whname" label="所属仓库名" align="center">
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" @click="handleEdit(scope.row)"
              >编辑</el-button
            >
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.row)"
              >删除</el-button
            >
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
      <!-- 添加窗口 -->
      <el-dialog
        title="添加货架"
        width="35%"
        :visible.sync="dialogFormVisible"
        @close="closeDialog()"
      >
        <el-form :model="newData">
          <el-form-item label="货架类型" label-width="120px">
            <el-select v-model="newData.stype" placeholder="点击选择">
              <el-option label="小型" value="小型"></el-option>
              <el-option label="中型" value="中型"></el-option>
              <el-option label="大型" value="大型"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="货架容量" label-width="120px">
            <el-input
              type="number"
              v-model="newData.snum"
              placeholder="货架容量"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="所属仓库编号" label-width="120px">
            <el-select v-model="newData.whcode" placeholder="点击选择">
              <el-option
                v-for="(item, index) in WhDataFilter1"
                :key="index"
                :label="item.whcode"
                :value="item.whcode"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="所属仓库名" label-width="120px">
            <el-input disabled :value="WhData" style="width: 250px"></el-input>
          </el-form-item>
        </el-form>

        <div slot="footer" class="dialog-footer">
          <el-button @click="closeDialog()">取 消</el-button>
          <el-button type="primary" @click="SubmitAddFormData()"
            >确 定</el-button
          >
        </div>
      </el-dialog>
      <!-- 编辑窗口 -->
      <el-dialog
        title="编辑货架"
        width="35%"
        :visible.sync="editDialogFormVisible"
        @close="closeEditDialog()"
      >
        <el-form :model="editDataDialogForm">
          <el-form-item label="货架号" label-width="120px">
            <el-input
              disabled
              v-model="editDataDialogForm.scode"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="货架类型" label-width="120px">
            <el-select
              v-model="editDataDialogForm.stype"
              placeholder="点击选择"
            >
              <el-option label="小型" value="小型"></el-option>
              <el-option label="中型" value="中型"></el-option>
              <el-option label="大型" value="大型"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="货架容量" label-width="120px">
            <el-input
              type="number"
              v-model="editDataDialogForm.snum"
              placeholder="货架容量"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="所属仓库编号" label-width="120px">
            <el-select v-model="editDataDialogForm.whcode" placeholder="点击选择">
              <el-option
                v-for="(item, index) in WhDataFilter1"
                :key="index"
                :label="item.whcode"
                :value="item.whcode"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="closeEditDialog()">取 消</el-button>
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
  namne: "ShelveManager",
  data() {
    return {
      tableData: [],
      // 新增窗口是否可见
      dialogFormVisible: false,
      //新增数据模型
      newData: {
        scode: 0,
        stype: "",
        snum: 0,
        whcode: "",
      },
      // 编辑窗口是否可见
      editDialogFormVisible: false,
      //修改数据模型
      editDataDialogForm: {
        scode: 0,
        stype: "0",
        snum: 0,
        whcode:""
      },
      StypeFilter: [],
      WhDataFilter1: [],
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
    filterTag(value, row) {
      return row.stype === value;
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
    //打开新增窗口
    openDialog() {
      // 打开窗口
      this.dialogFormVisible = true;
    },
    //关闭新建窗口
    closeDialog() {
      this.newData.scode = "";
      this.newData.stype = "";
      this.newData.snum = 0;
      this.newData.whcode = 0;
      //关闭窗口
      this.dialogFormVisible = false;
    },
    //打开编辑窗口
    handleEdit(row) {
      // console.log(row)
      //将信息写入到窗口中
      this.editDataDialogForm.scode = row.scode;
      this.editDataDialogForm.stype = row.stype;
      this.editDataDialogForm.snum = row.snum;
      this.editDataDialogForm.whcode = row.whcode;
      //打开窗口
      this.editDialogFormVisible = true;
    },
    //关闭编辑窗口
    closeEditDialog() {
      this.editDataDialogForm.scode = "";
      this.editDataDialogForm.stype = "0";
      this.editDataDialogForm.snum = 0;
      this.editDataDialogForm.whcode = "";

      this.editDialogFormVisible = false;
    },
    // 获取货架数据
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
        .get("/getSh", params)
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
    //获取过滤器所需信息
    getFilterList() {
      var dic = new Array();
      // console.log(this.tableData);
      this.tableData.forEach((item) => {
        if (!this.StypeFilter.find((i) => i.value === item.stype)) {
          dic["text"] = item.stype;
          dic["value"] = item.stype;
          this.StypeFilter.push(dic);
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
        this.WhDataFilter1 = res.data.data.list;
        // console.log(this.WhDataFilter1)
      });

      // console.log(this.StypeFilter);
    },
    //提交添加数据表单数据
    SubmitAddFormData() {
      if (this.dialogFormSubmitVisible) {
        return;
      }
      this.dialogFormSubmitVisible = true;
      // console.log("flag1")
      this.$http
        .post("/addSh", this.newData)
        .then((res) => {
          if (res.data.code == 1001) {
            this.$message({
              message: "添加成功",
              type: "success",
            });
          } else {
            this.$message.error("添加失败");
          }
        })
        .finally(() => {
          // 关闭窗口
          // this.dialogFormVisible = false;
          this.dialogFormSubmitVisible = false;
          this.closeDialog();
          // 刷新数据
          this.getDatalist();
        });
    },
    //提交修改数据表单
    SubmitEditFormData() {
      if (this.dialogFormSubmitVisible) {
        return;
      }
      this.dialogFormSubmitVisible = true;
      // console.log("flag1")
      this.$http
        .post("/editSh", this.editDataDialogForm)
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
              scode: row.scode,
            },
          };
          this.$http
            .get("deleteSh", params)
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
  computed: {
    WhData() {
      var temp = this.WhDataFilter1.find(
        (i) => i.whcode === this.newData.whcode
      );
      return temp ? temp.whname : "";
    },
  },
};
</script>

<style>
</style>