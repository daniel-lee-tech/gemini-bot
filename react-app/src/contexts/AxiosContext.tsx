import React, { createContext, ReactElement, useEffect, useState } from "react";
import { AxiosRequestConfig } from "axios";
import { axiosConfig as baseConfig } from "../axios/axios";

interface initialValues {
  axiosConfig: any | AxiosRequestConfig<any>;
  setAxiosConfig: (newConfig: AxiosRequestConfig<any>) => void | (() => {});
}

const AxiosConfigContext = createContext({
  axiosConfig: baseConfig,
  setAxiosConfig: (p: AxiosRequestConfig) => {
    console.log(p);
  },
});

interface Props {
  children: React.ReactNode;
}

const AxiosConfigContextProvider = function ({
  children,
}: Props): ReactElement {
  const [axiosConfig, setAxiosConfig] = useState(baseConfig);

  useEffect(() => {
    const savedConfig = localStorage.getItem("axiosConfig");
    if (savedConfig != null)
      setAxiosConfig({ ...baseConfig, ...JSON.parse(savedConfig) });
  }, []);

  const AxiosConfigInitialValues: initialValues = {
    axiosConfig: axiosConfig,
    setAxiosConfig: setAxiosConfigWrapper,
  };

  function setAxiosConfigWrapper(newConfig: AxiosRequestConfig) {
    setAxiosConfig({ ...axiosConfig, ...newConfig });
    localStorage.setItem(
      "axiosConfig",
      JSON.stringify({ ...axiosConfig, ...newConfig })
    );
  }

  return (
    <AxiosConfigContext.Provider value={{ ...AxiosConfigInitialValues }}>
      {children}
    </AxiosConfigContext.Provider>
  );
};

export { AxiosConfigContextProvider, AxiosConfigContext };
