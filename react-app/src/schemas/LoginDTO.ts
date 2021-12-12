import * as yup from "yup";
import { SchemaOf } from "yup";

interface LoginDTO {
  email: string;
  plainTextPassword: string;
}

const LoginDTOValidationSchema: SchemaOf<LoginDTO> = yup
  .object({
    email: yup
      .string()
      .matches(
        /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/,
        "Must be an email"
      )
      .required(),
    plainTextPassword: yup.string().required().min(8),
  })
  .required();

export { LoginDTOValidationSchema };
export type { LoginDTO };
