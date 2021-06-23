<template>
  <div class="div-box">
    <el-card class="box-card">
      <el-tabs v-model="activeName" @tab-click="seachList">
        <el-tab-pane label="车辆信息" name="first">
          <el-form ref="form" :model="detailInfo" label-width="100px">
            <el-form-item label="车辆ID">
              <el-input v-model="detailInfo.id" disabled></el-input>
            </el-form-item>
            <el-form-item label="车辆品牌">
              <el-input v-model="detailInfo.brandName" disabled></el-input>
            </el-form-item>
            <el-form-item label="车型图片">
              <div @click="modalPicTap('1')" :disabled="isDisabled">
                <el-image style="width: 100px; height: 100px" :src="detailInfo.image"/>
              </div>
              <!-- <el-image style="width: 100px; height: 100px" :src="detailInfo.image"/> -->
            </el-form-item>
            <el-form-item label="车辆型号">
              <el-input v-model="detailInfo.name"></el-input>
            </el-form-item>
            <el-form-item label="最大起重量">
              <el-input v-model="detailInfo.maxweight"></el-input>
            </el-form-item>
            <el-form-item label="指导价格">
              <el-input v-model="detailInfo.guidePrice"></el-input>
            </el-form-item>
            <el-form-item label="车辆简介">
              <el-input type="textarea" :rows="3" v-model="detailInfo.introduce"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" :plain="true" @click="onSubmitCrane">保存</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="配重列表" name="second">
          <el-button size="small" type="primary" @click="dialogFormVisible=true,weightInfo.craneid=detailInfo.id">新增配重</el-button>
          
          <el-dialog title="配重信息" :visible.sync="dialogFormVisible">
            <el-form ref="form" :model="weightInfo" label-width="100px">
              <el-form-item label="车辆ID">
                <el-input v-model="weightInfo.craneid" disabled></el-input>
              </el-form-item>
              <el-form-item label="组合方式">
                <el-input v-model="weightInfo.legtype" placeholder="1:仅主臂,2:主臂+副臂,3:主臂+横臂,4:超起"></el-input>
              </el-form-item>
              <el-form-item label="支腿方式">
                <el-input v-model="weightInfo.legway"></el-input>
              </el-form-item>
              <el-form-item label="配重">
                <el-input v-model="weightInfo.equipweight"></el-input>
              </el-form-item>
              <el-form-item label="工作范围">
                <el-input v-model="weightInfo.workextent"></el-input>
              </el-form-item>
              <el-form-item label="是否超起">
                <el-input v-model="weightInfo.issuperweight" placeholder="0:未超起,1:超起"></el-input>
              </el-form-item>
              <el-form-item label="超起配重">
                <el-input v-model="weightInfo.superweight"></el-input>
              </el-form-item>
              <el-form-item label="后移">
                <el-input v-model="weightInfo.backmove"></el-input>
              </el-form-item>
              <el-form-item label="性能参数上传">
                <el-upload
                  class="upload"
                  :show-file-list="false"
                  action=""
                  :multiple="false"
                  accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                  :on-change="importExcel"
                  :limit="1"
                >
                  <el-button size="small" type="primary">上传</el-button>
                </el-upload>
              </el-form-item>
              <el-form-item label="备注">
                <el-input v-model="weightInfo.remarks"></el-input>
              </el-form-item>

              <el-form-item>
                <el-button type="primary" :plain="true" @click="onSubmitWeight">保存</el-button>
              </el-form-item>
            </el-form>
          </el-dialog>
          
          <el-table
            v-loading="listLoading"
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
              <el-table-column label="组合方式" width="150">
                <template slot-scope="scope">
                  <!-- 这里使用===还是==得看返回值是什么类型的-->
                  <span v-if="scope.row.legtype == '1'">仅主臂</span>
                  <span v-if="scope.row.legtype == '2'">主臂+副臂</span>
                  <span v-if="scope.row.legtype == '3'">主臂+横臂</span>
                  <span v-if="scope.row.legtype == '4'">超起</span>
                </template>
              </el-table-column>
              <el-table-column
                prop="legway"
                label="支腿方式"
                width="100">
              </el-table-column>
              <el-table-column
                prop="equipweight"
                label="配重"
                width="100">
              </el-table-column>
              <el-table-column
                prop="workextent"
                label="工作范围"
                width="100"
                >
              </el-table-column>
              <el-table-column label="是否超起" width="100">
                <template slot-scope="scope">
                  <el-checkbox v-if="scope.row.issuperweight == '1'" checked disabled></el-checkbox>
                  <el-checkbox v-if="scope.row.issuperweight == '0'" disabled></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column
                prop="superweight"
                label="超起配重"
                width="100"
                >
              </el-table-column>
              <el-table-column
                prop="backmove"
                label="后移"
                width="100"
                >
              </el-table-column>
              <el-table-column label="性能参数" min-width="100" fixed="right" align="center">
                <template slot-scope="scope">
                  <el-button type="text" size="small" class="mr10" @click="seeWay(scope.row.id),dialogTableVisible=true">详情</el-button>
                </template>
              </el-table-column>
              <el-table-column label="操作" min-width="100" fixed="right" align="center">
                <template slot-scope="scope">
                  <el-button type="text" size="small" class="mr10" @click="rmdelete(scope.row.id)">移除</el-button>
                </template>
              </el-table-column>
          </el-table>
          <el-dialog title="组合方式" :visible.sync="dialogTableVisible" :before-close="handleClose">
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
                  label="作业半径"
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
          </el-dialog>
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
        dialogFormVisible: false,
        dialogTableVisible: false,
        isDisabled: this.$route.params.isDisabled==='1'?true:false,
        weightInfo: {},
        detailInfo: {},
        tableFrom: {
          craneid: this.$route.query.id,
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
        listLoading: true,
        activeName: 'first',
        url: this.images,
        srcList: this.images,
      }

    },

    created(){
        this.getDetail()
        // this.getWeightList()
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
          this.listLoading = false
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
        if(this.activeName = 'second'){
          this.getWeightList()
        }
      },

      seeWay(id) {
        this.wayTableFrom.cweightid = id
        this.getWayList()
      },

      rmdelete(id){
        crane.removeWeight(id).then(res=>{  
          this.$message({
            message: '配重移除成功!',
            type: 'success'
          })
        });
        this.getWeightList();
      },
      
      onSubmitCrane(){
        crane.editCrane(this.detailInfo).then(res =>{
          this.$message({
            message: '车辆信息修改成功!',
            type: 'success'
          });
        })
        this.$router.push({
          path: 'craneQuery'
        })
      },

      onSubmitWeight() {
        crane.addWeight(this.weightInfo).then(res=>{
          this.$message({
            message: '配重新增成功!',
            type: 'success'
          });
        })
        this.getWeightList()
        this.dialogFormVisible = false
      },

      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
        this.getWeightList()  
      },

      modalPicTap (tit, num, i) {
        const _this = this;
        if(_this.isDisabled) return;
        this.$modalUpload(function(img) {
          if(tit==='1'&& !num){
            _this.detailInfo.images = img[0].sattDir
          }
        },tit, 'content')
      },
      
    }
  }
</script>