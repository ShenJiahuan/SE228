<template>
  <div id="app">
    <el-header>
      <Header></Header>
    </el-header>
    <el-main id="main">
      <router-view/>
    </el-main>
    <el-footer>
      <Footer></Footer>
    </el-footer>
  </div>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";

export default {
  name: "app",
  components: {
    Footer,
    Header,
  },
}
</script>

<style>
  body {
    font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
  }

  #app {
    font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }

  #main {
    max-width: 1000px;
    margin: 0 auto;
  }
  a {
    color: #303133;
    text-decoration: none;
  }
</style>
