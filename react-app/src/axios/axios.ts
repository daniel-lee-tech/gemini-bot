import { urlPrefix } from "../constants/urls";
import axios, { AxiosRequestConfig } from "axios";

const axiosConfig: AxiosRequestConfig = { baseURL: urlPrefix() };

const axiosInstance = (customConfig?: any) =>
  axios.create({ ...axiosConfig, ...customConfig });

export { axiosInstance, axiosConfig };
