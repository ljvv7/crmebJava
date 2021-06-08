<template>
  <div class="divBox">
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
                  <el-button type="text" size="small" class="mr10" @click="seeDetail(scope.row.id)">详情</el-button>
                </template>
              </el-table-column>
              <el-table-column label="操作" min-width="100" fixed="right" align="center">
                <template slot-scope="scope">
                  <el-button type="text" size="small" class="mr10" @click="rmdelete(scope.row.id)">移除</el-button>
                </template>
              </el-table-column>
          </el-table>
          <!-- <div class="block">
            <el-pagination
              :page-sizes="[5, 10, 15, 20]"
              :page-size="tableFrom.limit"
              :current-page="tableFrom.page"
              layout="total, sizes, prev, pager, next, jumper"
              :total="tableData.total"
              @size-change="handleSizeChange"
              @current-change="pageChange"
            />
          </div> -->
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
          cbrandid: null,
          craneid: this.$route.query.id,
          craneName: '',
          page: 1,
          limit: 10
        },
        weightData: {
          data: [],
          total: 0
        },
        companyList: [{
          id: '',
          cbrands: '',
          name: '',
          maxweight: '',
          images: '',
          introduce: ''
        }],
        activeName: 'first',
        url: this.images,
        srcList: this.images,
        tableID: this.$route.query.id
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

      seachList(tab, event){
          this.tableFrom.page = 1
          this.getList()
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
      },
      /**
       * 新增公司
       */
      onadd(){
        this.Companyform.status = 10
        company.wxappAddCompanyApi(this.Companyform).then(res => {

        }).catch(res => {

        })
      },
      //获取公司认证提示
      authmsg(){
        company.getdCompanyAuthmsgApi().then(res => {

        }).catch(res => {

        })
      },
      //我的
      admin(){
        const userId = 1
          company.getAdmin(userId).then(res => {

          })
      },
      //收藏
      shoucang(){
        const userId = 1
        company.getAllConnection(userId).then(res =>{

        })
      },
      //添加收藏
      addshoucang(){
        const userid = 2
        const craneid = 1
        company.addCollection(userid,craneid).then(res =>{

        })
      }
    }
  }
</script>