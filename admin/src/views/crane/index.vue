<template>
  <div class="divBox">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <div class="container">
          <el-form inline size="small">
            <el-form-item label="品牌搜索：">
              <el-select v-model="brandList.name" placeholder="请选择" @change="getOListById">
                <el-option
                  v-for="item in brandList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="车型搜索：">
              <el-input v-model="tableFrom.craneName" placeholder="请输入车辆型号" class="selWidth" size="small" clearable>
                <el-button slot="append" icon="el-icon-search" @click="seachList" size="small"/>
              </el-input>
            </el-form-item>
            <el-form-item>
              <!-- <el-button size="small" @click="seachList">搜索</el-button> -->
              <el-button size="small" @click="add">新增</el-button>
              <el-button size="small" @click="exports">导出</el-button>
              <!-- <el-button slot="append" icon="el-icon-search" @click="seachList" size="small"/> -->
            </el-form-item>

          </el-form>
        </div>

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
          label="车辆ID"
          min-width="100"
        />
        <el-table-column
          prop="cbrands"
          label="品牌名称"
          min-width="100"
        />
        <el-table-column
          prop="name"
          label="车辆型号"
          min-width="100"
        />
        <el-table-column
          prop="maxweight"
          label="最大起重量"
          min-width="100"
        />
        <el-table-column label="车型图片" min-width="100">
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
          prop="remarks"
          label="车辆简介"
          min-width="150"
        />
        <el-table-column label="查看" min-width="100" fixed="right" align="center">
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
      <div class="block">
        <el-pagination
          :page-sizes="[5, 10, 15, 20]"
          :page-size="tableFrom.limit"
          :current-page="tableFrom.page"
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
import crane from '@/api/crane'
export default {
  name: 'CraneList',
  data() {
    return {
      // roterPre: roterPre,
      listLoading: true,
      tableData: {
        data: [],
        total: 0
      },
      brandList: [],
      tableFrom: {
        cbrandid: null,
        craneid: null,
        craneName: '',
        page: 1,
        limit: 10
      },
      categoryList: [],
      merCateList: [],
      objectUrl: process.env.VUE_APP_BASE_API,
      dialogVisible: false
    }
  },

  created() {   //页面渲染之前执行
    // this.getList()
    this.getBrandList()
    this.getAdmCraneList()
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
      this.tableFrom.cbrandid = cbrandid
      this.getAdmCraneList()
    },
    
    getAdmCraneList(){
      this.listLoading = true
      crane.getCraneList(this.tableFrom).then(res =>{
        this.tableData.data = res.admCraneList
        this.tableData.total = res.count
        this.listLoading = false
      }).catch(res => {
        this.listLoading = false
      })
    },

    seachList() {
      // this.tableFrom.page = 1
      this.getAdmCraneList()
    },

    seeDetail(id) {
      this.$router.push({
        path: 'craneDetails',
        query: {
          id: id
		    }
      })
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

    pageChange(page) {
      this.tableFrom.page = page
      this.getAdmCraneList()
    },
    handleSizeChange(val) {
      this.tableFrom.limit = val
      this.getAdmCraneList()
    },
  }
}
</script>

<style scoped lang="scss">
  .taoBaoModal{
   z-index: 3333 !important;
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
