import * as yup from "yup";
import { SchemaOf } from "yup";

interface RegisterDTO {
  email: string;
  plainTextPassword: string;
  plainTextPasswordConfirmation: string;
}

const RegisterDTOValidationSchema: SchemaOf<RegisterDTO> = yup
  .object({
    email: yup
      .string()
      .matches(
        /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/,
        "Must be an email"
      )
      .required(),
    plainTextPassword: yup.string().required().min(8),
    plainTextPasswordConfirmation: yup
      .string()
      .required()
      .oneOf([yup.ref("plainTextPassword")], "Passwords must match"),
  })
  .required();

export { RegisterDTOValidationSchema };
export type { RegisterDTO };
