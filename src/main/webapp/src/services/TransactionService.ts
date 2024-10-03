import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import { ApiRequestType } from '../types/ApiRequestType.ts'
import {
  IMonthlyTransaction,
  IPeriodParam,
  ITotalByPeriod,
  ITransaction,
  ITransactionResult,
} from '../interfaces/TransactionInterface.ts'

const TRA_TAG: string = 'Transactions'

export const transactionApi = createApi({
  reducerPath: 'transactionApi',
  baseQuery: fetchBaseQuery({ baseUrl: '/api/transaction' }),
  tagTypes: [TRA_TAG],
  endpoints: (builder) => ({
    getMonthlyTransactions: builder.mutation<IMonthlyTransaction, IPeriodParam>(
      {
        query: (payload) => ({
          url: '/month',
          method: ApiRequestType.POST,
          body: payload,
        }),
      },
    ),
    getCtgTotalByPeriod: builder.mutation<ITotalByPeriod, IPeriodParam>({
      query: (payload) => ({
        url: `/period/${payload.periodType}`,
        method: ApiRequestType.POST,
        body: payload,
      }),
    }),
    getOneTransactionsById: builder.mutation<ITransactionResult, number>({
      query: (transactionId) => ({
        url: `/one/${transactionId}`,
        method: ApiRequestType.GET,
      }),
    }),
    addTransaction: builder.mutation<ITransaction, ITransaction>({
      query: (payload) => ({
        url: ``,
        method: ApiRequestType.POST,
        body: payload,
      }),
      invalidatesTags: [TRA_TAG],
    }),
    updateTransaction: builder.mutation<void, ITransaction>({
      query: (payload) => ({
        url: ``,
        method: ApiRequestType.PUT,
        body: payload,
      }),
      invalidatesTags: [TRA_TAG],
    }),
    deleteTransaction: builder.mutation<void, number>({
      query: (transactionId) => ({
        url: `${transactionId}`,
        method: ApiRequestType.DELETE,
      }),
      invalidatesTags: [TRA_TAG],
    }),
  }),
})

export const {
  useGetMonthlyTransactionsMutation,
  useGetCtgTotalByPeriodMutation,
  useGetOneTransactionsByIdMutation,
  useAddTransactionMutation,
  useUpdateTransactionMutation,
  useDeleteTransactionMutation,
} = transactionApi
