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
import { UserAxiosContext } from "../contexts/UserAxiosContext";

function Login(): ReactElement {
  const [formSubmitState, setFormSubmitState] = useState({
    formIsSubmitting: false,
    submitMessage: "",
  });

  const { setUserInfo, setAxiosConfig, setUserAxiosConfig } =
    useContext(UserAxiosContext);

  const formik = useFormik({
    initialValues: {
      email: "",
      plainTextPassword: "",
    },
    validationSchema: LoginDTOValidationSchema,
    onSubmit: (values: LoginDTO) => {
      setFormSubmitState({
        formIsSubmitting: true,
        submitMessage: "Submitting, please do NOT refresh.",
      });

      axiosInstance()
        .post(userLoginUrl(), values)
        .then((successResponse: AxiosResponse<LoginResponse>) => {
          const data: LoginResponse = successResponse.data;
          setFormSubmitState({
            formIsSubmitting: false,
            submitMessage: data.message,
          });

          if (setAxiosConfig != null && setUserInfo != null) {
            setUserAxiosConfig({
              userInfo: {
                id: data.entity.id,
                email: data.entity.email,
                jsonWebToken: data.entity.jsonWebToken,
              },
              axiosConfig: {
                headers: {
                  Authorization: "Bearer " + data.entity.jsonWebToken,
                },
              },
            });
          }
        })
        .catch((errorResponse: AxiosError<LoginResponse>) => {
          const data: LoginResponse | undefined = errorResponse?.response?.data;

          setAxiosConfig({
            headers: { Authorization: "" },
          });

          setFormSubmitState({
            formIsSubmitting: false,
            submitMessage:
              data?.message !== undefined
                ? data.message
                : "Something went wrong, please let us know your issue by contacting us. So sorry!",
          });
        });
    },
  });

  return (
    <Box width="100%" maxWidth="800px" margin="auto">
      {formSubmitState.formIsSubmitting ? (
        <CircularProgress />
      ) : (
        <form action="POST" onSubmit={formik.handleSubmit}>
          <p>{formSubmitState.submitMessage}</p>
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

export {Login};
