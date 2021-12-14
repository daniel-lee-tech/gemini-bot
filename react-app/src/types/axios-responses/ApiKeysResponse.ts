import { Response } from "./Response";

interface ApiKeysResponse extends Response {
  entity: ApiKey[];
}

interface ApiKey {
  id: number;
  user: {
    id: number;
    email: string;
    passwordDigest: string;
    emailVerified: boolean;
    emailVerificationToken: string;
  };
  type: string;
  publicKey: string;
  secretKey: string;
}

export type { ApiKeysResponse };
