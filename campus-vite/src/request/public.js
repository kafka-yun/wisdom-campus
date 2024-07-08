import instance from './request'
import axios from 'axios'
export function reqs(T){
    return instance({
        method:T.method,
        url:T.url,
        data:T.data,
        params:T.params
      })
}
export function res(T){
  return axios({
      method:T.method,
      url:T.url,
      data:T.data,
      params:T.params
    })
}