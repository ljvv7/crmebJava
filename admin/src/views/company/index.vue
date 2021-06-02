<template>
  <div class="divBox">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <div class="container">
          <el-form inline size="small">
            <el-form-item label="公司搜索：">
              <el-input v-model="tableFrom.code" placeholder="请输入公司名称或者手机号" class="selWidth" size="small" clearable>
                <el-button slot="append" icon="el-icon-search" @click="seachList" size="small"/>
              </el-input>
            </el-form-item>
          </el-form>
        </div>
         <el-button size="small" @click="exports">导出</el-button>
      </div>
      <el-table
        v-loading="listLoading"
        :data="tableData.data"
        style="width: 100%"
        size="mini"
        highlight-current-row
      >
        <el-table-column
          prop="id"
          label="ID"
          min-width="50"
        />
        <el-table-column label="Logo" min-width="80">
          <template slot-scope="scope">
            <div class="demo-image__preview">
              <el-image
                style="width: 36px; height: 36px"
                :src="scope.row.image"
                :preview-src-list="[scope.row.image]"
              />
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="name"
          label="公司名称"
          min-width="150"
        />
        <el-table-column
          prop="area"
          label="省-市-区"
          min-width="90"
        />
        <el-table-column
          prop="adds"
          label="地址"
          min-width="150"
        />
        <el-table-column
          prop="atten"
          label="联系人"
          min-width="90"
        />
        <el-table-column
          prop="phone"
          label="联系电话"
          min-width="90"
        />
        <el-table-column
          prop="status"
          label="状态"
          min-width="70"
        />
        <el-table-column label="操作" min-width="150" fixed="right" align="center">
          <!-- <template slot-scope="scope"> -->
            <el-button type="primary" @click="chakan">查看</el-button>
            <el-button type="primary" @click="xiangqing">公司详情</el-button>
            <!-- <router-link :to="{path: '/store/list/creatProduct/' + scope.row.id}">
              <el-button type="text" size="small" class="mr10" v-if="tableFrom.type !== '5'">查看</el-button>
            </router-link> -->
            <!-- <el-button type="text" size="small" @click="handleDelete(scope.row.id, tableFrom.type)">{{ tableFrom.type === '5' ? '查看' : '加入回收站' }}</el-button> -->
          <!-- </template> -->
        </el-table-column>
      </el-table>
      <div class="block">
        <el-pagination
          :page-sizes="[20, 40, 60, 80]"
          :page-size="tableFrom.pagesize"
          :current-page="tableFrom.pageindex"
          layout="total, sizes, prev, pager, next, jumper"
          :total="tableData.total"
          @size-change="handleSizeChange"
          @current-change="pageChange"
        />
      </div>
    </el-card>
  </div>
</template>

<script>

// import { getAallCompanyApi,productExcelApi} from '@/api/company'
import company from '@/api/company'
import router from '@/router'
export default {
  name: 'ProductList',
  data() {
    return {
      // roterPre: roterPre,
      listLoading: true,
      tableData: {
        data: [],
        total: 0
      },
      tableFrom: {
        pageindex: 1,
        pagesize: 20,
        cateId: '',
        code: '',
        type: '1'
      },
      categoryList: [],
      merCateList: [],
      objectUrl: process.env.VUE_APP_BASE_API,
      dialogVisible: false
    }
  },

  created() {   //页面渲染之前执行
    this.getList()
  },

  methods: {

    chakan(){
        company.testWXAppApi()
        .then(
          res => {
            this.tableData.data = res.map
          }
        ).catch(res =>{

        })
    },

    xiangqing(){
        this.$router.push('@/views/company/Edit')
    },
    
    getList() {
      this.listLoading = true
      company.getAallCompanyApi(this.tableFrom).then(res => {
        this.tableData.data = res.list
        this.tableData.total = res.total
        this.listLoading = false
      }).catch(res => {
        this.listLoading = false
      })
    },
    seachList() {
      this.tableFrom.pageindex = 1
      this.getList()
    },
    // 复制
    onCopy(){
      this.dialogVisible = true
    },
    // 导出
    exports () {
      company.productExcelApi({cateId:this.tableFrom.cateId,keywords: this.tableFrom.keywords, type:this.tableFrom.type}).then((res) => {
        window.open(res.fileName)
      })
    //  window.open(this.objectUrl + 'admin/export/excel/product?type=1&Authori-zation=' + getToken())
    },

    pageChange(pageindex) {
      this.tableFrom.pageindex = pageindex
      this.getList()
    },
    handleSizeChange(val) {
      this.tableFrom.limit = val
      this.getList()
    },
  }
}
</script>

<style scoped lang="scss">
  .taoBaoModal{
  //  z-index: 3333 !important;
  }
  .demo-table-expand{
    /deep/ label{
      width: 82px;
    }
  }
  .demo-table-expand{
    /deep/ .el-form-item__content{
      width: 77%;
    }
  }
  .selWidth{
    width: 350px !important;
  }
  .seachTiele{
    line-height: 30px;
  }
</style>
