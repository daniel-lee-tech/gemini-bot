import { urlPrefix } from "../constants/urls";
import axios, { AxiosRequestConfig } from "axios";

const baseConfig: AxiosRequestConfig = { baseURL: urlPrefix() };

const axiosInstance = (customConfig?: any) =>
  axios.create({ ...baseConfig, ...customConfig });

export { axiosInstance, baseConfig };
