import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  public config:any={
    //域名：
    domain:'http://47.112.226.54:8010',  // 服务器
    // domain:'http://localhost:8000',  // 本地后端
  }

  private header = new HttpHeaders({'content-type': 'application/json'});
  constructor(public http:HttpClient) { }

  //封装了一个get请求
  ajaxGet(url:String) {
    var api=this.config.domain + url;
    return new Promise((resolve, reject) => {
      this.http.get(api,{headers : this.header}).subscribe((response) => {
        resolve(response);
      }, (err) => {
        reject(err);
      })
    })
  }
  //封装了一个post请求 
  ajaxPost(url:String, json:Object) {
    var api = this.config.domain + url;
    return new Promise((resove, reject) => {
      this.http.post(api, json, {headers : this.header}).subscribe((response) => {
        resove(response);
      }, (error) => {
        reject(error);
      })
    })
  }
  ajaxPut(url:String, json:Object) {
    var api = this.config.domain + url;
    return new Promise((resove, reject) => {
      this.http.put(api, json, {headers : this.header}).subscribe((response) => {
        resove(response);
      }, (error) => {
        reject(error);
      })
    })
  }
}
