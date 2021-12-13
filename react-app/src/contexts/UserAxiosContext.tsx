import React, { createContext, useCallback, useEffect, useState } from "react";

import { baseConfig } from "../axios/axios";
import { AxiosRequestConfig } from "axios";

interface IUserInfo {
  id: null | number;
  email: null | string;
  jsonWebToken: null | string;
}

const initialUserInfo: IUserInfo = {
  id: null,
  email: null,
  jsonWebToken: null,
};

interface IUserAxiosContextState {
  userInfo: IUserInfo;
  axiosConfig: AxiosRequestConfig;
}

interface IUserAxiosContext {
  userAxiosConfig: IUserAxiosContextState;
  setUserAxiosConfig: (newUserAxiosConfig: IUserAxiosContextState) => void;
  setUserInfo: (newUserInfo: IUserInfo) => void;
  setAxiosConfig: (newAxiosConfig: AxiosRequestConfig) => void;
}

const initialContext: IUserAxiosContext = {
  userAxiosConfig: { userInfo: initialUserInfo, axiosConfig: baseConfig },
  setUserAxiosConfig: (_) => {},
  setUserInfo: (_) => {},
  setAxiosConfig: (_) => {},
};

const UserAxiosContext = createContext(initialContext);

function UserAxiosContextProvider({ children }: { children: React.ReactNode }) {
  // this gets overridden during runtime when jsx is rendered
  const [userAxiosConfig, setUserAxiosConfig] = useState(initialContext);

  function setUserAxiosConfigWrapper(newState: IUserAxiosContextState) {
    const newValue = { ...userAxiosConfig };
    newValue.userAxiosConfig = { ...newState };
    setUserAxiosConfig(newValue);

    localStorage.setItem(
      "axiosConfig",
      JSON.stringify(newValue.userAxiosConfig.axiosConfig)
    );

    localStorage.setItem(
      "userInfo",
      JSON.stringify(newValue.userAxiosConfig.userInfo)
    );
  }

  function setAxiosConfigWrapper(newConfig: AxiosRequestConfig) {
    const newValue = { ...userAxiosConfig };
    newValue.userAxiosConfig.axiosConfig = {
      ...newValue.userAxiosConfig.axiosConfig,
      ...newConfig,
    };

    setUserAxiosConfig(newValue);

    localStorage.setItem(
      "axiosConfig",
      JSON.stringify(newValue.userAxiosConfig.axiosConfig)
    );
  }

  function setUserInfoWrapper(newUserInfo: IUserInfo) {
    const newValue = { ...userAxiosConfig };
    newValue.userAxiosConfig.userInfo = {
      ...newValue.userAxiosConfig.userInfo,
      ...newUserInfo,
    };

    setUserAxiosConfig(newValue);
    localStorage.setItem(
      "userInfo",
      JSON.stringify(newValue.userAxiosConfig.userInfo)
    );
  }

  const checkLocalStorageOnBootup = useCallback(() => {
    const savedAxiosConfig = localStorage.getItem("axiosConfig");
    const savedUser = localStorage.getItem("userInfo");
    if (savedAxiosConfig != null && savedUser != null) {
      const savedValues = userAxiosConfig;
      savedValues.userAxiosConfig.axiosConfig = {
        ...savedValues.userAxiosConfig.axiosConfig,
        ...JSON.parse(savedAxiosConfig),
      };
      savedValues.userAxiosConfig.userInfo = {
        ...savedValues.userAxiosConfig.userInfo,
        ...JSON.parse(savedUser),
      };
      setUserAxiosConfig(savedValues);
      console.log(userAxiosConfig);
    } else {
      localStorage.clear();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    checkLocalStorageOnBootup();
  }, [checkLocalStorageOnBootup]);

  return (
    <UserAxiosContext.Provider
      value={{
        ...userAxiosConfig,
        setUserAxiosConfig: setUserAxiosConfigWrapper,
        setUserInfo: setUserInfoWrapper,
        setAxiosConfig: setAxiosConfigWrapper,
      }}
    >
      {children}
    </UserAxiosContext.Provider>
  );
}

export { UserAxiosContext, UserAxiosContextProvider };
export type { IUserInfo, IUserAxiosContext };
