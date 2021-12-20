import { Response } from "./Response";

interface NetWorthCurrencyResponse extends Response {
  entity: CurrencyNetWorth[];
}

interface CurrencyNetWorth {
  currency: string;
  netWorth: number;
}

export type { NetWorthCurrencyResponse, CurrencyNetWorth };
