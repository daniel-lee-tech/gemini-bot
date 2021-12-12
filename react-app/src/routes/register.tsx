import { Box, Button, Typography } from "@mui/material";
import { ReactElement, useContext, useState } from "react";
import { useFormik } from "formik";
import { FormikFormControl } from "../components/FormirkFormControl/formik-form-control";
import {
  RegisterDTO,
  RegisterDTOValidationSchema,
} from "../schemas/RegisterDTO";
import CircularProgress from "@mui/material/CircularProgress";
import { AxiosError, AxiosResponse } from "axios";
import { userRegisterUrl } from "../constants/urls";
import { axiosInstance } from "../axios/axios";
import { RegisterResponse } from "../types/axios-responses/RegisterResponse";
import { AxiosConfigContext } from "../contexts/AxiosContext";

function Register(): ReactElement {
  const [formIsSubmitting, setFormIsSubmitting] = useState(false);
  const [submitMessage, setSubmitMessage] = useState("");
  const axiosContext = useContext(AxiosConfigContext);

  const formik = useFormik({
    initialValues: {
      email: "",
      plainTextPassword: "",
      plainTextPasswordConfirmation: "",
    },
    validationSchema: RegisterDTOValidationSchema,
    onSubmit: (values: RegisterDTO) => {
      setFormIsSubmitting(true);
      axiosInstance(axiosContext.axiosConfig)
        .post(userRegisterUrl(), values)
        .then((successResponse: AxiosResponse<RegisterResponse>) => {
          const data: RegisterResponse = successResponse.data;
          setSubmitMessage(data.message);
          setFormIsSubmitting(false);
        })
        .catch((errorResponse: AxiosError<RegisterResponse>) => {
          const data: RegisterResponse | undefined =
            errorResponse?.response?.data;

          if (data === undefined) {
            setSubmitMessage(
              "Something went wrong, please let us know your issue by contacting us. So sorry!"
            );
            setFormIsSubmitting(false);
          } else {
            setSubmitMessage(data.message);
            setFormIsSubmitting(false);
          }
        });
    },
  });

  return (
    <Box width="100%" maxWidth="800px" margin="auto">
      {formIsSubmitting ? (
        <CircularProgress />
      ) : (
        <form action="POST" onSubmit={formik.handleSubmit}>
          <p>{submitMessage}</p>
          <Typography variant="h2" gutterBottom={true}>
            Register
          </Typography>

          <FormikFormControl
            formik={formik}
            fieldLabel={"Email"}
            fieldName={"email"}
            fieldType={"email"}
            helperText={"An email will be sent to you"}
          />

          <FormikFormControl
            fieldLabel={"Password"}
            formik={formik}
            fieldName={"plainTextPassword"}
            fieldType="password"
            helperText={"Password must be 8 characters or more"}
          />

          <FormikFormControl
            fieldLabel={"Confirm Password"}
            formik={formik}
            fieldName="plainTextPasswordConfirmation"
            fieldType="password"
            helperText={"Please input your password again"}
          />

          <Button
            sx={{ height: 50, marginTop: 3 }}
            fullWidth
            size="large"
            variant="contained"
            type="submit"
          >
            Submit
          </Button>
        </form>
      )}
    </Box>
  );
}

export { Register };
