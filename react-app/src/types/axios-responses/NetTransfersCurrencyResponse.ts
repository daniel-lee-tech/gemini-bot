import { Response } from "./Response";

interface NetWorthTransfersResponse extends Response {
  entity: TransfersNetWorth[];
}

interface TransfersNetWorth {
  currency: string;
  netTransfers: number;
}

export type { NetWorthTransfersResponse, TransfersNetWorth };
