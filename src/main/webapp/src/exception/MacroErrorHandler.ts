import type { Middleware, MiddlewareAPI } from '@reduxjs/toolkit';
import { isRejectedWithValue } from '@reduxjs/toolkit';
import { globalExceptionHandler } from './GlobalExceptionHandler.ts';

/**
 * Log a warning and show a toast!
 */
export const rtkQueryErrorLogger: Middleware =
  (api: MiddlewareAPI) => (next) => (action) => {
    // RTK Query uses `createAsyncThunk` from redux-toolkit under the hood, so we're able to utilize these matchers!
    if (isRejectedWithValue(action)) {
      // console.log(action)

      let isToastable = true;

      const error: { data: { code: string; message: string } } =
        action.payload as {
          data: { code: string; message: string };
        };

      if (error.data.code) {
        // if (action.type.includes('loginApi') && error.data.code === 'AU007') isToastable = false
        // if (error.data.code === 'DB_002') {
        //   isToastable = false
        //   alert(error.data.message)
        // }
      }

      if (isToastable) globalExceptionHandler(action.payload);
    }
    return next(action);
  };
