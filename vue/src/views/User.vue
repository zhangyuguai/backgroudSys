<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="userName"></el-input>
      <el-input style="width: 200px" placeholder="请输入邮箱"
                suffix-icon="el-icon-message"
                class="ml-5"
                v-model="email"

      ></el-input>
      <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-position"
                class="ml-5"
                v-model="address"
      ></el-input>
      <el-button class="ml-5" type="primary" @click="searchCondition()">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="dialogFormVisible=true">新增 <i class="el-icon-circle-plus-outline"></i>
      </el-button>
      <el-popconfirm

          icon-color="red"
          title="确定删除吗？"
          confirm-button-text='确认'
          cancel-button-text='我还没想好'
          @confirm="delBatch(multipleTable)"
      >
        <el-button type="danger" slot="reference" class="ml-5" style=" margin-left: 5px;">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <el-upload
          class="upload-demo"
          action="http://localhost:9090/user/import"
          style="display: inline-block"
          :on-success="handleExcelImlSuccess"
          :show-file-list="false"
      >
      <el-button type="primary" class="ml-5" style=" margin-left: 5px;">导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" style=" margin-left: 5px;">导出 <i class="el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe
              :header-cell-class-name="headerBg"
              ref="multipleTable"
              @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="Id" width="80"></el-table-column>
      <el-table-column prop="userName" label="姓名" width="120"></el-table-column>
      <el-table-column prop="nickName" label="昵称"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="hadleEdit(scope)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm

              icon-color="red"
              title="确定删除吗？"
              confirm-button-text='确认'
              cancel-button-text='我还没想好'
              @confirm="delRow(scope)"
          >
            <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="5"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <el-dialog title="收货地址" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="姓名" label-width="150px">
          <el-input v-model="form.userName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称" label-width="150px">
          <el-input v-model="form.nickName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" label-width="150px">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" label-width="150px">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="住址" label-width="150px">
          <el-input v-model="form.address" autocomplete="off" width="fit-content"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handlePost">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "User",
  data() {
    return {
      tableData: [],
      pageNum: 1,
      pageSize: 5,
      total: 0,
      userName: '',
      email: '',
      address: '',
      dialogFormVisible: false,
      form: {},
      multipleTable: [],
      headerBg: 'headerBg',

    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      request.get("/user/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userName: this.userName,
          email: this.email,
          address: this.address
        }
      }).then(
          response => {
            this.tableData = response.data.records
            this.total = response.data.total
          },
          error => {
            console.log(error.getMessage())
          }
      )
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load()

    },

    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load()
    },
    searchCondition() {
      this.load();
    },
    reset() {
      this.userName = '';
      this.email = '';
      this.address = '';
      this.load();
    },
    handlePost() {
      this.dialogFormVisible = false;
      request.post("/user", this.form).then(
          res => {
            if (res.code === 200) {
              this.$message.success("保存成功");
              this.load()
            }else{
              this.$message.success(res.data.msg);
              this.load()
            }
          },
          error => {
            this.$message.error("保存失败");
          }
      )
      this.form = {}
    },
    hadleEdit(scope) {
      this.form = scope.row;
      console.log(this.form)
      this.dialogFormVisible = true;
    },
    delRow(scope) {
      const id = scope.row.id;
      request.delete("/user/" + id).then(
          res => {
            if (res.code === 200) {
              this.$message.success("删除成功");
              this.load();
            }else{
              this.$message.success(res.data.msg);
              this.load()
            }
          },
          error => {
            this.$message.error("删除失败!");
          }
      )
    },
    handleSelectionChange(val) {
      this.multipleTable = val.map(v => v.id)
      console.log(this.multipleTable)
    },
    delBatch(multipleTable) {
      request.delete("/user/del/batch", {
        data: multipleTable
      }).then(
          res => {
            if (res.code === 200) {
              this.$message.success("删除成功");
              this.load();
            }else{
              this.$message.success(res.data.msg);
              this.load()
            }
          },
          error => {
            this.$message.error("删除失败!");
          }
      )
    },
    exp(){
      window.open("http://localhost:9090/user/export");
    },
    handleExcelImlSuccess(){
      this.$message.success("文件上传成功!");
      this.load();
    }
  },
}
</script>

<style scoped>

</style>