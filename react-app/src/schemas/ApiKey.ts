import * as yup from "yup";
import { SchemaOf } from "yup";

interface ApiKey {
  type: string;
  publicKey: string;
  secretKey: string;
}

const ApiKeyValidationSchema: SchemaOf<ApiKey> = yup
  .object({
    type: yup.string().required(),
    publicKey: yup.string().required(),
    secretKey: yup.string().required(),
  })
  .required();

export { ApiKeyValidationSchema };
export type { ApiKey };
