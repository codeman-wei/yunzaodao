import request from '@/utils/request'

export function getSignData(id) {
  return request({
    url: 'api/studentCourseSign/sign' + '?id=' + id,
    method: 'get'
  })
}

export function updateSign(ids) {
  return request({
    url: 'api/studentCourseSign/sign',
    method: 'put',
    data: ids
  })
}

export function add(data) {
  return request({
    url: 'api/signHistory',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/signHistory/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/signHistory',
    method: 'put',
    data
  })
}

export default { add, edit, del }
