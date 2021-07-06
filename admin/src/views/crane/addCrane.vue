<template>
  <div class="div-box">
    <el-card class="box-card">
      <el-tabs v-model="activeName" @tab-click="seachList">
        <el-tab-pane label="车辆信息" name="first">
          <el-form :model="craneInfo" label-width="100px" :rules="rules" ref="ruleForm" class="demo-ruleForm">
            <el-form-item label="品牌名称" prop="cbrands">
              <el-select v-model="craneInfo.cbrands" placeholder="请选择" @change="getOListById">
                <el-option
                  v-for="item in brandList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="车辆型号" prop="name">
              <el-input v-model="craneInfo.name"></el-input>
            </el-form-item>
            <el-form-item label="最大起重量" prop="maxweight">
              <el-input v-model="craneInfo.maxweight"></el-input>
            </el-form-item>
            <el-form-item label="指导价格" prop="guidePrice">
              <el-input v-model="craneInfo.guidePrice"></el-input>
            </el-form-item>
            <el-form-item label="车型图片" prop="images">
              <div class="upLoadPicBox" @click="modalPicTap('1')" :disabled="isDisabled">
                <div v-if="craneInfo.images" class="pictrue">
                  <el-image :src="craneInfo.images"/>
                </div>
                <div v-else class="upLoad">
                  <i class="el-icon-camera cameraIconfont" />
                </div>
              </div>
            </el-form-item>
            <el-form-item label="车辆简介" prop="introduce">
              <el-input v-model="craneInfo.introduce"></el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" :plain="true" @click="onSubmit('ruleForm')">保存</el-button>
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
          images: '',
          introduce: null
        },
        rules: {
          cbrands: [
            {required: true, message: '请选择品牌', trigger: 'change'}
          ],
          name: [
            {required: true, message: '请输入型号', trigger: 'blur'}
          ],
          maxweight: [
            {required: true, message: '请输入最大起重量', trigger: 'blur'}
          ],
          guidePrice: [
            {required: true, message: '请输入指导价格', trigger: 'blur'}
          ],
          // images: [
          //   {required: true, message: '请上传图片'}
          // ],
          introduce: [
            {required: true, message: '请输入简介', trigger: 'blur'}
          ]
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

      onSubmit(ruleForm) {
        this.$refs[ruleForm].validate((valid) => {
          if (valid) {
            crane.addCrane(this.craneInfo).then(res=>{
              this.$message({
                message: '车辆新增成功!',
                type: 'success'
              });
            })
            this.$router.push({
              path: 'craneQuery'
            })
          } else {
            this.$message({
              message: '请将车辆信息输入完整!',
              type: 'error'
            });
          }
        });

        
      },

      modalPicTap (tit) {
        const _this = this;
        if(_this.isDisabled) return;
        this.$modalUpload(function(img) {
          if(tit==='1'){
            _this.craneInfo.images = img[0].sattDir
          }
        },tit, 'content')
      },     
    }
  }
</script>