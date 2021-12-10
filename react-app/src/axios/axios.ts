import { urlPrefix } from "../constants/urls";
import axios, { AxiosRequestConfig } from "axios";

const axiosConfig: AxiosRequestConfig = { baseURL: urlPrefix() };

const axiosInstance = (customConfig: AxiosRequestConfig = axiosConfig) =>
  axios.create(customConfig);

export { axiosInstance };
