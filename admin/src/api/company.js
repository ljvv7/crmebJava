import request from '@/utils/request'

/**
 * @param tableFrom 
 * @returns 
 */
export default {

  getAallCompanyApi(tableFrom){
    return request({
      url: '/admin/company/getAllCompany',
      method: 'post',
      data: tableFrom
    })
  },

  productExcelApi(params) {
    return request({
      url: `/admin/export/excel/product`,
      method: 'get',
      params
    })
  }
}