<template>
  <div>
    <el-card>
      <el-form :inline="true">
        <el-form-item>
          <el-input placeholder="工号" clearable v-model="query"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getDatalist()">查询</el-button>
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
        <el-table-column prop="id" label="工号" align="center">
        </el-table-column>
        <el-table-column prop="uname" label="工号" align="center">
        </el-table-column>
        <el-table-column type="password" label="密码" align="center">
          xxxxxxxx
        </el-table-column>
        <el-table-column prop="email" label="电子邮箱" align="center">
        </el-table-column>
        <el-table-column
          prop="type"
          label="用户类型"
          align="center"
          :filters="[
            { text: '仓库管理员', value: '0' },
            { text: '仓库员工', value: '1' },
          ]"
          :filter-method="filterTag"
          filter-placement="bottom-end"
        >
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.idtype === '0' ? 'primary' : 'success'"
              disable-transitions
            >
              {{ scope.row.idtype === '0' ? "仓库管理员" : "仓库员工" }}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column prop="state" label="状态" align="center">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.state === 1 ? 'success' : 'danger'"
              disable-transitions
            >
              {{ scope.row.state == 1 ? "正常" : "禁用" }}
            </el-tag>
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
              @click="handleUpdatestate(scope.row)"
              v-if="scope.row.state === 1"
              >禁用</el-button
            >
            <el-button
              size="mini"
              type="success"
              @click="handleUpdatestate(scope.row)"
              v-if="scope.row.state === 0"
              >激活</el-button
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
      <!-- 编辑窗口 -->
      <el-dialog
        title="编辑用户"
        width="35%"
        :visible.sync="editDialogFormVisible"
        @close="closeEditDialog()"
      >
        <el-form :model="editDataDialogForm" :rules="rules">
          <el-form-item label="工号" label-width="120px">
            <el-input
              disabled
              v-model="editDataDialogForm.id"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="姓名" label-width="120px">
            <el-input
              placeholder="姓名"
              v-model="editDataDialogForm.uname"
              style="width: 250px"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="密码" label-width="120px">
            <el-input
              placeholder="如果要修改则在此输入"
              v-model="editDataDialogForm.passwd"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="电子邮箱" prop="email" label-width="120px">
            <el-input
              placeholder="电子邮箱"
              v-model="editDataDialogForm.email"
              style="width: 250px"
            ></el-input>
          </el-form-item>
          <el-form-item label="用户类型" label-width="120px">
            <el-select placeholder="仓库" v-model="editDataDialogForm.idtype">
              <el-option label="仓库管理员" value=0></el-option>
              <el-option label="仓库员工" value=1></el-option>
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
  name: "LoginManager",
  data() {
    return {
      rules:{
        email:[
          { required: true, message: "邮箱必填", trigger: "blur" },
          {
            pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
            message: "邮箱格式不正确",
            trigger: "blur",
          },
        ]
      },
      tableData: [],
      // 编辑窗口是否可见
      editDialogFormVisible: false,
      //修改数据模型
      editDataDialogForm: {
        id: "",
        uname: "",
        passwd: "",
        email: "",
        idtype: 1,
        state: 0,
      },
      rolefilter: [],
      roleAddAndEdit: [],
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
      return row.idtype === value;
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
      this.editDataDialogForm.id = row.id;
      this.editDataDialogForm.uname = row.uname;
      this.editDataDialogForm.email = row.email;
      this.editDataDialogForm.idtype = row.idtype;
      this.editDataDialogForm.state = row.state;
      //打开窗口
      this.editDialogFormVisible = true;
    },
    //关闭编辑窗口
    closeEditDialog() {
      this.editDataDialogForm.id = "";
      this.editDataDialogForm.uname = "";
      this.editDataDialogForm.passwd = "";
      this.editDataDialogForm.email = "";
      this.editDataDialogForm.idtype = 1;
      this.editDataDialogForm.state = 0;
      //关闭窗口
      this.editDialogFormVisible = false;
    },
    //获取登录用户数据
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
      this.$http.get("getPermit", params).then((res) => {
        // console.log(res);
        this.tableData = res.data.data.list;
        this.totalPage = res.data.data.pages;
        this.totaNum = res.data.data.total;
        // console.log(this.tableData);
      });
    },
    //获取角色信息
    getRoleList() {
      //日后角色类型变多再实现
    },
    //提交修改数据表单
    SubmitEditFormData() {
      if (this.dialogFormSubmitVisible) {
        return;
      }
      this.dialogFormSubmitVisible = true;
      // console.log("flag1")
      this.$http
        .post("/editPermit", this.editDataDialogForm)
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
    //切换状态
    handleUpdatestate(row) {
      this.$confirm("此操作将切换该用户的登录权限, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          //确认删除
          const params = {
            params: {
              id: row.id,
              state: row.state,
            },
          };
          this.$http
            .get("/changeState", params)
            .then((res) => {
              if (res.data.code == 3001) {
                this.$message({
                  type: "success",
                  message: "切换成功!",
                });
              } else {
                this.$message.error("切换失败");
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
            message: "已取消切换",
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