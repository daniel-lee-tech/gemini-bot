import { Response } from "./Response";

interface LoginResponse extends Response {
  entity: AuthenticatedUser;
}

interface AuthenticatedUser {
  id: number;
  email: string;
  jsonWebToken: string;
}

export type { LoginResponse, AuthenticatedUser };
