import request from '@/utils/request'
import qs from 'qs'

export function initData(url, params) {
  // console.log(url + '?' + qs.stringify(params, { indices: false }))
  return request({
    url: url + '?' + qs.stringify(params, { indices: false }),
    method: 'get'
  })
}

export function download(url, params) {
  return request({
    url: url + '?' + qs.stringify(params, { indices: false }),
    method: 'get',
    responseType: 'blob'
  })
}
