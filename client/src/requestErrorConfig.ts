import type { RequestConfig } from '@umijs/max';
// import { message, notification } from 'antd';
import {getToken} from "@/utils/common";

/*const codeMessage = {
  200: '服务器成功返回请求的数据。',
  201: '新建或修改数据成功。',
  202: '一个请求已经进入后台排队（异步任务）。',
  204: '删除数据成功。',
  400: '发出的请求有错误，服务器没有进行新建或修改数据的操作。',
  401: '用户没有权限（令牌、用户名、密码错误）。',
  403: '用户得到授权，但是访问是被禁止的。',
  404: '发出的请求针对的是不存在的记录，服务器没有进行操作。',
  405: '请求方法不被允许。',
  406: '请求的格式不可得。',
  410: '请求的资源被永久删除，且不会再得到的。',
  422: '当创建一个对象时，发生一个验证错误。',
  500: '服务器发生错误，请检查服务器。',
  502: '网关错误。',
  503: '服务不可用，服务器暂时过载或维护。',
  504: '网关超时。',
};*/


// 错误处理方案： 错误类型
enum ErrorShowType {
  SILENT = 0,
  WARN_MESSAGE = 1,
  ERROR_MESSAGE = 2,
  NOTIFICATION = 3,
  REDIRECT = 9,
}

// 与后端约定的响应数据格式
interface ResponseStructure {
  success: boolean;
  data: any;
  errorCode?: number;
  errorMessage?: string;
  showType?: ErrorShowType;
}

/**
 * 自动添加 AccessToken 的请求前拦截器
 * 参考 https://github.com/umijs/umi-request/issues/181#issuecomment-730794198
 * @param url
 * @param options
 */
const authHeaderInterceptor = (url: string, options: RequestConfig) => {
  const accessToken = getToken();

  if (accessToken) {
    return {
      url,
      options: {
        ...options,
        interceptors: true,
        headers: {
          // ...options.headers,
          Authorization: accessToken,
        },
      },
    };
  }

  return {url, options};
};




/**
 * @name 错误处理
 * pro 自带的错误处理， 可以在这里做自己的改动
 * @doc https://umijs.org/docs/max/request#配置
 */
export const errorConfig: RequestConfig = {
  // 错误处理： umi@3 的错误处理方案。
  errorConfig: {
    // 错误抛出
    errorThrower: (res) => {
      const { success, data, errorCode, errorMessage, showType } =
        res as unknown as ResponseStructure;
      if (!success) {
        const error: any = new Error(errorMessage);
        error.name = 'BizError';
        error.info = { errorCode, errorMessage, showType, data };
        throw error; // 抛出自制的错误
      }
    },
    // 错误接收及处理
    /*errorHandler: (error: any, opts: any) => {
      if (opts?.skipErrorHandler) throw error;
      // 我们的 errorThrower 抛出的错误。
      if (error.name === 'BizError') {
        const errorInfo: ResponseStructure | undefined = error.info;
        if (errorInfo) {
          const { errorMessage, errorCode } = errorInfo;
          switch (errorInfo.showType) {
            case ErrorShowType.SILENT:
              // do nothing
              break;
            case ErrorShowType.WARN_MESSAGE:
              message.warn(errorMessage);
              break;
            case ErrorShowType.ERROR_MESSAGE:
              message.error(errorMessage);
              break;
            case ErrorShowType.NOTIFICATION:
              notification.open({
                description: errorMessage,
                message: errorCode,
              });
              break;
            case ErrorShowType.REDIRECT:
              // TODO: redirect
              break;
            default:
              message.error(errorMessage);
          }
        }
      } else if (error.response) {
        // Axios 的错误
        // 请求成功发出且服务器也响应了状态码，但状态代码超出了 2xx 的范围
        message.error('Response status:', error.response.status);
      } else if (error.request) {
        // 请求已经成功发起，但没有收到响应
        // \`error.request\` 在浏览器中是 XMLHttpRequest 的实例，
        // 而在node.js中是 http.ClientRequest 的实例
        message.error('None response! Please retry.');
      } else {
        // 发送请求时出了点问题
        message.error('Request error, please retry.');
      }
    },*/
  },

  // 请求拦截器
  requestInterceptors: [authHeaderInterceptor],

  // 响应拦截器
  responseInterceptors: [
    /*(response) => {
      // 拦截响应数据，进行个性化处理
      const { data } = response as unknown as ResponseStructure;
      if (!data.success) {
        message.error('请求失败！');
      }
      return response;
    },*/
  ],
};





/*
let confirmModalVisible = false;

/!** 异常处理程序
 * @see https://pro.ant.design/zh-CN/docs/request
 *!/
const errorHandler = async (error: ResponseError) => {
  // if (error.message === 'Failed to fetch') {
  //   throw error;
  // }

  const { response } = error;

  if (!response) {
    notification.error({
      description: '您的网络发生异常，无法连接服务器',
      message: '网络异常',
    });
  }

  if (response.status) {
    const errorText = codeMessage[response.status] || response.statusText;
    const { status, url } = response;

    const res = await response.clone().json();

    if (status === 401) {
      /!**
       * (4010, "密码账号认证出错")
       * (4011, "token签名异常")
       * (4012, "token格式不正确")
       * (4013, "token已过期")
       * (4014, "不支持该token")
       * (4015, "token参数异常")
       * (4016, "token错误")
       *!/
      if (res.code >= 4011 && res.code <= 4016) {
        if (history.location.pathname !== '/login' && history.location.pathname !== '/') {
          if (!confirmModalVisible) {
            confirmModalVisible = true;
            Modal.confirm({
              title: '登陆超时',
              icon: <ExclamationCircleOutlined />,
              content: '您已被登出，可以取消继续留在该页面，或者重新登录。',
              okText: '重新登陆',
              cancelText: '留在此页',
              onOk() {
                confirmModalVisible = false;
                history.replace({
                  pathname: '/login',
                  search: stringify({
                    redirect: history.location.pathname,
                  }),
                });
              },
              onCancel() {
                confirmModalVisible = false;
              },
            });
          }
        }
      } else {
        notification.error({
          message: '鉴权失败',
          description: res.message || '您的网络发生异常，无法连接服务器',
        });
      }
    } else {
      notification.error({
        message: `请求错误 ${status}: ${url}`,
        description: res.message || errorText,
      });
    }
  }

  throw error;
};
*/