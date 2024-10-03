import { configureStore, Middleware } from '@reduxjs/toolkit'
import {
  TypedUseSelectorHook,
  useDispatch as useAppDispatch,
  useSelector as useAppSelector,
} from 'react-redux'
import { rtkQueryErrorLogger } from '../exception/MacroErrorHandler.ts'
import reducer from '../reducer/reducer.ts'
import { userApi } from '../services/UserService.ts'
import { transactionApi } from '../services/TransactionService.ts'

export const store = configureStore({
  reducer: reducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware()
      .concat(rtkQueryErrorLogger)
      .concat(userApi.middleware as Middleware)
      .concat(transactionApi.middleware as Middleware),
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

const useDispatch = () => useAppDispatch<AppDispatch>()
const useSelector: TypedUseSelectorHook<RootState> = useAppSelector

export { useSelector, useDispatch }
