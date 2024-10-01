import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { ILogin, IUser } from '../interface/UserInterface.ts';
import { ApiRequestType } from '../type/ApiRequestType.ts';

const USER_TAG: string = 'Users';

export const userApi = createApi({
  reducerPath: 'userApi',
  baseQuery: fetchBaseQuery({ baseUrl: '/api/user' }),
  tagTypes: [USER_TAG],
  endpoints: (builder) => ({
    doLogin: builder.mutation<boolean, ILogin>({
      query: (payload: ILogin) => ({
        url: '/login',
        method: ApiRequestType.POST,
        body: payload,
      }),
    }),
    getUserById: builder.mutation<IUser, string>({
      query: (id) => ({
        url: `/${id}`,
        method: ApiRequestType.GET,
      }),
    }),
    getAllUsers: builder.query<IUser[], void>({
      query: () => ({
        url: '/all',
        method: ApiRequestType.GET,
      }),
      providesTags: [USER_TAG],
    }),
    registerUser: builder.mutation<IUser, IUser>({
      query: (payload) => ({
        url: '',
        method: ApiRequestType.POST,
        body: payload,
      }),
      invalidatesTags: [USER_TAG],
    }),
    updateUser: builder.mutation<void, IUser>({
      query: (payload) => ({
        url: '',
        method: ApiRequestType.PUT,
        body: payload,
      }),
      invalidatesTags: [USER_TAG],
    }),
    updatePassword: builder.mutation<void, IUser>({
      query: (payload) => ({
        url: '/password',
        method: ApiRequestType.PUT,
        body: payload,
      }),
    }),
    deleteUser: builder.mutation<void, IUser>({
      query: (payload) => ({
        url: '/delete',
        method: ApiRequestType.POST,
        body: payload,
      }),
      invalidatesTags: [USER_TAG],
    }),
    validateEmail: builder.mutation<boolean, string>({
      query: (email) => ({
        url: `/validate/email?email=${email}`,
        method: ApiRequestType.GET,
      }),
    }),
    validateUsername: builder.mutation<boolean, string>({
      query: (username) => ({
        url: `/validate/username?username=${username}`,
        method: ApiRequestType.GET,
      }),
    }),
  }),
});

// hooks export
export const {
  useDoLoginMutation,
  useGetUserByIdMutation,
  useGetAllUsersQuery,
  useRegisterUserMutation,
  useUpdateUserMutation,
  useUpdatePasswordMutation,
  useDeleteUserMutation,
  useValidateEmailMutation,
  useValidateUsernameMutation,
} = userApi;
