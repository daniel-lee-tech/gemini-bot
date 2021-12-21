import { Response } from "./Response";

interface NetWorthFeesResponse extends Response {
  entity: FeesNetWorth[];
}

interface FeesNetWorth {
  currency: string;
  netFees: number;
}

export type { NetWorthFeesResponse, FeesNetWorth };
