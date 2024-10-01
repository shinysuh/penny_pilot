import { FetchBaseQueryError } from '@reduxjs/toolkit/query';
import { SerializedError } from '@reduxjs/toolkit';
import toast from 'react-hot-toast';
import { ToastType } from '../type/ToastType.ts';

interface IError {
  data: { code: string; message: string };
}

export const globalExceptionHandler = (
  err: FetchBaseQueryError | SerializedError | IError | undefined,
) => {
  const error: IError = err as { data: { code: string; message: string } };

  let isErrorInfoExists = !!error.data.code;

  if (typeof err === 'undefined') isErrorInfoExists = false;
  if (isErrorInfoExists && typeof error.data === 'undefined')
    isErrorInfoExists = false;
  if (isErrorInfoExists && typeof error.data.code === 'undefined')
    isErrorInfoExists = false;

  const sessionErrors = ['SECURITY001', 'SECURITY002'];

  if (!isErrorInfoExists) {
    toast('알 수 없는 오류가 발생했습니다. 관리자에게 문의해주세요.', {
      icon: ToastType.ERROR,
    });
  } else {
    toast(`[${error.data.code}] ${error.data.message}`, {
      icon: ToastType.ERROR,
    });
  }

  if (sessionErrors.includes(error.data.code)) {
    window.location.href = '/login';
  }
};
