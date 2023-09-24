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
        title="添加产品"
        width="35%"
        :visible.sync="dialogFormVisible"
        @close="closeDialog()"
      >
        <el-form :model="newData">
          <el-form-item label="产品名称" label-width="120px">
            <el-input
              v-model="newData.pname"
              placeholder="产品名称"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="产品类型" label-width="120px">
            <el-input
              v-model="newData.ptype"
              placeholder="产品类型"
              style="width: 250px"
            ></el-input>
          </el-form-item>

          <el-form-item label="厂商" label-width="120px">
            <el-input
              v-model="newData.manufacture"
              placeholder="厂商名称"
              style="width: 250px"
            ></el-input>
          </el-form-item>

          <el-form-item label="生产日期" label-width="120px">
            <el-date-picker
              v-model="newData.protime"
              type="date"
              placeholder="选择日期"
              format="yyyy 年 MM 月 dd 日"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="保修期" label-width="120px">
            <el-input
              type="number"
              v-model="newData.warrantytime"
              placeholder="保修期"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="规格" label-width="120px">
            <el-input
              type="number"
              v-model="newData.size"
              placeholder="规格"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="详细信息" label-width="120px">
            <el-input
              type="textarea"
              v-model="newData.info"
              placeholder="没有不填"
              style="width: 250px"
            ></el-input>
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
        title="编辑产品"
        width="35%"
        :visible.sync="editDialogFormVisible"
        @close="closeEditDialog()"
      >
        <el-form :model="editDataDialogForm">
          <el-form-item label="产品编号" label-width="120px">
            <el-input
              v-model="editDataDialogForm.pcode"
              disabled
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="产品名称" label-width="120px">
            <el-input
              v-model="editDataDialogForm.pname"
              placeholder="产品名称"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="产品类型" label-width="120px">
            <el-input
              v-model="editDataDialogForm.ptype"
              placeholder="产品类型"
              style="width: 250px"
            ></el-input>
          </el-form-item>

          <el-form-item label="厂商" label-width="120px">
            <el-input
              v-model="editDataDialogForm.manufacture"
              placeholder="厂商名称"
              style="width: 250px"
            ></el-input>
          </el-form-item>

          <el-form-item label="生产日期" label-width="120px">
            <el-date-picker
              v-model="editDataDialogForm.protime"
              type="date"
              placeholder="选择日期"
              format="yyyy 年 MM 月 dd 日"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="保修期" label-width="120px">
            <el-input
              type="number"
              v-model="editDataDialogForm.warrantytime"
              placeholder="保修期"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="规格" label-width="120px">
            <el-input
              type="number"
              v-model="editDataDialogForm.size"
              placeholder="规格"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="详细信息" label-width="120px">
            <el-input
              type="textarea"
              v-model="editDataDialogForm.info"
              placeholder="没有不填"
              style="width: 250px"
            ></el-input>
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
import moment from "moment";
export default {
  name: "GoodinfoManager",
  data() {
    return {
      tableData: [],
      // 新增窗口是否可见
      dialogFormVisible: false,
      //新增数据模型
      newData: {
        pcode: "0",
        pname: "",
        ptype: "",
        manufacture: "",
        protime: "",
        warrantytime: "",
        size: "",
        info: "",
      },
      // 编辑窗口是否可见
      editDialogFormVisible: false,
      //修改数据模型
      editDataDialogForm: {
        pcode: "",
        pname: "",
        ptype: "",
        manufacture: "",
        protime: "",
        warrantytime: "",
        size: "",
        info: "",
      },
      prodfilter: [],
      query: "",
      pageIndex: 1,
      pageSize: 7,
      totalPage: 0,
      totaNum: 0,
      CurrentId: "",
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
    //打开新增窗口
    openDialog() {
      // 打开窗口
      this.dialogFormVisible = true;
    },
    //关闭新建窗口
    closeDialog() {
      this.newData.pcode = "";
      this.newData.pname = "";
      this.newData.ptype = "";
      this.newData.manufacture = "";
      this.newData.protime = "";
      this.newData.warrantytime = "";
      this.newData.size = "";
      this.newData.info = "";
      //关闭窗口
      this.dialogFormVisible = false;
    },
    //打开编辑窗口
    handleEdit(row) {
      // console.log(row)
      //将信息写入到窗口中
      this.editDataDialogForm.pcode = row.pcode;
      this.editDataDialogForm.pname = row.pname;
      this.editDataDialogForm.ptype = row.ptype;
      this.editDataDialogForm.manufacture = row.manufacture;
      this.editDataDialogForm.protime = row.protime;
      this.editDataDialogForm.warrantytime = row.warrantytime;
      this.editDataDialogForm.size = row.size;
      this.editDataDialogForm.info = row.info;
      //打开窗口
      this.editDialogFormVisible = true;
    },
    //关闭编辑窗口
    closeEditDialog() {
      this.editDataDialogForm.pcode = "";
      this.editDataDialogForm.pname = "";
      this.editDataDialogForm.ptype = "";
      this.editDataDialogForm.manufacture = "";
      this.editDataDialogForm.protime = "";
      this.editDataDialogForm.warrantytime = "";
      this.editDataDialogForm.size = "";
      this.editDataDialogForm.info = "";

      this.editDialogFormVisible = false;
    },
    // 获取配件数据
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
    //提交添加数据表单数据
    SubmitAddFormData() {
      if (this.dialogFormSubmitVisible) {
        return;
      }
      this.dialogFormSubmitVisible = true;
      // console.log("flag1")
      this.$http
        .post("/addPart", this.newData)
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
        .post("/editPart", this.editDataDialogForm)
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
              pcode: row.pcode,
            },
          };
          this.$http
            .get("deletePart", params)
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
  },
};
</script>

<style>
</style>