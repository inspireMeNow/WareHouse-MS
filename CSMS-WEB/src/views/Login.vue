<template>
  <div class="login_container">
    <div class="login_form">
      <p class="login_title">电脑配件库存管理系统</p>
      <el-form
        ref="formName"
        :model="form"
        :rules="rules"
        status-icon
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="账号" prop="username">
          <el-input v-model="form.username" placeholder="请输入账号"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            type="password"
            v-model="form.password"
            placeholder="请输入密码"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="medium" @click="submitFormData"
            >登录</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      form: {
        username: "",
        password: "",
      },
      rules: {
        username: [{ required: true, message: "请输入账号", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
      },
    };
  },
  methods: {
    //提交表单的数据
    submitFormData() {
      this.$refs["formName"].validate((valid) => {
        if (valid) {
          this.$http.post("/login", this.form).then((res) => {
            // console.log(res)
            if (res.data.code == 5001) {
              //表示登录成功
              //1.存储相关的token信息 token信息在响应的head中
              sessionStorage.setItem("token", res.data.data.token);
              sessionStorage.setItem("username", res.data.data.username);
              sessionStorage.setItem("type", res.data.data.type);
              sessionStorage.setItem("ucode", res.data.data.ucode);
              this.$message({
                showClose: true,
                message: "登录成功",
                type: "success",
              });
              //2.路由到主界面
              if (res.data.data.type === "0") {
                this.$router.push("/home");
              } else {
                sessionStorage.setItem("rcode", res.data.data.rcode);
                this.$router.push("/");
              }
            } else {
              //表示登录失败
              // console.log(res.data.data)
              this.$message.error(res.data.data);
            }
          });
        } else {
          // console.log('error submit!!');
          return false;
        }
      });
    },
  },
};
</script>
<style lang="less" scoped>
.login_container {
  width: 100%;
  height: 100vh;
  background-color: rgba(242, 242, 242, 1);
  background-image: url(../assets/img/bg.png);
  background-position: center bottom;
  background-repeat: no-repeat;
  //   background-size: 1920px 1080px;
  background-size: 100% 100%;
  border: none;
  border-radius: 0px;
  display: flex;
  justify-content: center;
  align-items: center;
  .login_form {
    width: 510px;
    margin: 0px auto;
    padding: 0 55px 15px 35px;
    background-color: rgba(255, 255, 255, 0.7);
    border: none;
    border-radius: 5px;
    // 添加阴影效果
    box-shadow: 0 0 25px #cac6c6;
    .login_title {
      font-family: "微软雅黑 Bold", "微软雅黑";
      font-weight: 700;
      text-decoration: none;
      color: rgb(0, 121, 254);
      font-size: 32px;
      margin-top: 50px;
      margin-bottom: 30px;
      text-align: center;
    }
  }
}
</style>