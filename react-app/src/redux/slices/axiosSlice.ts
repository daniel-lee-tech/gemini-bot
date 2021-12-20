import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import type {RootState} from "../store";
import {AxiosRequestConfig} from "axios";
import {baseConfig} from "../../axios/axios";

// Define a type for the slice state
interface AxiosState {
  config: AxiosRequestConfig;
}

// Define the initial state using that type
const initialState: AxiosState = {
  config: baseConfig,
};

export const userSlice = createSlice({
  name: "axios",
  // `createSlice` will infer the state type from the `initialState` argument
  initialState,
  reducers: {
    // Use the PayloadAction type to declare the contents of `action.payload`
    updateAxiosConfig: (state, action: PayloadAction<AxiosRequestConfig>) => {
      state.config = { ...state.config, ...action.payload };
    },
  },
});

export const { updateAxiosConfig } = userSlice.actions;

// Other code such as selectors can use the imported `RootState` type
export const selectAxiosConfig = (state: RootState) => state.axios.config;

export default userSlice.reducer;
