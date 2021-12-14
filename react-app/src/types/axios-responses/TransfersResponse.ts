import { Response } from "./Response";

interface TransfersResponse extends Response {
  entity: Transfer[];
}

interface Transfer {
  id: number;
  user: {
    id: number;
    email: string;
    passwordDigest: string;
    emailVerified: boolean;
    emailVerificationToken: string;
  };
  type: string;
  advanced: string | null;
  timestampms: number;
  eid: number;
  currency: string;
  amount: number;
  method: null | string;
}

export type { TransfersResponse, Transfer };
