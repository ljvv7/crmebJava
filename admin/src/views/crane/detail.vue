<template>
  <div class="div-box">
    <el-card class="box-card">
      <el-tabs v-model="activeName" @tab-click="seachList">
        <el-tab-pane label="车辆信息" name="first">
          <el-form ref="form" :model="detailInfo" label-width="100px">
            <el-form-item label="车辆ID">
              <el-input v-model="detailInfo.id"></el-input>
            </el-form-item>
            <el-form-item label="品牌ID">
              <el-input v-model="detailInfo.cbrands"></el-input>
            </el-form-item>
            <el-form-item label="车辆型号">
              <el-input v-model="detailInfo.name"></el-input>
            </el-form-item>
            <el-form-item label="最大起重量">
              <el-input v-model="detailInfo.maxweight"></el-input>
            </el-form-item>
            <el-form-item label="车型图片">
              <el-input v-model="detailInfo.image"></el-input>
            </el-form-item>
            <el-form-item label="车辆简介">
              <el-input v-model="detailInfo.remarks"></el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="onSubmit">保存</el-button>
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
        detailInfo: {},
        tableFrom: {
          // cbrandid: null,
          craneid: this.$route.query.id,
          // craneName: '',
          page: 1,
          limit: 10
        },
        wayTableFrom:{
          craneid: this.$route.query.id,
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
        this.getDetail()
        this.getWeightList()
    },
    methods: {

      getDetail(){
        this.listLoading = true
        crane.getCraneList(this.tableFrom).then(res=>{
          this.detailInfo = res.admCraneList[0]
        })
      },

      getWeightList(){
        this.listLoading = true
        crane.getWeightList(this.tableFrom).then(res=>{
          this.weightData.data = res.admWeightList
          this.weightData.total = res.count
        })
      },

      getWayList(){
        this.listLoading = true
        crane.getWayList(this.wayTableFrom).then(res=>{
          this.wayData.data = res.admWayList
          this.wayData.total = res.count
        })
      },

      seachList(tab, event){
        this.tableFrom.page = 1
        // this.getDetail()
      },

      seeWay(id) {
        this.wayTableFrom.cweightid = id
        this.getWayList()
        this.activeName = 'third'
      },

      deleteRow(id) {
        alert(id)
        alert(this.Companyform.id)
        company.deleteCompanyAndCrane(id,this.Companyform.id).then(res =>{

        })
      },
      
      onSubmit() {
        company.updateCompanyById(this.Companyform).then(res =>{

        })
        console.log('submit!');
      }
      
    }
  }
</script>