import { configureStore, Middleware } from '@reduxjs/toolkit';
import { TypedUseSelectorHook, useDispatch as useAppDispatch, useSelector as useAppSelector } from 'react-redux';
import { rtkQueryErrorLogger } from '../exception/MacroErrorHandler.ts';
import reducer from '../reducer/reducer.ts';
import { userApi } from '../service/UserService.ts';

export const store = configureStore({
  reducer: reducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware()
      .concat(rtkQueryErrorLogger)
      .concat(userApi.middleware as Middleware)
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

const useDispatch = () => useAppDispatch<AppDispatch>();
const useSelector: TypedUseSelectorHook<RootState> = useAppSelector;

export { useSelector, useDispatch };

