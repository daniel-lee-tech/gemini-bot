import React from "react";
import { UserAxiosContext } from "../contexts/UserAxiosContext";

function useUser() {
  return React.useContext(UserAxiosContext).userAxiosConfig.userInfo;
}

export { useUser };
