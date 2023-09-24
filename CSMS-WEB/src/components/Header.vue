<template>
  <div class="header-container">
    <div class="l-content">
      <el-button
        @click="handleMenu"
        icon="el-icon-s-fold"
        size="medium"
        style="margin-left: 20px"
      ></el-button>
      <el-breadcrumb separator="/" style="padding-left: 20px" v-if="type">
        <el-breadcrumb-item
          @click.native="handleClick(item)"
          v-for="item in tags"
          :key="item.path"
          :to="{ path: item.path }"
          >{{ item.label }}</el-breadcrumb-item
        >
      </el-breadcrumb>
    </div>
    <div class="r-content">
      <!-- 头像下拉菜单 -->
      <el-dropdown @command="handleCommand">
        <span class="el-dropdown-link">
          <img class="user_img" src="@/assets/img/avatar.png" alt="" />
        </span>
        <el-dropdown-menu slot="dropdown">
          <!-- <el-dropdown-item command="a">个人信息</el-dropdown-item> -->
          <el-dropdown-item command="b">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Header",
  data() {
    return {
      type: false,
    };
  },
  methods: {
    handleMenu() {
      this.$store.commit("collapseChange");
    },
    handleClick(item) {
      // console.log(item)
    },
    handleCommand(command) {
      if (command === "b") {
        sessionStorage.clear();
        this.$router.push("/Login");
      }
    },
    Flag() {
      if (sessionStorage.getItem("type") === "0") {
        this.type = true;
      } else {
        this.type = false;
      }
    },
  },
  created() {
    this.Flag();
  },
  computed: {
    ...mapState({
      tags: (state) => state.tab.tabsList,
    }),
  },
};
</script>

<style lang="less" scoped>
.header-container {
  height: 60px;
  background-color: #b3c0d1;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.firstPage {
  color: #fff;
  font-size: 16px;
  margin-left: 10px;
  cursor: pointer;
}

.r-content {
  padding-right: 20px;

  .user_img {
    width: 40px;
    height: 40px;
    border-radius: 50%;
  }
}

.l-content {
  display: flex;
  align-items: center;

  /deep/.el-breadcrumb__item {
    .el-breadcrumb__inner {
      font-weight: normal;

      &.is-link {
        color: #666;
      }
    }

    &:last-child {
      .el-breadcrumb__inner {
        color: #fff;
      }
    }
  }
}
</style>