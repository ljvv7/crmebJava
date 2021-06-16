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
            <el-form-item label="车型图片">
              <el-input v-model="craneInfo.images"></el-input>
            </el-form-item>
            <el-form-item label="车辆简介">
              <el-input v-model="craneInfo.introduce"></el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" :plain="true" @click="onSubmit">保存</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="配重列表" name="second">
          <el-table
            :data="weightData.data"
            style="width: 100%"
            max-height="700">
              <el-table-column
                fixed
                prop="id"
                label="配重ID"
                width="150">
              </el-table-column>
              <el-table-column
                prop="craneid"
                label="车辆ID"
                width="150">
              </el-table-column>
              <el-table-column
                prop="legtype"
                label="组合方式"
                width="150">
              </el-table-column>
              <el-table-column
                prop="legway"
                label="支腿方式"
                width="150">
              </el-table-column>
              <el-table-column
                prop="equipweight"
                label="配重"
                width="150">
              </el-table-column>
              <el-table-column
                prop="workextent"
                label="工作范围"
                width="150"
                >
              </el-table-column>
              <el-table-column
                prop="remarks"
                label="备注"
                width="150"
                >
              </el-table-column>
              <el-table-column label="性能参数" min-width="100" fixed="right" align="center">
                <template slot-scope="scope">
                  <el-button type="text" size="small" class="mr10" @click="seeWay(scope.row.id)">详情</el-button>
                </template>
              </el-table-column>
              <el-table-column label="操作" min-width="100" fixed="right" align="center">
                <template slot-scope="scope">
                  <el-button type="text" size="small" class="mr10" @click="rmdelete(scope.row.id)">移除</el-button>
                </template>
              </el-table-column>
          </el-table>
        </el-tab-pane>

        <el-tab-pane label="组合方式" name="third">
          <el-table
            :data="wayData.data"
            style="width: 100%"
            max-height="700">
              <el-table-column
                fixed
                prop="id"
                label="组合ID"
                width="100">
              </el-table-column>
              <el-table-column
                prop="weightid"
                label="配重ID"
                width="100">
              </el-table-column>
              <el-table-column
                prop="craneid"
                label="车辆ID"
                width="100">
              </el-table-column>
              <el-table-column
                prop="radius"
                label="工作范围"
                width="150">
              </el-table-column>
              <el-table-column
                prop="primaryLength"
                label="主臂长度"
                width="150">
              </el-table-column>
              <el-table-column
                prop="minorLength"
                label="次臂长度"
                width="150">
              </el-table-column>
              <el-table-column
                prop="weight"
                label="起重量"
                width="150"
                >
              </el-table-column>
              <el-table-column
                prop="way"
                label="伸缩方式"
                width="150"
                >
              </el-table-column>
              <el-table-column
                prop="legtype"
                label="组合方式"
                width="100"
                >
              </el-table-column>
          </el-table>
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
        craneInfo: {},
        brandList: [],
        tableFrom: {
          // cbrandid: null,
          craneid: null,
          // craneName: '',
          page: 1,
          limit: 10
        },
        wayTableFrom:{
          craneid: null,
          cweightid : null,
          cwayid: null,
          page: 1,
          limit: 10
        },
        weightData: {
          data: [],
          total: 0
        },
        wayData: {
          data: [],
          total: 0
        },
        activeName: 'first',
        url: this.images,
        srcList: this.images,
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
          console.log(res);
          this.$message({
            message: '车辆新增成功!',
            type: 'success'
          });
        })
        this.$router.push({
          path: 'craneQuery'
        })
      }
      
    }
  }
</script>