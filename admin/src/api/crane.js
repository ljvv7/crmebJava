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
  },
  getWeightList(tableFrom){
    return request({
      url: 'admin/nsl/adminCrane/admWeightList',
      method: 'post',
      data: tableFrom
    })
  },
  getWayList(wayTableFrom){
    return request({
      url: 'admin/nsl/adminCrane/admWayList',
      method: 'post',
      data: wayTableFrom
    })
  },
  addCrane(craneInfo){
    return request({
      url: 'admin/nsl/adminCrane/addCrane',
      method: 'post',
      data: craneInfo
    })
  },
  editCrane(detailInfo){
    return request({
      url: 'admin/nsl/adminCrane/editCrane',
      method: 'post',
      data: detailInfo
    })
  },
  removeCrane(id){
    return request({
      url: `admin/nsl/adminCrane/removeCrane/${id}`,
      method: 'get'
    })
  },
  removeWeight(id){
    return request({
      url: `admin/nsl/adminCrane/removeWeight/${id}`,
      method: 'get'
    })
  },
  addWeight(weightInfo){
    return request({
      url: 'admin/nsl/adminCrane/addWeight',
      method: 'post',
      data: weightInfo
    })
  },
  editWeight(weightDetail){
    return request({
      url: 'admin/nsl/adminCrane/editWeight',
      method: 'post',
      data: weightDetail
    })
  },
  addWay(addWayParams){
    return request({
      url: 'admin/nsl/adminCrane/addWay',
      method: 'post',
      data: addWayParams
    })
  },

}
