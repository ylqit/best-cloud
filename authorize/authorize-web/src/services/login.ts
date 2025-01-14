// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';
import {AccountLogin, FakeCaptcha, PhoneLogin, R} from "@/services/typings";

const apiRoot = process.env.NODE_ENV === 'production' ? '' : '/api'

/** 通过账号密码接口 POST /login/account */
export async function loginByAccount(accountLogin: AccountLogin, options?: Record<string, any>) {
  return request<R<boolean>>(`${apiRoot}/login/account`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: accountLogin,
    ...(options || {}),
  });
}

/** 通过手机验证码接口 POST /login/phone */
export async function loginByPhone(phoneLogin: PhoneLogin, options?: Record<string, any>) {
  return request<R<boolean>>(`${apiRoot}/login/phone`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: phoneLogin,
    ...(options || {}),
  });
}

/** 发送验证码 POST /login/captcha */
export async function getFakeCaptcha(params: { phone?: string; }, options?: { [key: string]: any }) {
  return request<FakeCaptcha>('${apiRoot}/login/captcha', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}
