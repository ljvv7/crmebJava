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
          <template slot-scope="scope">
              <el-button type="primary" @click="chakan(scope.row.id)">查看</el-button>
              <!-- <el-button type="primary" @click="xiangqing">公司详情</el-button>  -->
               <el-select v-model="value" placeholder="更多" min-width="150" @change="selectEditAndRemove(scope.row.id)">
                 <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
               </el-option>
              </el-select>   
          </template>
        </el-table-column>
      </el-table>
      
      
      <el-dialog
        title="状态编辑"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">

        <template>
          <el-radio-group v-model="radio">
            <el-radio :label="20">已审核</el-radio>
            <el-radio :label="40">上架</el-radio>
            <el-radio :label="30">下架</el-radio>
          </el-radio-group>
        </template>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
      </el-dialog>

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
        code: '',
      },
      categoryList: [],
      merCateList: [],
      objectUrl: process.env.VUE_APP_BASE_API,
      dialogVisible: false,

      options: [{
          value: 'yishenhe',
          label: '更多'
        }, {
          value: 'zhuangtaishenzhi',
          label: '状态设置'
        }, {
          value: 'bianji',
          label: '编辑'
        }, {
          value: 'delete',
          label: '删除'
        }],
        value: 'yishenhe',
        dialogVisible: false,
        radio: 20
    }
  },

  created() {   //页面渲染之前执行
    this.getList()
  },

  methods: {

    chakan(id){
        this.$router.push({
          path: 'companyDetails',
          query: {
		      	id: id
		      }
        })
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    selectEditAndRemove(id){
        if(this.value == 'zhuangtaishenzhi'){
          this.dialogVisible = true
          const status = this.radio
          this.company.updateStatusByid(id,status).then(res =>{
          })
        }else if(this.value == 'bianji'){
          this.$router.push({
            path: 'companyDetails',
            query : {
              id: id
            }
          })
        }else if(this.value == 'delete'){
            this.$confirm('删除公司后将不可恢复, 请慎重操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          company.deleteCompanyById(id).then(res =>{
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.getList()
          }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });  
          this.getList()        
        });
        
        })
        }
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
      // this.changePageCoreRecordData()
      this.tableFrom.pageindex = pageindex
      this.getList()
    },
    handleSizeChange(val) {
      // this.changePageCoreRecordData()
      this.tableFrom.pagesize = val
      this.getList()
    }
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
