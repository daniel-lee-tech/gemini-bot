import { Response } from "./Response";

interface RegisterResponse extends Response {
  entity: User;
}

interface User {
  id: number;
  email: string;
  encodedPassword: string;
  activated: boolean;
  activationToken: string;
}

export type { RegisterResponse };
