<template>
  <div>
    <el-card>
      <el-form :inline="true">
        <el-form-item>
          <el-input placeholder="仓库号" v-model="query" clearable></el-input>
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
        <el-table-column prop="whcode" label="仓库号" align="center">
        </el-table-column>
        <el-table-column prop="whname" label="仓库名" align="center">
        </el-table-column>
        <el-table-column prop="address" label="仓库地址" align="center">
        </el-table-column>
        <el-table-column
          prop="snum"
          label="小型货架余量"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="mnum"
          label="中型货架余量"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="bnum"
          label="大型货架余量"
          align="center"
        ></el-table-column>
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
        title="添加仓库"
        width="35%"
        :visible.sync="dialogFormVisible"
        @close="closeDialog()"
      >
        <el-form :model="newData">
          <el-form-item label="仓库名" label-width="120px">
            <el-input
              v-model="newData.whname"
              placeholder="仓库名称"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="仓库地址" label-width="120px">
            <el-input
              v-model="newData.address"
              placeholder="仓库地址"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="小型货架剩余量" label-width="120px">
            <el-input
              type="number"
              v-model="newData.snum"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="中型货架剩余量" label-width="120px">
            <el-input
              type="number"
              v-model="newData.mnum"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="大型货架剩余量" label-width="120px">
            <el-input
              type="number"
              v-model="newData.bnum"
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
        title="编辑仓库"
        width="35%"
        :visible.sync="editDialogFormVisible"
        @close="closeEditDialog()"
      >
        <el-form :model="editDataDialogForm">
          <el-form-item label="仓库号" label-width="120px">
            <el-input
              disabled
              v-model="editDataDialogForm.whcode"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="仓库名" label-width="120px">
            <el-input
              placeholder="仓库名"
              v-model="editDataDialogForm.whname"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="仓库所在地" label-width="120px">
            <el-input
              placeholder="仓库所在地"
              v-model="editDataDialogForm.address"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="小型货架剩余量" label-width="120px">
            <el-input
              type="number"
              v-model="editDataDialogForm.snum"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="中型货架剩余量" label-width="120px">
            <el-input
              type="number"
              v-model="editDataDialogForm.mnum"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="大型货架剩余量" label-width="120px">
            <el-input
              type="number"
              v-model="editDataDialogForm.bnum"
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
export default {
  name: "StorageManager",
  data() {
    return {
      tableData: [],
      // 新增窗口是否可见
      dialogFormVisible: false,
      //新增数据模型
      newData: {
        whcode: 0,
        whname: "",
        address: "",
        snum: 0,
        mnum: 0,
        bnum: 0,
      },
      // 编辑窗口是否可见
      editDialogFormVisible: false,
      //修改数据模型
      editDataDialogForm: {
        whcode: 0,
        whname: "",
        address: "",
        snum: 0,
        mnum: 0,
        bnum: 0,
      },
      query: "",
      pageIndex: 1,
      pageSize: 7,
      totalPage: 0,
      totaNum: 0,
      CurrentId: "",
      //防止多次提交表单
      dialogFormSubmitVisible: false,
    };
  },
  methods: {
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
      this.newData.whcode = "ck000";
      this.newData.whname = "";
      this.newData.address = "";
      this.newData.snum = 0;
      this.newData.mnum = 0;
      this.newData.bnum = 0;
      //关闭窗口
      this.dialogFormVisible = false;
    },
    //打开编辑窗口
    handleEdit(row) {
      // console.log(row)
      //将信息写入到窗口中
      this.editDataDialogForm.whcode = row.whcode;
      this.editDataDialogForm.whname = row.whname;
      this.editDataDialogForm.address = row.address;
      this.editDataDialogForm.snum = row.snum;
      this.editDataDialogForm.mnum = row.mnum;
      this.editDataDialogForm.bnum = row.bnum;
      //打开窗口
      this.editDialogFormVisible = true;
    },
    //关闭编辑窗口
    closeEditDialog() {
      this.editDataDialogForm.whcode = "";
      this.editDataDialogForm.whname = "";
      this.editDataDialogForm.address = "";
      this.editDataDialogForm.snum = 0;
      this.editDataDialogForm.mnum = 0;
      this.editDataDialogForm.bnum = 0;

      this.editDialogFormVisible = false;
    },
    // 获取仓库数据
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
        .get("/getWh", params)
        .then((res) => {
          // console.log(res);
          this.tableData = res.data.data.list;
          this.totalPage = res.data.data.pages;
          this.totaNum = res.data.data.total;
          // console.log(this.tableData);
        });
    },
    //提交添加数据表单数据
    SubmitAddFormData() {
      if (this.dialogFormSubmitVisible) {
        return;
      }
      this.dialogFormSubmitVisible = true;
      // console.log("flag1")
      this.$http
        .post("/addWh", this.newData)
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
        .catch(() => {
          this.$message.error("添加失败");
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
        .post("/editWh", this.editDataDialogForm)
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
        .catch(() => {
          this.$message.error("修改失败");
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
              whcode: row.whcode,
            },
          };
          this.$http
            .get("deleteWh", params)
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
            .catch(() => {
              this.$message.error("删除失败");
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

<style>
</style>