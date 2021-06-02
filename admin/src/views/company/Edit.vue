<template>
  <div class="divBox">
    <el-card class="box-card">
      <el-tabs v-model="activeName" @tab-click="seachList">
        <el-tab-pane label="公司信息" name="first">
          <el-form ref="form" :model="Companyform" label-width="80px">
            <el-form-item label="公司ID">
              <el-input v-model="Companyform.id"></el-input>
            </el-form-item>
            <el-form-item label="公司名称">
              <el-input v-model="Companyform.name"></el-input>
            </el-form-item>
            <el-form-item label="省-市-区">
              <el-input v-model="Companyform.area"></el-input>
            </el-form-item>
            <el-form-item label="公司地址">
              <el-input v-model="Companyform.adds"></el-input>
            </el-form-item>
            <el-form-item label="联系人">
              <el-input v-model="Companyform.atten"></el-input>
            </el-form-item>
            <el-form-item label="联系电话">
              <el-input v-model="Companyform.phone"></el-input>
            </el-form-item>

            <el-form-item label="公司状态">
              <el-select v-model="Companyform.status" placeholder="请选择活动区域">
                <el-option label="已审核" value="yishenhe"></el-option>
                <el-option label="待审核" value="daishenhe"></el-option>
                <el-option label="已下架" value="yixiajia"></el-option>
                <el-option label="待下架" value="daixiajia"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="公司简介">
              <el-input type="textarea" v-model="Companyform.introduce"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">保存</el-button>
              <el-button type="primary" @click="onadd">新增</el-button>
               <el-button type="primary" @click="authmsg">获取公司认证提示</el-button>
                <el-button type="primary" @click="admin">我的</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="车辆信息" name="second">
          <el-table
            :data="companyList"
            style="width: 100%"
            max-height="700">
              <el-table-column
                fixed
                prop="id"
                label="ID"
                width="150">
              </el-table-column>
              <el-table-column
                prop="cbrands"
                label="车辆品牌"
                width="120">
              </el-table-column>
              <el-table-column
                prop="name"
                label="车辆型号"
                width="180">
              </el-table-column>
              <el-table-column
                prop="maxweight"
                label="最大轻重量"
                width="120">
              </el-table-column>
              <el-table-column label="公司图片" min-width="80" prop="images">
                <div class="demo-image__preview">
                  <el-image 
                    style="width: 50px; height: 50px"
                    :src="url" 
                    :preview-src-list="srcList">
                  </el-image>
                </div>
              </el-table-column>
              <el-table-column
                prop="introduce"
                label="车辆简介"
                width="300"
                >
              </el-table-column>
              <el-table-column
                fixed="right"
                label="操作"
                width="120">
                  <template slot-scope="scope">
                      <el-button
                        @click.native.prevent="deleteRow(scope.$index, tableData)"
                        type="text"
                        size="small">
                        移除
                      </el-button>
                  </template>
              </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>

    </el-card>
  </div>
</template>


<script>
import company from '@/api/company'
import { param } from '@/utils'

  export default {
    data() {
      return {
        Companyform: {
          in: '',
          name: '',
          area: '',
          adds: '',
          atten: '',
          phone: '',
          status:'',
          introduce: '',
          region: '',
        },
        tableFrom: {
          companyid: 1,
          pageindex: 0,
          pagesize: 20
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
      }
    },

    created(){
        this.getList()
        
    },
    methods: {
      getList(){
          this.listLoading = true
          company.getCompanyById(this.tableFrom).then(res =>{
            // debugger
           this.listLoading = false
           this.Companyform = res.cranedetail

           this.companyList = res.companylist
           this.url = this.companyList.images
           if(res.cranedetail.status=='10'){
            this.Companyform.status = 'yishenhe'
           }else if(res.cranedetail.status == '20'){
              this.Companyform.status = 'yixiajia'
           }
           
           }).catch(res => {
            this.listLoading = false
          })
      },
      seachList(tab, event){
          this.tableFrom.page = 1
          this.getList()
      },

      deleteRow(index, rows) {
        rows.splice(index, 1);
      },
      
      onSubmit() {
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
        alert(adminId)
          company.getAdmin(userId).then(res => {

          }).catch(res => {

          })
      }
    }
  }
</script>