import request from '@/utils/request'

export function get(data) {
  return request({
    url: 'api/userSysVal',
    method: 'get',
    params: data
  })
}

export function add(data) {
  return request({
    url: 'api/userSysVal',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/userSysVal/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/userSysVal',
    method: 'put',
    data
  })
}

export default { add, edit, del }
