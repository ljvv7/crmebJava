import request from '@/utils/request'

/**
 * @param tableFrom
 * @returns
 */
export default {

  getAallCompanyApi(tableFrom){
    return request({
      url: 'admin/nsl/crane/getdetail',
      method: 'post',
      data: tableFrom
    })
  },

  productExcelApi(params) {
    return request({
      url: '/admin/export/excel/product',
      method: 'get',
      params
    })
  },

  testWXAppApi(){
    return request({
      url: '/admin/nsl/index',
      method: 'post',
    })
  }
}
