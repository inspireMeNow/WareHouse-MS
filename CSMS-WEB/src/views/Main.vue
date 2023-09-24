<template>
  <div>
    <el-container>
      <el-aside width="auto"><aside-menu/>
      </el-aside>
      <el-container>
        <el-header><my-header/>
        </el-header>
        <common-tag v-if="type"/>
        <el-main>
          <router-view/>
        </el-main>
        <el-footer>
          <my-footer/>
        </el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import AsideMenu from '@/components/AsideMenu.vue';
import MyHeader from '@/components/Header.vue';
import MyFooter from '@/components/Footer.vue';
import CommonTag from '@/components/CommonTag.vue'
export default {
  name: "Main",
  data() {
    return {
      msg: "Main",
      type:false
    };
  },
  components: {
    AsideMenu,
    MyHeader,
    MyFooter,
    CommonTag
  },
  methods:{
    Flag(){
      if(sessionStorage.getItem("type")==="0"){
        this.type=true;
      }
      else{
        this.type=false;
      }
    }
  },
  mounted() {
    this.Flag();
  },
};
</script>
<style scoped>
  .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
  .el-main {
    background-color: #E9EEF3;
    color: #333;
  }
  .el-header{
    padding: 0%;
    margin: 0%;
  }
  .el-aside{
    background-color:#545c64
  }
</style>