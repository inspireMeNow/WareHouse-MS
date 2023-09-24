<template>
  <div>
    <el-menu
      :default-active="InitNavState"
      class="el-menu-vertical-demo"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      :collapse="isCollapse"
    >
      <h3>{{ isCollapse ? "" : "电脑配件库存管理系统" }}</h3>
      <!-- 没有二级菜单 -->
      <el-menu-item
        v-for="item in noChildren"
        :key="item.name"
        :index="item.name"
        @click="clickMenu(item)"
      >
        <i :class="`el-icon-${item.icon}`"></i>
        <span>{{ item.label }}</span>
      </el-menu-item>
      <!-- 有二级菜单 -->
      <el-submenu
        v-for="item in hasChildren"
        :key="item.name"
        :index="item.name"
      >
        <template slot="title">
          <i :class="`el-icon-${item.icon}`"></i>
          <span slot="title">{{ item.label }}</span>
        </template>
        <el-menu-item-group
          v-for="subItem in item.children"
          :key="subItem.name"
        >
          <el-menu-item :index="subItem.name" @click="clickMenu(subItem)">{{
            subItem.label
          }}</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
  </div>
</template>
<script>
export default {
  name: "AsideMenu",
  data() {
    return {
      MenuData: [],
      adminMenuData: [
        {
          path: "/home",
          name: "Home",
          label: "首页",
          icon: "s-home",
          url: "Home",
        },
        {
          label: "用户管理",
          icon: "s-custom",
          name: "1UserManager",
          children: [
            // 用户管理
            {
              path: "/workmanager",
              name: "MyworkManager",
              label: "人员管理",
              url: "1UserManager/WorkerManager",
            },
            {
              path: "/loginmanager",
              name: "MyloginManager",
              label: "登录管理",
              url: "1UserManager/LoginManager",
            },
          ],
        },
        {
          label: "设施管理",
          icon: "menu",
          name: "2ManufactureManager",
          children: [
            // 设施管理
            {
              path: "/storagemanager",
              name: "MystorageManager",
              label: "仓库管理",
              url: "2ManufactureManager/StorageManager",
            },
            {
              path: "/shelvemanager",
              name: "MyshelverManager",
              label: "货架管理",
              url: "2ManufactureManager/ShelveManager",
            },
          ],
        },
        {
          label: "存储管理",
          icon: "s-goods",
          name: "MemoryManager",
          children: [
            // 存储管理
            {
              path: "/goodinfomanager",
              name: "Mygoodinfomanager",
              label: "商品信息管理",
              url: "3MemoryManager/GoodinfoManger",
            },
            {
              path: "/goodmanager",
              name: "MygoodManager",
              label: "库存管理",
              url: "3MemoryManager/GoodManger",
            },
          ],
        },
        {
          label: "订单管理",
          icon: "s-order",
          name: "OrderManager",
          children: [
            //订单管理
            {
              path: "/inordermanager",
              name: "MyinorderManager",
              label: "入库订单管理",
              url: "4OrderManager/InOrderManager",
            },
            {
              path: "/outordermanager",
              name: "MyoutorderManager",
              label: "出库订单管理",
              url: "4OrderManager/OutOrderManager",
            },
          ],
        },
      ],
      commonMenuData: [
        {
          path: "/commonhome",
          name: "Home",
          label: "首页",
          icon: "s-home",
          url: "Home",
        },
      ],
      InitNavState: "",
    };
  },
  mounted() {
    this.typeCheck();
    this.InitNavState = this.MenuData[0].name;
  },
  methods: {
    clickMenu(item) {
      // 如果需要更新的路由和当前的路由不一致就更新,!(this.$route.path === '/FirstPage' && item.path === '/')用于解决路由为'/'时的报错
      if (
        this.$route.path != item.path &&
        !(this.$route.path === "/home" && item.path === "/"  && item.path === "/commonhome")
      ) {
        this.$router.push(item.path);
        //触发面包屑
        this.$store.commit("menuChange", item);
      }
    },
    typeCheck() {
      // console.log(sessionStorage.getItem("type"))
      if (sessionStorage.getItem("type") === "0") {
        this.MenuData = this.adminMenuData;
      } else {
        this.MenuData = this.commonMenuData;
      }
      // this.MenuData = this.adminMenuData;
    },
  },
  computed: {
    hasChildren() {
      // 过滤获取所有的有二级菜单的数据
      return this.MenuData.filter((item) => item.children);
    },
    noChildren() {
      // 过滤所有只有一级菜单的数据
      return this.MenuData.filter((item) => !item.children);
    },
    isCollapse() {
      return this.$store.state.tab.isCollapse;
    },
  },
};
</script>

<style lang="less" scoped>
.el-menu {
  height: 100vh;
  h3 {
    color: #fff;
    text-align: center;
    padding: 30px;
    padding-bottom: 10px;
    line-height: 48px;
    font-size: 16px;
    font-weight: 400;
  }
  border-right: none;
}
</style>