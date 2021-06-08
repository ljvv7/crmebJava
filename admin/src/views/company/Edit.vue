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
              <el-select v-model="value" placeholder="请选择状态">
                 <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
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
              <el-button type="primary" @click="shoucang">收藏</el-button>
              <el-button type="primary" @click="addshoucang">添加收藏</el-button>
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
                label="车辆ID"
                width="100">
              </el-table-column>
              <el-table-column
                prop="cbrands"
                label="品牌ID"
                width="100">
              </el-table-column>
              <el-table-column
                prop="name"
                label="车辆型号"
                width="100">
              </el-table-column>
              <el-table-column
                prop="maxweight"
                label="最大起重量"
                width="100">
              </el-table-column>
              <el-table-column label="车型图片" min-width="100" prop="images">
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
                width="200"
                >
              </el-table-column>
              <el-table-column
                fixed="right"
                label="操作"
                width="120">
                  <template slot-scope="scope">
                  <el-button type="danger" round
                    @click="deleteRow(scope.row.id)"
                   
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
          statu:'',
          introduce: '',
          region: '',
        },
        tableFrom: {
          code: this.$route.query.id,
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

        options: [{
          value: 'yishenhe',
          label: '已审核'
        }, {
          value: 'daishenhe',
          label: '待审核'
        }, {
          value: 'yixiajia',
          label: '已下架'
        }, {
          value: 'xiajia',
          label: '下架'
        }],
        value: 'yishenhe',
        tableID: this.$route.query.id
      }

    },

    created(){
        this.getList(this.tableID)
        
    },
    methods: {
      getList(tableID){
          this.listLoading = true
          this.tableFrom.code = tableID
          alert(this.tableFrom.code)
          company.getCompanyById(this.tableFrom).then(res =>{
           this.listLoading = false
           this.Companyform = res.cranedetail
           this.companyList = res.companylist
           this.url = this.companyList.images
           if(res.cranedetail.status=='10'){
            this.value = 'yishenhe'
           }else if(res.cranedetail.status == '20'){
              this.Companyform.statu = 'yixiajia'
           }
           
           }).catch(res => {
            this.listLoading = false
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