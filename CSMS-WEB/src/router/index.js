import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/views/Main'
import Home from '@/views/Home'
import LoginManager from '@/views/1UserManager/LoginManager'
import WorkManager from '@/views/1UserManager/WorkerManager'
import ShelveManager from '@/views/2ManufactureManager/ShelveManager'
import StorageManager from '@/views/2ManufactureManager/StorageManager'
import GoodinfoManger from '@/views/3MemoryManager/GoodinfoManager'
import GoodManger from '@/views/3MemoryManager/GoodManager'
import InOrderManager from '@/views/4OrderManager/InOrderManager'
import OutOrderManager from '@/views/4OrderManager/OutOrderManager'
import Login from '@/views/Login.vue'
import MyCommonHome from'@/views/CommonHome.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Main',
      component: Main,
      redirect:'/commonhome',
      children:[
        // 首页
        {
          path:'/home',
          name:'Home',
          component:Home
        },
        // 用户管理
        {
          path:'/workmanager',
          name:'MyworkManager',
          component:WorkManager
        },
        {
          path:'/loginmanager',
          name:'MyloginManager',
          component:LoginManager
        },
        // 设施管理
        {
          path:'/storagemanager',
          name:'MystorageManager',
          component:StorageManager
        },
        {
          path:'/shelvemanager',
          name:'MyshelverManager',
          component:ShelveManager
        },
        // 存储管理
        {
          path:'/goodinfomanager',
          name:'Mygoodinfomanager',
          component:GoodinfoManger
        },
        {
          path:'/goodmanager',
          name:'MygoodManager',
          component:GoodManger
        },
        //订单管理
        {
          path:'/inordermanager',
          name:'MyinorderManager',
          component:InOrderManager
        },
        {
          path:'/outordermanager',
          name:'MyoutorderManager',
          component:OutOrderManager
        },
        {
          path:'/commonhome',
          name:'Mycommonhome',
          component:MyCommonHome
        }
      ]
    },
    {
      path:"/Login",
      name:"Login",
      component:Login
    }
  ]
})
