import request from '@/utils/request'

/**
 * @param tableFrom
 * @returns
 */
export default {

  getBrandList(){
    return request({
      url: 'admin/nsl/adminCrane/admBrandList',
      method: 'post'
    })
  },
  getCraneList(tableFrom){
    return request({
      url: 'admin/nsl/adminCrane/admCraneList',
      method: 'post',
      data: tableFrom
    })
  }

}
