import { AxiosRequestConfig } from "axios";
import { ApiKey } from "../schemas/ApiKey";
import { axiosInstance } from "./axios";
import { apiUrl } from "../constants/urls";
import { ApiKeysResponse } from "../types/axios-responses/ApiKeysResponse";

function addApiKey(
  axiosConfig: AxiosRequestConfig,
  values: ApiKey
): Promise<any> {
  return axiosInstance(axiosConfig)
    .post(apiUrl(), values)
    .then((response) => response.data);
}

function fetchKeys(axiosConfig: AxiosRequestConfig): Promise<ApiKeysResponse> {
  return axiosInstance(axiosConfig)
    .get(apiUrl())
    .then((response) => response.data);
}

export { addApiKey, fetchKeys };
