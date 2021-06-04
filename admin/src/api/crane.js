import request from '@/utils/request'

/**
 * @param tableFrom
 * @returns
 */
export default {

  getBrandList(){
    return request({
      url: 'admin/nsl/crane/brandlist',
      method: 'post'
    })
  },
  getCraneList(tableFrom){
    return request({
      url: 'admin/nsl/crane/cranelist',
      method: 'post',
      data: tableFrom
    })
  }

}
