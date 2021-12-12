import { Box, Button, Typography } from "@mui/material";
import { ReactElement, useContext, useState } from "react";
import { useFormik } from "formik";
import { FormikFormControl } from "../components/FormirkFormControl/formik-form-control";
import { LoginDTO, LoginDTOValidationSchema } from "../schemas/LoginDTO";
import CircularProgress from "@mui/material/CircularProgress";
import { AxiosError, AxiosResponse } from "axios";
import { userLoginUrl } from "../constants/urls";
import { axiosInstance } from "../axios/axios";
import { LoginResponse } from "../types/axios-responses/LoginResponse";
import { AxiosConfigContext } from "../contexts/AxiosContext";

function Login(): ReactElement {
  const [formIsSubmitting, setFormIsSubmitting] = useState(false);
  const [submitMessage, setSubmitMessage] = useState("");
  const axiosContext = useContext(AxiosConfigContext);

  const formik = useFormik({
    initialValues: {
      email: "",
      plainTextPassword: "",
    },
    validationSchema: LoginDTOValidationSchema,
    onSubmit: (values: LoginDTO) => {
      setFormIsSubmitting(true);
      axiosInstance()
        .post(userLoginUrl(), values)
        .then((successResponse: AxiosResponse<LoginResponse>) => {
          const data: LoginResponse = successResponse.data;
          setSubmitMessage(data.message);
          setFormIsSubmitting(false);

          if (axiosContext != null && axiosContext.setAxiosConfig != null) {
            axiosContext.setAxiosConfig({
              headers: { Authorization: "Bearer " + data.entity.jsonWebToken },
            });
          }
        })
        .catch((errorResponse: AxiosError<LoginResponse>) => {
          const data: LoginResponse | undefined = errorResponse?.response?.data;

          axiosContext.setAxiosConfig({
            headers: { Authorization: "" },
          });

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
            Login
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

export { Login };
