// @ts-ignore
/* eslint-disable */
import { request } from 'umi';
import {Page, PageParams, R} from "@/services/common/typings";
import {CurrentUser, UserForm, UserVO} from "@/services/uaa/type/user";

/** 获取当前登录用户的个人和权限信息接口 GET /user/current */
export async function getCurrentUserInfo(options?: Record<string, any>) {
  return request<R<CurrentUser>>(`/api/uaa/user/current`, {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取用户信息（分页） GET /user/page */
export async function getUserPage(params: PageParams, options?: Record<string, any>) {
  return request<R<Page<UserVO>>>(`/api/uaa/user/page`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: {
      ...params,
      page: undefined,
      ...params['page'],
    },
    ...(options || {}),
  });
}

/** 添加用户接口 POST /user */
export async function addUser(body: UserForm, options?: Record<string, any>) {
  return request<R<number>>(`/api/uaa/user`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 更新用户接口 PUT /user */
export async function updateUser(body: UserForm, options?: Record<string, any>) {
  return request<R<number>>(`/api/uaa/user`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 批量删除用户接口 DELETE /user */
export async function batchDeleteUser(userIds: (number | undefined)[], options?: Record<string, any>) {
  return request<R<number>>(`/api/uaa/user`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    data: userIds,
    ...(options || {}),
  });
}

/** 获取用户信息（通过用户id） GET /user/${userId} */
export async function getUserById(userId: number, options?: Record<string, any>) {
  return request<R<UserVO>>(`/api/uaa/user/${userId}`, {
    method: 'GET',
    ...(options || {}),
  });
}

/** 删除用户接口 DELETE /user/${userId} */
export async function deleteUser(userId: number, options?: Record<string, any>) {
  return request<R<number>>(`/api/uaa/user/${userId}`, {
    method: 'DELETE',
    ...(options || {}),
  });
}
/** 通过角色ID获取用户信息（分页） GET /user/role/page */
export async function getUserPageByRoleId(pageParams: PageParams, roleId?: number, options?: Record<string, any>) {
  return request<R<Page<UserVO>>>(`/api/uaa/user/role/page`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: {
      ...pageParams,
      roleId,
    },
    ...(options || {}),
  });
}

/** 通过部门ID获取用户信息（分页） GET /user/department/page */
export async function getUserPageByDepartmentId(pageParams: PageParams, departmentId?: number, options?: Record<string, any>,) {
  return request<R<Page<UserVO>>>(`/api/uaa/user/department/page`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: {
      ...pageParams,
      departmentId,
    },
    ...(options || {}),
  });
}
