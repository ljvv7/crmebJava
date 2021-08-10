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
              <div class="upLoadPicBox" @click="modalPicTap('1')" :disabled="isDisabled">
                <div v-if="detailInfo.images" class="pictrue">
                  <el-image :src="detailInfo.images"/>
                </div>
                <div v-else class="upLoad">
                  <i class="el-icon-camera cameraIconfont" />
                </div>
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
          <el-button size="small" type="primary" @click="addNewWeight">新增配重</el-button>
          
          <el-table
            v-loading="listLoading"
            :data="weightData.data"
            style="width: 100%"
            max-height="700">
              <el-table-column
                fixed
                prop="id"
                label="配重ID"
                width="120">
              </el-table-column>
              <el-table-column
                prop="craneid"
                label="车辆ID"
                width="120">
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
                  <el-button type="text" size="small" class="mr10" @click="seeWay(scope.row.id)">详情</el-button>
                </template>
              </el-table-column>
              <el-table-column label="操作" min-width="150" fixed="right" align="center">
                <template slot-scope="scope">
                  <el-button type="text" size="small" class="mr10" @click="editWeight(scope.row)">编辑</el-button>
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
              :total="weightData.total"
              @size-change="handleSizeChange"
              @current-change="pageChange"
            />
          </div>

          <!-- 新增配重弹窗 -->
          <el-dialog title="新增配重信息" :visible.sync="dialogAddVisible">
            <el-form ref="form" :model="weightInfo" label-width="100px" :rules="rules">
              <el-form-item label="车辆ID">
                <el-input v-model="weightInfo.craneid" disabled></el-input>
              </el-form-item>
              <el-form-item label="组合方式" prop="legtype">
                <el-select v-model="weightInfo.legtype" placeholder="请选择">
                 <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="支腿方式" prop="legway">
                <el-input v-model="weightInfo.legway"></el-input>
              </el-form-item>
              <el-form-item label="配重" prop="equipweight">
                <el-input v-model="weightInfo.equipweight"></el-input>
              </el-form-item>
              <el-form-item label="工作范围" prop="workextent">
                <el-input v-model="weightInfo.workextent"></el-input>
              </el-form-item>
              <el-form-item label="是否超起" prop="issuperweight">
                <el-input v-model="weightInfo.issuperweight" placeholder="0:未超起,1:超起"></el-input>
              </el-form-item>
              <el-form-item label="超起配重" prop="superweight">
                <el-input v-model="weightInfo.superweight"></el-input>
              </el-form-item>
              <el-form-item label="后移" prop="backmove">
                <el-input v-model="weightInfo.backmove"></el-input>
              </el-form-item>
              <el-form-item label="备注" prop="remarks">
                <el-input v-model="weightInfo.remarks"></el-input>
              </el-form-item>
              <el-form-item label="性能参数">
                <template>
                  <el-upload
                    class="upload-demo"
                    :action="this.excelUploadUrl"
                    accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
                    :multiple="false"
                    :on-success="handleSuccess"
                    :before-remove="beforeRemove"
                    :on-remove="handleRemove"
                    :show-file-list="false"
                  >
                    <el-button size="small" type="primary">点击上传</el-button>
                    <template #tip>
                      <div class="el-upload__tip">只能上传 xlsx/xls 文件</div>
                    </template>
                  </el-upload>
                  <!-- excel表格 -->
                  <el-table
                    v-loading="excelListLoading"
                    :data="excelList"
                    style="width: 100%"
                    max-height="200">
                    <el-table-column
                      prop="primaryLength"
                      label="臂长"
                      width="120">
                    </el-table-column>
                    <el-table-column
                      prop="radius"
                      label="作业半径"
                      width="120">
                    </el-table-column>
                    <el-table-column
                      prop="way"
                      label="组合方式"
                      width="120">
                    </el-table-column>
                    <el-table-column
                      prop="weight"
                      label="起重量"
                      width="120">
                    </el-table-column>
                  </el-table>
                </template>
              </el-form-item>
            </el-form>
          
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="dialogAddVisible = false">取 消</el-button>
                <el-button type="primary" @click="onSubmitAddWeight">保 存</el-button>
              </span>
            </template>
          </el-dialog>

          <!-- 编辑配重弹窗 -->
          <el-dialog title="编辑配重信息" :visible.sync="dialogEditVisible">
            <el-form ref="form" :model="weightDetail" label-width="100px">
              <el-form-item label="配重ID">
                <el-input v-model="weightDetail.id" disabled></el-input>
              </el-form-item>
              <el-form-item label="车辆ID">
                <el-input v-model="weightDetail.craneid" disabled></el-input>
              </el-form-item>
              <el-form-item label="组合方式">
                <el-select v-model="weightDetail.legtype">
                 <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="支腿方式">
                <el-input v-model="weightDetail.legway"></el-input>
              </el-form-item>
              <el-form-item label="配重">
                <el-input v-model="weightDetail.equipweight"></el-input>
              </el-form-item>
              <el-form-item label="工作范围">
                <el-input v-model="weightDetail.workextent"></el-input>
              </el-form-item>
              <el-form-item label="是否超起">
                <el-input v-model="weightDetail.issuperweight" placeholder="0:未超起,1:超起"></el-input>
              </el-form-item>
              <el-form-item label="超起配重">
                <el-input v-model="weightDetail.superweight"></el-input>
              </el-form-item>
              <el-form-item label="后移">
                <el-input v-model="weightDetail.backmove"></el-input>
              </el-form-item>
              <el-form-item label="备注">
                <el-input v-model="weightDetail.remarks"></el-input>
              </el-form-item>
              <el-form-item label="性能参数">
                <template>
                  <el-upload
                    class="upload-demo"
                    :action="this.excelUploadUrl"
                    accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
                    :multiple="false"
                    :on-success="handleSuccess"
                    :before-remove="beforeRemove"
                    :on-remove="handleRemove"
                    :show-file-list="false"
                  >
                    <el-button size="small" type="primary">点击上传</el-button>
                    <template #tip>
                      <div class="el-upload__tip">只能上传 xlsx/xls 文件</div>
                    </template>
                  </el-upload>
                  <!-- excel表格 -->
                  <el-table
                    v-loading="excelListLoading"
                    :data="excelList"
                    style="width: 100%"
                    max-height="200">
                    <el-table-column
                      prop="primaryLength"
                      label="臂长"
                      width="120">
                    </el-table-column>
                    <el-table-column
                      prop="radius"
                      label="作业半径"
                      width="120">
                    </el-table-column>
                    <el-table-column
                      prop="way"
                      label="组合方式"
                      width="120">
                    </el-table-column>
                    <el-table-column
                      prop="weight"
                      label="起重量"
                      width="120">
                    </el-table-column>
                  </el-table>
                </template>
              </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="dialogEditVisible = false">取 消</el-button>
                <el-button type="primary" @click="onSubmitEditWeight">保 存</el-button>
              </span>
            </template>
          </el-dialog>
          
          <!-- 查看详情弹窗 -->
          <el-dialog title="组合方式" :visible.sync="dialogWayVisible" :before-close="handleClose">
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
                <!-- <el-table-column
                  prop="minorLength"
                  label="次臂长度"
                  width="150">
                </el-table-column> -->
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
                <!-- <el-table-column
                  prop="legtype"
                  label="组合方式"
                  width="100"
                  >
                </el-table-column> -->
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
        excelUploadUrl: 'http://localhost:8101/api/admin/nsl/adminCrane/importExcel',
        listLoading: true,
        excelListLoading: false,
        activeName: 'first',
        dialogAddVisible: false,
        dialogEditVisible: false,
        dialogWayVisible: false,
        isDisabled: this.$route.params.isDisabled==='1'?true:false,
        excelList: [],
        weightInfo: {},
        weightDetail: {},
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
        addWayParams:{
          craneid: null,
          weightid: null,
          list: null
        },
        weightData: {
          data: [],
          total: 0
        },
        wayData: {
          data: [],
          total: 0
        },
        options: [
          {
            value: '1',
            label: '仅主臂'
          }, 
          {
            value: '2',
            label: '主臂+副臂'
          }, 
          {
            value: '3',
            label: '主臂+横臂'
          }, 
          {
            value: '4',
            label: '超起'
          }
        ], 
        
        rules: {
          legtype: [
            {required: true, message: '请选择组合方式', trigger: 'change'}
          ],
          legway: [
            {required: true, message: '请输入支腿方式', trigger: 'blur'}
          ],
          equipweight: [
            {required: true, message: '请输入配重', trigger: 'blur'}
          ],
          workextent: [
            {required: true, message: '请输入工作范围', trigger: 'blur'}
          ],
          issuperweight: [
            {required: true, message: '请输入是否超起0或1', trigger: 'blur'}
          ],
          superweight: [
            {required: true, message: '请输入超起配重', trigger: 'blur'}
          ],
          backmove: [
            {required: true, message: '请输入后移', trigger: 'blur'}
          ],
          remarks: [
            {required: true, message: '请输入备注', trigger: 'blur'}
          ],
        }
        
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
        this.dialogWayVisible = true
      },

      //移除配重的钩子
      rmdelete(id){
        crane.removeWeight(id).then(res=>{  
          this.$message({
            message: '配重移除成功!',
            type: 'success'
          })
        });
        this.getWeightList();
      },
      
      //车辆信息保存的钩子
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

      //组合方式弹窗关闭之前的钩子
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
        this.getWeightList()  
      },

      //车型图片上传
      modalPicTap (tit, num, i) {
        const _this = this;
        if(_this.isDisabled) return;
        this.$modalUpload(function(img) {
          if(tit==='1'&& !num){
            _this.detailInfo.images = img[0].sattDir
          }
        },tit, 'content')
      },

      //新增配重
      addNewWeight(){
        this.excelList = []
        this.weightInfo.craneid=this.detailInfo.id
        this.dialogAddVisible=true
      },

      //新增配重保存的钩子
      onSubmitAddWeight() {
        
        //新增配重
        crane.addWeight(this.weightInfo).then(res=>{
          this.$message({
            message: '配重新增成功!',
            type: 'success'
          });
        })
          
        //新增组合方式
        if(this.excelList.length!=0){
          this.addWayParams.craneid = this.tableFrom.craneid
          this.addWayParams.weightid = null
          this.addWayParams.list = this.excelList
          crane.addWay(this.addWayParams).then(res=>{
            this.$message({
              message: '组合方式新增成功!',
              type: 'success'
            });
          })
        }
        this.dialogAddVisible = false
        this.getWeightList()
      },

      //编辑配重
      editWeight(val){
        this.weightDetail = val
        this.wayTableFrom.cweightid = val.id
        crane.getWayList(this.wayTableFrom).then(res=>{
          this.excelList = res.admWayList
        })
        this.dialogEditVisible = true
      },

      //编辑配重信息保存的钩子
      onSubmitEditWeight(){
        crane.editWeight(this.weightDetail).then(res=>{
          this.$message({
            message: '配重信息修改成功!',
            type: 'success'
          });
        })
        
        //新增组合方式
        this.addWayParams.craneid = this.weightDetail.craneid
        this.addWayParams.weightid = this.weightDetail.id
        this.addWayParams.list = this.excelList
        crane.addWay(this.addWayParams).then(res=>{
          this.$message({
            message: '组合方式新增成功!',
            type: 'success'
          });
        })

        this.dialogEditVisible = false
        this.getWeightList()
      },

      // 上传excel文件的钩子
      beforeRemove(file) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },
      handleRemove(){
        this.excelList = []
      },
      handleSuccess(res){
        this.excelListLoading = true
        this.excelList = res.data.excelList
        this.excelListLoading = false
      },

      //分页
      pageChange(page) {
        this.tableFrom.page = page
        this.getWeightList()
      },
      handleSizeChange(val) {
        this.tableFrom.limit = val
        this.getWeightList()
      },

      
    }
  }
</script>

<style scoped>
  .el-pagination {
    text-align: center;
  }
</style>