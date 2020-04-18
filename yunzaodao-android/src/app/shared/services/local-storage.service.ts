import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {
  private storage: any = window.localStorage
  constructor() { }
  get(key: string, defaultValue: any): any {
    let value: any = this.storage.getItem(key)
    try {
      value = JSON.parse(value)
    } catch (error) {
      value = null
    }
    if (value === null && defaultValue) {
      value = defaultValue
    }
    return value
  }
  set(key: string, value: any) {
    this.storage.setItem(key, JSON.stringify(value))
  }
  remove(key: string) {
    this.storage.removeItem(key)
  }
}
export const APP_KEY: string = 'App';
export const USER_KEY: string = 'User';
export const GLOBAL_VARIABLE_KEY = 'Global_Variable';
