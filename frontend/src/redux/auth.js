import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from "axios";
import {setMessage} from "./message";
import URL from "../misc/url";

const initialState = {
    error: null,
    isLoggedIn: false,
    user: {}
};

export const signIn = createAsyncThunk(
    "authenticated/signin",
    async ({email, password}, thunkAPI) => {
        console.log(email,password)
        try {
            const response = await axios.post(URL.SIGN_IN,{email, password});
            console.log(response)

            console.log(response.data)
            return response.data

        } catch (error){
            thunkAPI.dispatch(setMessage(error.response.data.message))
            console.log(error.response.data.message)
            // return error.response.data.message
            return  thunkAPI.rejectWithValue(error.response.data.message)
        }
    }
)


export const signUp = createAsyncThunk(
    "authenticated/signup",
    async ({firstName, lastName, email, password}, thunkAPI) => {
        console.log(firstName,lastName,email,password)
        try {
            const response = await axios.post(URL.REGISTER,{firstName, lastName, email, password});
            // thunkAPI.dispatch
            console.log(response.data.message)
            // console.log(response.data)
            thunkAPI.dispatch(setMessage(response.data.message))

            return response.data;

        } catch (error){
            thunkAPI.dispatch(setMessage(error.response.data.message))
            console.log(error.response.data.message)
            return  thunkAPI.rejectWithValue(error.response.data.message)
        }
    }
)

export const signOut = createAsyncThunk(
    "authenticated/signout",
    async ({firstName, lastName, email, password}, thunkAPI) => {
        console.log(firstName,lastName,email,password)
        try {
            const response = await axios.post(URL.REGISTER,{firstName, lastName, email, password});
            // thunkAPI.dispatch
            console.log(response.data.message)
            // console.log(response.data)
            thunkAPI.dispatch(setMessage(response.data.message))

            return response.data;

        } catch (error){
            thunkAPI.dispatch(setMessage(error.response.data.message))
            console.log(error.response.data.message)
            return  thunkAPI.rejectWithValue(error.response.data.message)
        }
    }
)

const mySlice = createSlice({
    name: 'authenticated',
    initialState,
    reducers: {},
    extraReducers: {
        [signUp.fulfilled]: (state, action) => {
            state.isLoggedIn = false;
            state.error = false
            state.user = {}
        },
        [signUp.rejected]: (state, action) => {
            state.isLoggedIn = false;
            state.error = true;
            state.user = {}
        },
        [signIn.fulfilled]: (state, action) => {
            state.isLoggedIn = true;
            state.error = false;
            state.user = action.payload;
        },
        [signIn.rejected]: (state, action) => {
            state.isLoggedIn = false;
            state.user = {};
            state.error = true;
        },


    },
});

const {reducer} = mySlice;
export default reducer
