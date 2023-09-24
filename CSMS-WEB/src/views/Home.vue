<template>
  <div>
    <el-row>
      <el-col :span="8">
        <el-card class="box-card">
          <div class="user">
            <img src="@/assets/img/avatar.png" />
            <div class="userinfo">
              <p class="name">{{ username }}</p>
              <p class="access">超级管理员</p>
            </div>
          </div>
          <div class="login-info">
            <p>
              上次登录的时间:<span>{{ lastLoginDate }}</span>
            </p>
            <p>上次登录的地点:<span>河北</span></p>
          </div>
        </el-card>
        <el-card class="box-card" style="margin-top: 20px">
          <!-- 统计图1 -->
          <div ref="echarts1" style="height: 300px"></div>
        </el-card>
      </el-col>
      <el-col :span="16">
        <!-- 统计图3 -->
        <el-card class="box-card system_bulletin">
          <div ref="echarts3" style="height: 300px"></div>
        </el-card>
        <!-- 统计图2 -->
        <el-card class="box-card" style="margin: 20px">
          <div ref="echarts2" style="height: 300px"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from "echarts";
import moment from "moment";
export default {
  name: "Home",
  data() {
    return {
      lastLoginDate: "",
      username: "",
    };
  },
  methods: {
    lastLogintime() {
      var Time = new Date();
      var dateTime = moment(Time).format("YYYY-MM-DD HH:MM:SS");
      var lastTime=sessionStorage.getItem("username")+"_lastTime";
      if (window.localStorage.getItem(lastTime)) {
        this.lastLoginDate = window.localStorage.getItem(lastTime);
      } else {
        this.lastLoginDate = dateTime;
      }
      window.localStorage.setItem(lastTime, dateTime);
      // console.log(dateTime)
    },
    getStockInfo() {
      //货架剩余量柱状图
      this.$http.get("/getBar").then((res) => {
        // 将数据传递给 ECharts
        var parsedData = res.data.data;
        // console.log(res.data.data);
        let xdata = [];
        let ydata = [];
        for (var i = 0; i < parsedData.length; i++) {
          xdata.push(parsedData[i].id);
          ydata.push(parsedData[i].num);
        }
        // 基于准备好的dom，初始化echarts实例
        var myChart1 = echarts.init(this.$refs.echarts1);
        // 指定图表的配置项和数据
        var option1 = {
          title: {
            text: "",
          },
          tooltip: {},
          legend: {
            data: ["货架剩余总量"],
          },
          xAxis: {
            data: xdata,
            // [parsedData[0].id, parsedData[1].id, parsedData[2].id]
          },
          yAxis: {},
          series: [
            {
              name: "货架剩余总量",
              type: "bar",
              data: ydata,
            },
          ],
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart1.setOption(option1);
      });
      this.$http.get("/getInLine").then((res) => {
        // console.log(res.data)
        var parsedData = res.data.data;
        parsedData=parsedData.sort(this.sortData);
        let xdata = [];
        let ydata = [];
        for (var i = 0, len = parsedData.length; i < len; i++) {
          xdata.push(parsedData[len - 1 - i].date);
          ydata.push(parsedData[len - 1 - i].num);
        }
        var myChart2 = echarts.init(this.$refs.echarts2);
        var option2 = {
          title: {
            text: "近期总入库情况",
          },
          tooltip: {
            trigger: "axis",
          },
          legend: {
            data: ["入库数量"],
          },
          grid: {
            left: "3%",
            right: "4%",
            bottom: "3%",
            containLabel: true,
          },
          toolbox: {
            feature: {
              saveAsImage: {},
            },
          },
          xAxis: {
            type: "category",
            boundaryGap: false,
            data: xdata,
          },
          yAxis: {
            type: "value",
          },
          series: [
            {
              name: "入库数量",
              type: "line",
              stack: "Total",
              data: ydata,
            },
          ],
        };
        myChart2.setOption(option2);
      });
      this.$http.get("/getOutLine").then((res) => {
        // console.log(res.data)
        var parsedData = res.data.data;
        parsedData=parsedData.sort(this.sortData);
        // console.log(parsedData);
        let xdata = [];
        let ydata = [];
        for (var i = 0, len = parsedData.length; i < len; i++) {
          xdata.push(parsedData[len - 1 - i].date);
          ydata.push(parsedData[len - 1 - i].num);
        }
        var myChart3 = echarts.init(this.$refs.echarts3);
        var option3 = {
          title: {
            text: "近期总出库情况",
          },
          tooltip: {
            trigger: "axis",
          },
          legend: {
            data: ["出库数量"],
          },
          grid: {
            left: "3%",
            right: "4%",
            bottom: "3%",
            containLabel: true,
          },
          toolbox: {
            feature: {
              saveAsImage: {},
            },
          },
          xAxis: {
            type: "category",
            boundaryGap: false,
            data: xdata,
          },
          yAxis: {
            type: "value",
          },
          series: [
            {
              name: "出库数量",
              type: "line",
              stack: "Total",
              data: ydata,
              itemStyle: {
                normal: {
                  color: "#8cd5c2", //改变折线点的颜色
                  lineStyle: {
                    color: "#8cd5c2", //改变折线颜色
                  },
                },
              },
            },
          ],
        };
        myChart3.setOption(option3);
      });
    },
    sortData(a, b){
        return new Date(b.date).getTime() - new Date(a.date).getTime()
    }
  },
  mounted() {
    this.username = sessionStorage.getItem("username");
    this.lastLogintime();
    this.getStockInfo();
  },
};
</script>

<style lang="less" scoped>
.user {
  padding-bottom: 20px;
  margin-bottom: 20px;
  border-bottom: 1px solid #ccc;
  display: flex;
  align-items: center;
  img {
    margin-left: 40px;
    width: 100px;
    height: 100px;
    border-radius: 50%;
  }
  .userinfo {
    margin-left: 40px;
    .name {
      font-size: 32px;
      margin-bottom: 20px;
    }
    .access {
      color: #999;
    }
  }
}
.login-info {
  p {
    line-height: 28px;
    font-size: 14px;
    color: #999999;
    span {
      color: #666666;
      margin-left: 20px;
    }
    margin-top: 5%;
  }
}
.system_bulletin {
  margin-left: 2%;
  .system_title {
    font-size: 22px;
    color: #000000;
  }
  .system_context {
    padding-bottom: 10px;
    margin-bottom: 10px;
    border-bottom: 1px solid rgb(242, 242, 242);
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>