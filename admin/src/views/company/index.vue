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
         <!-- <el-button size="small" @click="exportExcel">导出</el-button> -->
      </div>
      <el-table
        v-loading="listLoading"
        :data="tableData.data"
        id="outTable"
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
                :src="scope.row.logo"
                :preview-src-list="[scope.row.logo]"
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
        <el-table-column label="状态" min-width="70">
          <template slot-scope="scope">
            <!-- 这里使用===还是==得看返回值是什么类型的-->
            <span v-if="scope.row.status == '10'">待审核</span>
            <span v-if="scope.row.status == '20'">已审核</span>
            <span v-if="scope.row.status == '30'">下架</span>
            <span v-if="scope.row.status == '40'">上架</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" min-width="150" fixed="right" align="center">
          <template slot-scope="scope">
              <el-button type="primary" @click="chakan(scope.row.id)">查看</el-button>
              <!-- <el-button type="primary" @click="xiangqing">公司详情</el-button>  -->
               <el-select v-model="scope.row.value" min-width="150" @change="selectEditAndRemove(scope.row.id,scope.row.value)" placeholder="更多">
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
          <!-- <el-button type="primary" @click="dialogVisible = false">确 定</el-button> -->
          <el-button type="primary" @click=updateradio()>确 定</el-button>
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
import FileSaver from 'file-saver';
import XLSX from 'xlsx';
import router from '@/router'
export default {
  name: 'ProductList',
    provide () {    //父组件中通过provide来提供变量，在子组件中通过inject来注入变量。                                             
      return {
        reload: this.reload                                              
      }
  },
  data() {
    return {
      isRouterAlive: true ,                   //控制视图是否显示的变量
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
      companyId: '',
      categoryList: [],
      merCateList: [],
      objectUrl: process.env.VUE_APP_BASE_API,
      dialogVisible: false,
      radio: 20,
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
        value: 'yishenhe'
        
    }
  },


  created() {   //页面渲染之前执行
    this.getList()
  },

  methods: {
    reload () {
      this.isRouterAlive = false;            //先关闭，
      this.$nextTick(function () {
      this.isRouterAlive = true;         //再打开
      }) 
    },
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

    updateradio(){
        this.dialogVisible = false
        const status = this.radio
        
        company.updateStatusByid(this.companyId,status).then(res =>{
        })

        
    },
    selectEditAndRemove(id,value){
        if(value == 'zhuangtaishenzhi'){
          this.companyId = id
          this.dialogVisible = true
        }else if(value == 'bianji'){
          this.$router.push({
            path: 'companyDetails',
            query : {
              id: id
            }
          })
        }else if(value == 'delete'){
            this.$confirm('删除公司后将不可恢复, 请慎重操作?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {
              // debugger
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
         this.reload()
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
    exportExcel() {
      var xlsxParam = { raw: true };//转换成excel时，使用原始的格式
      var wb = XLSX.utils.table_to_book(document.querySelector("#outTable"),xlsxParam);//outTable为列表id
      var wbout = XLSX.write(wb, {
          bookType: "xlsx",
          bookSST: true,
          type: "array"
      });
      try {
          FileSaver.saveAs(
          new Blob([wbout], { type: "application/octet-stream;charset=utf-8" }),
          "companyList.xlsx"
          );
      } catch (e) {
          if (typeof console !== "undefined") console.log(e, wbout);
      }
      return wbout;
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
