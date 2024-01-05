import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from "axios";
import {setMessage} from "./message";
import URL from "../misc/url";

const initialState = {
    error: null,
    isLoggedIn: false,
    user: {},
    role: []
};

export const addFriend = createAsyncThunk(
    "authenticated/addFriend",
    async ({userId, friendId}, thunkAPI) => {
        try {
            const response = await axios.post(`${URL.BASE_URL}/friends/add-friend/${userId}/${friendId}`);
            return response.data;

        } catch (error){
            thunkAPI.dispatch(setMessage(error.response.data.message));
            return  thunkAPI.rejectWithValue(error.response.data.message);
        }
    }
);

export const removeFriend = createAsyncThunk(
    "authenticated/removeFriend",
    async ({userId, friendId}, thunkAPI) => {
        try {
            const response = await axios.delete(`${URL.BASE_URL}/friends/remove-friend/${userId}/${friendId}`);
            return response.data;

        } catch (error){
            thunkAPI.dispatch(setMessage(error.response.data.message));
            return  thunkAPI.rejectWithValue(error.response.data.message);
        }
    }
);

export const signIn = createAsyncThunk(
    "authenticated/signin",
    async ({email, password}, thunkAPI) => {
        try {
            const response = await axios.post(URL.SIGN_IN,{email, password});
            return response.data;

        } catch (error){
            thunkAPI.dispatch(setMessage(error.response.data.message));
            return  thunkAPI.rejectWithValue(error.response.data.message);
        }
    }
);

export const signUp = createAsyncThunk(
    "authenticated/signup",
    async ({firstName, lastName, email, password}, thunkAPI) => {
        try {
            const response = await axios.post(URL.SIGN_UP,{firstName, lastName, email, password});
            thunkAPI.dispatch(setMessage(response.data.message));
            return response.data;

        } catch (error){
            thunkAPI.dispatch(setMessage(error.response.data.message));
            return  thunkAPI.rejectWithValue(error.response.data.message);
        }
    }
);

export const signOut = createAsyncThunk(
    "authenticated/signout",
    async () => {
        await localStorage.clear();
    }

);

const mySlice = createSlice({
    name: 'authenticated',
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(signIn.fulfilled, (state, action) => {
                state.isLoggedIn = true;
                state.error = false;
                state.user = action.payload;
                state.role = action.payload.role;
            })

            .addCase(signIn.rejected, (state, action) => {
                state.error = true;
            })

            .addCase(signUp.fulfilled, (state, action) => {
                state.error = false;
            })

            .addCase(signUp.rejected, (state, action) => {
                state.error = true;
            })

            .addCase(signOut.fulfilled, (state, action) => {
                state.isLoggedIn = false;
                state.user = {};
                state.error = false;
                state.role = []
            })

            .addCase(addFriend.fulfilled, (state, action) => {
                state.user = action.payload;
            })

            .addCase(removeFriend.fulfilled, (state, action) => {
                state.user = action.payload;
            });

    },
});

const {reducer} = mySlice;
export default reducer
