<template>
  <div style="line-height: 60px; display: flex">
      <div style="flex: 1;">
        <span :class="collapseBtnClass" style="cursor: pointer; font-size: 20px" @click="collapse"></span>
        <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
          <el-breadcrumb-item  :to="{ path: '/home' }">主页</el-breadcrumb-item>
          <el-breadcrumb-item v-show="!(currenTitleName==='主页')">{{currenTitleName}}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <el-dropdown style="width: 70px; cursor: pointer">
        <span>王小虎</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item style="font-size: 14px; padding: 5px 0">个人信息</el-dropdown-item>
          <router-link to="/login"><el-dropdown-item style="font-size: 14px; padding: 5px 0">退出</el-dropdown-item></router-link>

        </el-dropdown-menu>
      </el-dropdown>
  </div>

</template>

<script>
export default {
  name: "Head",
  props:['collapseBtnClass','collapse'],
  data() {
    return {

    }
  },
  computed:{
    currenTitleName(){
      return this.$store.state.currentPathName;
    }
  },
  watch:{

  },
  methods: {},

}
</script>

<style scoped>

</style>