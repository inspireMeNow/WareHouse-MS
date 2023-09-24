<template>
  <div>
    <el-card>
      <el-form :inline="true">
        <el-form-item>
          <el-input placeholder="工号" clearable v-model="query"></el-input>
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
          :index="Nindex"
          label="序号"
          align="center"
          width="80"
        ></el-table-column>
        <el-table-column prop="ucode" label="工号" align="center">
        </el-table-column>
        <el-table-column prop="uname" label="姓名" align="center">
        </el-table-column>
        <el-table-column prop="phone" label="联系电话" align="center">
        </el-table-column>
        <el-table-column
          prop="whname"
          label="所负责仓库"
          align="center"
          :filters="whfilter"
          :filter-method="filterTag"
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
        title="添加用户"
        width="35%"
        :visible.sync="dialogFormVisible"
        @close="closeDialog()"
      >
        <el-form ref="addUser" :model="newData" :rules="rules">
          <el-form-item label="姓名" label-width="120px">
            <el-input
              v-model="newData.uname"
              placeholder="姓名"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="联系电话" prop="phone" label-width="120px">
            <el-input
              v-model="newData.phone"
              placeholder="联系电话"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="所管理的仓库" label-width="120px">
            <el-select v-model="newData.rcode" placeholder="点击选择">
              <el-option
                v-for="(item, index) in whAddAndEdit"
                :key="index"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
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
        title="编辑用户"
        width="35%"
        :visible.sync="editDialogFormVisible"
        @close="closeEditDialog()"
      >
        <el-form ref="editUser" :model="editDataDialogForm" :rules="rules">
          <el-form-item label="工号" label-width="120px">
            <el-input
              disabled
              v-model="editDataDialogForm.ucode"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="姓名" label-width="120px">
            <el-input
              placeholder="姓名"
              v-model="editDataDialogForm.uname"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="联系电话" label-width="120px" prop="phone">
            <el-input
              placeholder="联系电话"
              v-model="editDataDialogForm.phone"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="所管理的仓库" label-width="120px">
            <el-select placeholder="仓库" v-model="editDataDialogForm.rcode">
              <el-option
                v-for="(item, index) in whAddAndEdit"
                :key="index"
                :label="item.label"
                :value="item.value"
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
  name: "WorkerManager",
  data() {
    return {
      // 创建验证规则
      rules: {
        phone: [
          { required: true, message: "手机号必填", trigger: "blur" },
          {
            pattern: /^1[3456789]\d{9}$/,
            message: "手机号码格式不正确",
            trigger: "blur",
          },
        ],
      },
      tableData: [],
      // 新增窗口是否可见
      dialogFormVisible: false,
      //新增数据模型
      newData: {
        ucode: "000000",
        uname: "",
        rcode: "",
        phone: "",
        whname: "",
      },
      // 编辑窗口是否可见
      editDialogFormVisible: false,
      //修改数据模型
      editDataDialogForm: {
        ucode: "",
        uname: "",
        rcode: "",
        phone: "",
        whname: "",
      },
      whfilter: [],
      whAddAndEdit: [],
      query: "",
      pageIndex: 1,
      pageSize: 7,
      totalPage: 0,
      totaNum: 0,
      CurrentId: "0",
      //防止多次提交表单
      dialogFormSubmitVisible: false,
      //首次获取仓库信息
      whflag: true,
    };
  },
  methods: {
    filterTag(value, row) {
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
    //打开新增窗口
    openDialog() {
      // 打开窗口
      this.dialogFormVisible = true;
    },
    //关闭新建窗口
    closeDialog() {
      (this.newData.ucode = "000000"),
        (this.newData.uname = ""),
        (this.newData.phone = ""),
        (this.newData.rcode = "");
      (this.newData.whname = ""),
        //关闭窗口
        (this.dialogFormVisible = false);
    },
    //打开编辑窗口
    handleEdit(row) {
      // console.log(row)
      //将信息写入到窗口中
      this.editDataDialogForm.ucode = row.ucode;
      this.editDataDialogForm.uname = row.uname;
      this.editDataDialogForm.rcode = row.rcode;
      this.editDataDialogForm.phone = row.phone;
      this.editDataDialogForm.whname = row.whname;
      //打开窗口
      this.editDialogFormVisible = true;
    },
    //关闭编辑窗口
    closeEditDialog() {
      this.editDataDialogForm.ucode = "";
      this.editDataDialogForm.uname = "";
      this.editDataDialogForm.rcode = "";
      this.editDataDialogForm.phone = "";
      this.editDataDialogForm.whname = "";
      this.editDialogFormVisible = false;
    },
    // 获取用户数据
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
        .get("getUser", params)
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
            this.getWareHouseList();
            this.whflag = false;
          }
        });
    },
    //获取仓库信息
    getWareHouseList() {
      var dic = new Array();
      // console.log(this.tableData);
      this.tableData.forEach((item) => {
        if (!this.whAddAndEdit.find((i) => i.value === item.rcode)) {
          dic["text"] = item.whname;
          dic["value"] = item.whname;
          this.whfilter.push(dic);
          dic = [];
          dic["label"] = item.whname;
          dic["value"] = item.rcode;
          this.whAddAndEdit.push(dic);
          dic = [];
        }
      });
      // console.log(this.whfilter);
      // console.log(this.whAddAndEdit);
    },
    //提交添加数据表单数据
    SubmitAddFormData() {
      if (this.dialogFormSubmitVisible) {
        return;
      }
      this.dialogFormSubmitVisible = true;
      this.$refs.addUser.validate((valid) => {
        if (valid) {
          // console.log("flag1")
          this.$http
            .post("/addUser", this.newData)
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
        }
      });
    },
    //提交修改数据表单
    SubmitEditFormData() {
      if (this.dialogFormSubmitVisible) {
        return;
      }
      this.dialogFormSubmitVisible = true;
      // console.log("flag1")
      this.$refs.editUser.validate((valid) => {
        if (valid) {
          this.$http
            .post("/editUser", this.editDataDialogForm)
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
        }
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
              ucode: row.ucode,
            },
          };
          this.$http
            .get("deleteUser", params)
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
};
</script>

<style scoped>
</style>