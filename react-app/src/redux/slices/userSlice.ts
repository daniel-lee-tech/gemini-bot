import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import type {RootState} from "../store";

// Define a type for the slice state
interface UserState {
  id: null | number;
  email: null | string;
  jsonWebToken: null | string;
}

// Define the initial state using that type
const initialState: UserState = {
  id: null,
  email: null,
  jsonWebToken: null,
};

export const userSlice = createSlice({
  name: "user",
  // `createSlice` will infer the state type from the `initialState` argument
  initialState,
  reducers: {
    // Use the PayloadAction type to declare the contents of `action.payload`
    updateUser: (state, action: PayloadAction<UserState>) => {
      state.id = action.payload.id;
      state.email = action.payload.email;
      state.jsonWebToken = action.payload.jsonWebToken;
    },
  },
});

export const { updateUser } = userSlice.actions;

// Other code such as selectors can use the imported `RootState` type
export const selectUser = (state: RootState) => state.user;

export default userSlice.reducer;
