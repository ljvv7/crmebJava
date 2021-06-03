import request from '@/utils/request'

/**
 * @param tableFrom 
 * @returns 
 */
export default {

  getAallCompanyApi(tableFrom){
    return request({
      url: '/admin/nsl/company/getAllCompany',
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

  getCompanyById(tableFrom){
    return request({
      url: '/admin/nsl/company/getdetail',
      method: 'post',
      data: tableFrom
    })
  },
  //更新公司
  updateCompanyById(tableFrom){
    return request({
      url: '/admin/nsl/company/updateCompanyById',
      method: 'post',
      data: tableFrom
    })
  },

  /**
   * 微信小程序首页
   * @returns 
   */
  testWXAppApi(){
    return request({
      url: '/admin/nsl/index',
      method: 'post',
    })
  },

  wxappAddCompanyApi(tableFrom){
    return request({
      url: '/admin/nsl/company/add',
      method: 'post',
      data: tableFrom
    }) 
  },
  getdCompanyAuthmsgApi(){
    return request({
      url: '/admin/nsl/company/authmsgv1',
      method: 'post'
    }) 
  },
  getAdmin(userId){
    return request({
      url: `/admin/nsl/user/index/${userId}`,
      method: 'post',
    }) 
  },

  deleteCompanyAndCrane(craneId,companyId){
    return request({
      url: `/admin/nsl/company/deleteCompanyAndCrane/${companyId}/${craneId}`,
      method: 'post',
    }) 
  }
}