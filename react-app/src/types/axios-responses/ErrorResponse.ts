import { Response } from "./Response";

interface ErrorResponse extends Response {
  error: true;
  entity: Object;
}

export type { ErrorResponse };
