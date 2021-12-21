import { AxiosRequestConfig } from "axios";
import { ApiKey } from "../schemas/ApiKey";
import { axiosInstance } from "./axios";
import {
  apiUrl,
  netFeesCurrenciesUrl,
  netTransfersCurrenciesUrl,
  netWorthCurrenciesUrl,
  tradesImportUrl,
  tradesUrl,
  transfersImportUrl,
  transfersUrl,
} from "../constants/urls";
import { ApiKeysResponse } from "../types/axios-responses/ApiKeysResponse";
import { TransfersResponse } from "../types/axios-responses/TransfersResponse";
import { NetWorthCurrencyResponse } from "../types/axios-responses/NetWorthCurrencyResponse";
import { NetWorthFeesResponse } from "../types/axios-responses/NetFeesCurrencyResponse";
import { NetWorthTransfersResponse } from "../types/axios-responses/NetTransfersCurrencyResponse";

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

function fetchTransfers(
  axiosConfig: AxiosRequestConfig
): Promise<TransfersResponse> {
  return axiosInstance(axiosConfig)
    .get(transfersUrl())
    .then((response) => response.data);
}

function importTransfers(
  axiosConfig: AxiosRequestConfig
): Promise<TransfersResponse> {
  return axiosInstance(axiosConfig)
    .get(transfersImportUrl())
    .then((response) => response.data);
}

function fetchTrades(
  axiosConfig: AxiosRequestConfig
): Promise<TransfersResponse> {
  return axiosInstance(axiosConfig)
    .get(tradesUrl())
    .then((response) => response.data);
}

function importTrades(
  axiosConfig: AxiosRequestConfig
): Promise<TransfersResponse> {
  return axiosInstance(axiosConfig)
    .get(tradesImportUrl())
    .then((response) => response.data);
}

function fetchNetWorthCurrencies(
  axiosConfig: AxiosRequestConfig
): Promise<NetWorthCurrencyResponse> {
  return axiosInstance(axiosConfig)
    .get(netWorthCurrenciesUrl())
    .then((response) => response.data);
}

function fetchNetFeesCurrencies(
  axiosConfig: AxiosRequestConfig
): Promise<NetWorthFeesResponse> {
  return axiosInstance(axiosConfig)
    .get(netFeesCurrenciesUrl())
    .then((response) => response.data);
}

function fetchNetTransfersCurrencies(
  axiosConfig: AxiosRequestConfig
): Promise<NetWorthTransfersResponse> {
  return axiosInstance(axiosConfig)
    .get(netTransfersCurrenciesUrl())
    .then((response) => response.data);
}

function importNetWorthCurrencies(
  axiosConfig: AxiosRequestConfig
): Promise<TransfersResponse> {
  return axiosInstance(axiosConfig)
    .get(netWorthCurrenciesUrl())
    .then((response) => response.data);
}

export {
  addApiKey,
  fetchKeys,
  fetchTransfers,
  importTransfers,
  fetchTrades,
  importTrades,
  fetchNetWorthCurrencies,
  importNetWorthCurrencies,
  fetchNetFeesCurrencies,
  fetchNetTransfersCurrencies,
};
