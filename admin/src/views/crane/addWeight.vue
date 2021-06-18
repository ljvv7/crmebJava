<template>
  <div class="div-box">
    <el-card class="box-card">
      <el-tabs v-model="activeName" @tab-click="seachList">
        <el-tab-pane label="配重信息" name="first">
          <el-form ref="form" :model="weightInfo" label-width="100px">
            <el-form-item label="车辆ID">
              <el-input v-model="weightInfo.craneid" disabled></el-input>
            </el-form-item>
            <el-form-item label="组合方式">
              <el-input v-model="weightInfo.legtype" placeholder="1:仅主臂,2:主臂+副臂,3:主臂+横臂,4:超起"></el-input>
            </el-form-item>
            <el-form-item label="支腿方式">
              <el-input v-model="weightInfo.legway"></el-input>
            </el-form-item>
            <el-form-item label="配重">
              <el-input v-model="weightInfo.equipweight"></el-input>
            </el-form-item>
            <el-form-item label="工作范围">
              <el-input v-model="weightInfo.workextent"></el-input>
            </el-form-item>
            <el-form-item label="备注">
              <el-input v-model="weightInfo.remarks"></el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" :plain="true" @click="onSubmit">保存</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>                                          
    </el-card>
  </div>
</template>


<script>
import crane from '@/api/crane'
import { param } from '@/utils'

  export default {
    data() {
      return {
        weightInfo: {},
        activeName: 'first',
      }

    },

    created(){
      this.weightInfo.craneid = this.$route.query.id
    },
    methods: {

      seachList(tab, event){
        this.tableFrom.page = 1
        // this.getDetail()
      },

      onSubmit() {
        crane.addWeight(this.weightInfo).then(res=>{
          this.$message({
            message: '配重新增成功!',
            type: 'success'
          });
        })
        this.$router.back(-1);
      }
      
    }
  }
</script>