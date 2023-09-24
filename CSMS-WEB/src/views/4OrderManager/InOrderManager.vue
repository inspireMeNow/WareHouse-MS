<template>
  <div>
    <el-card>
      <el-form :inline="true">
        <el-form-item>
          <el-input placeholder="订单号" v-model="query" clearable></el-input>
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
        <el-table-column prop="icode" label="订单编号" align="center">
        </el-table-column>
        <el-table-column prop="pcode" label="产品编号" align="center">
        </el-table-column>
        <el-table-column prop="num" label="数量" align="center">
        </el-table-column>
        <el-table-column
          prop="itime"
          label="入库时间"
          align="center"
          :formatter="dateFormat"
        >
        </el-table-column>
        <el-table-column prop="rcode" label="仓库号" align="center">
        </el-table-column>
        <el-table-column
          prop="stype"
          label="货架类型"
          align="center"
          :filters="[
            { text: '小型', value: '小型' },
            { text: '中型', value: '中型' },
            { text: '大型', value: '大型' },
          ]"
          :filter-method="filterSh"
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
        <el-table-column prop="ucode" label="处理人工号" align="center">
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
    </el-card>
  </div>
</template>

<script>
import moment from "moment";
export default {
  name: "InOrderManager",
  data() {
    return {
      tableData: [],
      query: "",
      pageIndex: 1,
      pageSize: 7,
      totalPage: 0,
      totaNum: 0,
      CurrentId: "",
      //首次获取仓库信息
      whflag: true,
    };
  },
  methods: {
    filterSh(value, row) {
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
      this.$http.get("/getInOrder", params).then((res) => {
        // console.log(res);
        this.tableData = res.data.data.list;
        this.totalPage = res.data.data.pages;
        this.totaNum = res.data.data.total;
        // console.log(this.tableData);
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