<template>
  <div>
    <el-carousel :interval="300000" type="card" height="200px">
      <el-carousel-item v-for="item in 6" :key="item">
        <el-image
            style="width: 200px; height: 200px ;padding-left: 200px"
            :src="url"
             ></el-image>
      </el-carousel-item>
    </el-carousel>

  </div>

</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      // url:'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg'
      url:'/img/xioajia.jpg'
    }
  },
}
</script>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>