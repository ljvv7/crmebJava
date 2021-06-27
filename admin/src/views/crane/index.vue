<template>
  <div class="divBox">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <div class="container">
          <el-form inline size="small">
            <el-form-item label="品牌搜索：">
              <el-select v-model="brandList.name" placeholder="全选" @change="getOListById">
                <el-option
                  v-for="item in brandList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="车型搜索：">
              <el-input v-model="tableFrom.craneName" placeholder="请输入车辆型号" class="selWidth" size="small" clearable></el-input>
            </el-form-item>
            <el-form-item>
              <el-button size="small" type="primary" @click="seachList">搜索</el-button>
              <el-button size="small" type="primary" @click="add">新增</el-button>
              <!-- <el-button size="small" @click="exportExcel">导出</el-button> -->
            </el-form-item>
          </el-form>
        </div>
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
          label="车辆ID"
          min-width="100"
          align="center"
        />
        <el-table-column
          prop="brandName"
          label="车辆品牌"
          min-width="100"
          align="center"
        />
        <el-table-column
          prop="name"
          label="车辆型号"
          min-width="100"
          align="center"
        />
        <el-table-column
          prop="maxweight"
          label="最大起重量"
          min-width="100"
          align="center"
        />
        <el-table-column label="车型图片" min-width="100" align="center">
          <template slot-scope="scope">
            <div class="demo-image__preview">
              <el-image
                style="width: 36px; height: 36px"
                :src="scope.row.images"
                :preview-src-list="[scope.row.images]"
              />
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="introduce"
          label="车辆简介"
          min-width="150"
          align="center"
          :show-overflow-tooltip="true"
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
          :page-sizes="[10, 20, 30, 40]"
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
import FileSaver from 'file-saver';
import XLSX from 'xlsx';
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
      objectUrl: process.env.VUE_APP_BASE_API,
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
      // debugger
      crane.getCraneList(this.tableFrom).then(res =>{
        this.tableData.data = res.admCraneList
        // console.log(res.admCraneList)
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

    add(){
      this.$router.push({
        path: 'addCrane',
      })
    },

    seeDetail(id) {
      this.$router.push({
        path: 'craneDetails',
        query: {
          id: id
		    }
      })
    },

    rmdelete(id){
      crane.removeCrane(id).then(res=>{  
        this.$message({
          message: '车辆移除成功!',
          type: 'success'
        })
      });
      this.getAdmCraneList();
    },

    // 导出到本地
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
          "craneList.xlsx"
          );
      } catch (e) {
          if (typeof console !== "undefined") console.log(e, wbout);
      }
      return wbout;
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
