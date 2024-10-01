import { combineReducers } from '@reduxjs/toolkit';
import { userApi } from '../service/UserService.ts';
import { TypedUseSelectorHook, useSelector } from 'react-redux';
import { transactionApi } from '../service/TransactionService.ts';

const reducer = combineReducers({
  [userApi.reducerPath]: userApi.reducer,
  [transactionApi.reducerPath]: transactionApi.reducer,
});

export type RootState = ReturnType<typeof reducer>;
export const useTypedSelector: TypedUseSelectorHook<RootState> = useSelector;
export default reducer;
