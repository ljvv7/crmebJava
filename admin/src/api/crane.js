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

  getBrandList(){
    return request({
      url: 'admin/nsl/crane/brandlist',
      method: 'post'
    })
  },
  getcraneList(tableFrom){
    return request({
      url: 'admin/nsl/crane/cranelist',
      method: 'post',
      data: tableFrom
    })
  }

}
