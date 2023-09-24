import Vue from 'vue'
import App from './App.vue'
// 导入路由
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min.js'
// 导入axios
import axios from 'axios';
 
Vue.use(ElementUI)
// 配置axios
Vue.prototype.$http = axios//修改内部的$http为axios
axios.defaults.baseURL="https://localhost:8080/"
//添加Axios的拦截器
axios.interceptors.request.use(config=>{
  //每次发送请求我们都携带token信息
  var token=sessionStorage.getItem('token')
  config.headers['Authorization']=token //请求头带上Token
  return config;
},error=>{
  return Promise.reject(error);
})
/* 使用router钩子函数来处理
   未登陆返回登录页面
*/
router.beforeEach((to,from,next)=>{
  const username=sessionStorage.getItem('username')
  if(to.name!='Login'&& !username)next({name:'Login'})
  else next()
})
//使axios支持finally
require('promise.prototype.finally').shim();

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
