<template>
  <div class="div-box">
    <el-card class="box-card">
      <el-tabs v-model="activeName" @tab-click="seachList">
        <el-tab-pane label="车辆信息" name="first">
          <el-form ref="form" :model="craneInfo" label-width="100px">
            <el-form-item label="品牌名称">
              <el-select v-model="craneInfo.cbrands" placeholder="请选择" @change="getOListById">
                <el-option
                  v-for="item in brandList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="车辆型号">
              <el-input v-model="craneInfo.name"></el-input>
            </el-form-item>
            <el-form-item label="最大起重量">
              <el-input v-model="craneInfo.maxweight"></el-input>
            </el-form-item>
            <el-form-item label="指导价格">
              <el-input v-model="craneInfo.guidePrice"></el-input>
            </el-form-item>
            <el-form-item label="车型图片" >
              <div class="upLoadPicBox" @click="modalPicTap('1')" :disabled="isDisabled">
                <div class="upLoad">
                  <i class="el-icon-camera cameraIconfont" />
                </div>
              </div>
              <!-- <el-input v-model="craneInfo.images" hidden></el-input> -->
            </el-form-item>
            <el-form-item label="车辆简介">
              <el-input v-model="craneInfo.introduce"></el-input>
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
        isDisabled: this.$route.params.isDisabled==='1'?true:false,
        craneInfo: {
          cbrands: null,
          name: null,
          maxweight: null,
          guidePrice: null,
          images: null,
          introduce: null
        },
        brandList: [],
        activeName: 'first',
      }

    },

    created(){
        this.getBrandList()
    },
    methods: {

      getBrandList(){
        this.listLoading = true
        crane.getBrandList().then(res =>{
          this.brandList = res
          this.listLoading = true
        }).catch(res => {
          this.listLoading = false
        })
      },
      getOListById(cbrandid){
        this.craneInfo.cbrands = cbrandid
      },

      seachList(tab, event){
        this.tableFrom.page = 1
        // this.getDetail()
      },

      onSubmit() {
        crane.addCrane(this.craneInfo).then(res=>{
          this.$message({
            message: '车辆新增成功!',
            type: 'success'
          });
        })
        this.$router.push({
          path: 'craneQuery'
        })
      },

      modalPicTap (tit, num, i) {
        const _this = this;
        if(_this.isDisabled) return;
        this.$modalUpload(function(img) {
          if(tit==='1'&& !num){
            _this.craneInfo.images = img[0].sattDir
          }
        },tit, 'content')
      },
      
    }
  }
</script>